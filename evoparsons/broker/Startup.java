package evoparsons.broker;

import java.nio.file.Paths;

public class Startup {		
		
	// private static Broker getBroker(Log log, Config.Network networkConfig, Config config, Parsons) {
	// 	try {
	// 		Class<?> brokerClass = Class.forName(networkConfig.getBrokerName());		
	// 		Constructor<?> brokerConstructor = brokerClass.getConstructor(Log.class, Config.class, 
	// 			ParsonsGenotypeIndex.class, EvaluationDataStore.class, ParsonsLibrary.class,
	// 			SelectionPolicy.class);
	// 		return (Broker)brokerConstructor.newInstance(log, config, genotypeStore, evalStore, lib, selectionPolicy);
	// 	} catch (Exception e) {
	// 		log.err("[Startup.getBroker] Cannot create broker from %s", config.getBrokerName());
	// 		e.printStackTrace();
	// 		System.exit(1);
	// 	}			
	// 	return null;
	// }
		
	//public static ParsonsBroker Broker;
		
	public static void main(String[] args) {				
		String providedConfig = System.getProperty("file");
		Log log = Log.console;
		Config config = Config.FromFile(log, providedConfig).validate();

		try {
			Paths.get(config.getOutputFolder()).toFile().mkdir();
		} catch (Exception e) {
			System.err.format("Error verifying output folder %s%n", config.getOutputFolder());
			System.exit(1);
		}
		ParsonsLibrary lib = new ParsonsLibrary(log, config);
		
		//TODO: use reflection and configuration to configure selection policy
		//Next policy for epplets
		SelectionPolicy selectionPolicy = SelectionPolicy.pairing;
		switch (config.getSelectionPolicyName()) {
			case ("cycling"):
				selectionPolicy = SelectionPolicy.cycling;
				break;
			case ("epplets.pairing"):
				selectionPolicy = SelectionPolicy.pairing.limit(10).then(SelectionPolicy.dummy);
				break;
			case ("epplets.cycling"):
				selectionPolicy = SelectionPolicy.cycling.limit(10).then(SelectionPolicy.dummy);
				break;	
			default: break;		
		}

		ParsonsGenotypeIndex genotypeStore = new ParsonsGenotypeIndex(log, config);				
		EvaluationDataStore evalStore = new EvaluationDataStore(log, config);
		ParsonsBroker broker = new ParsonsBroker(log, genotypeStore, evalStore, lib, selectionPolicy);
		//TODO: use reflection
		config.getNetworkInterfaces().forEach((id, networkConfig) -> 
			{
				switch (networkConfig.brokerName) {
					case ("RMI"): 
						NetworkPolicy.RMI.startInterface(log, networkConfig, config, broker);
						break;
					case ("REST"): 
						NetworkPolicy.REST.startInterface(log, networkConfig, config, broker);
						break;		
					default:
						log.log("[Startup.NetworkInterface] Unknown interface %s. Possible RMI, REST...", networkConfig.brokerName);
						break;	
				}	
			}
		);							
		EvolutionAlgorithm alg = //TODO - make it more pretty with reflection
			config.getEvolutionAlgorithmName().equals("preset")
			? new Preset(log, config, broker, genotypeStore)
			: new ECJ(log, config, broker, lib.getProgramCount(), lib.getTransformCount());	
		broker.setFitnessConsumer(alg::setFitness);		
		if (evalStore.hasStudents())
			alg.runCheckpoint(providedConfig);
		else 
			alg.runFresh(providedConfig);					
	}
}