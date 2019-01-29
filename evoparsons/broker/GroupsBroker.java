package evoparsons.broker;

// RMI-related imports 
import java.rmi.RemoteException;// RMI-related imports 
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import evoparsons.rmishared.BrokerUIInterface;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import evoparsons.rmishared.Stats;

public class GroupsBroker implements Broker, BrokerUIInterface, BrokerEAInterface {
	
	private static class StudentData {
		public final BrokerUIInterface ui;
		public final int id;
		public final int localId;
		public StudentData(BrokerUIInterface ui, int id, int localId) {
			this.ui = ui;
			this.id = id;
			this.localId = localId;
		}
	}
	private Map<Broker, HashSet<Integer>> brokers;
	private Log log;
	private Library lib;
	private Map<String, StudentData> studentLoginToBroker;
	private Map<Integer, StudentData> studentIdToBroker;

	public GroupsBroker(Config config, Broker parent) {
		this.log = config.getLog();
		this.lib = config.<Library>getInstanceOpt("evoparsons.lib", config).orElse(null);
		List<Broker> brokers = 
			config.getList("evoparsons.broker.child.")
				.stream()
				.map(cf -> Config.FromFile(config, cf).init(this))
				.collect(Collectors.toList());
		if (brokers.size() == 0) {
			log.err("[GroupsBroker] evoparsons.broker.child was not specified");
			System.exit(1);
		}		
		this.brokers = brokers.stream().collect(Collectors.toMap(b -> b, b -> new HashSet<>()));
		this.studentLoginToBroker = new HashMap<>();
		this.studentIdToBroker = new HashMap<>();
	}

	@Override
	public ParsonsPuzzle getParsonsPuzzle(int studentId) throws RemoteException {
		synchronized (studentLoginToBroker) {
			StudentData data = studentIdToBroker.get(studentId);
			// TODO: check for null if necessary
			return data.ui.getParsonsPuzzle(data.localId);
		}
	}

	@Override
	public int getStudentID(String login) throws RemoteException {
		synchronized (studentLoginToBroker) {
			StudentData data = studentLoginToBroker.get(login);
			if (data == null) {
				Broker fullBroker = brokers.entrySet().stream().min(Comparator.comparing(entry -> entry.getValue().size()))
						.map(e -> e.getKey()).orElse(null);
				BrokerUIInterface broker = fullBroker.getUIInterface();
				int studentId = broker.getStudentID(login);
				data = new StudentData(broker, studentLoginToBroker.size(), studentId);
				log.log("New student: %s --> %d --> %d", login, data.id, data.localId);
				studentLoginToBroker.put(login, data);
				
				studentIdToBroker.put(data.id, data);
				brokers.get(fullBroker).add(data.id);
			}
			return data.id;
		}
	}

	public void setParsonsEvaluation(ParsonsEvaluation eval) throws RemoteException {
		synchronized (studentLoginToBroker) {
			StudentData data = this.studentIdToBroker.get(eval.studentId);
			eval.studentId = data.localId;
			data.ui.setParsonsEvaluation(eval);
		}
	}

	@Override
	public Stats getStudentStats(int studentId) throws RemoteException {
		synchronized (studentLoginToBroker) {
			StudentData data = this.studentIdToBroker.get(studentId);
			return data.ui.getStudentStats(data.localId);
		}
	}

	@Override
	public BrokerUIInterface getUIInterface() {
		return this;
	}

	@Override
	public BrokerEAInterface getEAInterface() {
		return this;
	}

	@Override
	public Library getLib() {
		return lib;
	}

	@Override
	public boolean startFresh() {
		return brokers.entrySet().stream().allMatch(b -> b.getKey().getEAInterface().startFresh());
	}

	@Override
	public void setGenotypes(List<ParsonsGenotype> genotype, int generation) {
		brokers.entrySet().stream().forEach(kv -> kv.getKey().getEAInterface().setGenotypes(genotype, generation));
	}

	@Override
	public void setFitnessConsumer(Consumer<ParsonsFitness> consumer) {
		brokers.entrySet().stream().forEach(kv -> kv.getKey().getEAInterface().setFitnessConsumer(consumer));
	}

}


