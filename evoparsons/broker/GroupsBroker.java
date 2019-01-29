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
	private Map<Broker, HashSet<Integer>> brokers;
	private Log log;
	private Library lib;
	private Map<String, BrokerUIInterface> studentLoginToBroker;
	private Map<Integer, BrokerUIInterface> studentIdToBroker;

	public GroupsBroker(Config config, Broker parent) {
		this.log = config.getLog();
		this.lib = config.<Library>getInstanceOpt("evoparsons.lib").orElse(null);
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
			BrokerUIInterface broker = studentIdToBroker.get(studentId);
			// TODO: check for null if necessary
			return broker.getParsonsPuzzle(studentId);
		}
	}

	@Override
	public int getStudentID(String login) throws RemoteException {
		synchronized (studentLoginToBroker) {
			BrokerUIInterface broker = studentLoginToBroker.get(login);
			if (broker == null) {
				Broker fullBroker = brokers.entrySet().stream().min(Comparator.comparing(entry -> entry.getValue().size()))
						.map(e -> e.getKey()).orElse(null);
				broker = fullBroker.getUIInterface();
				studentLoginToBroker.put(login, broker);
				int studentId = broker.getStudentID(login);
				studentIdToBroker.put(studentId, broker);
				brokers.get(fullBroker).add(studentId);
				return studentId;
			} else
				return broker.getStudentID(login);
		}
	}

	public void setParsonsEvaluation(ParsonsEvaluation eval) throws RemoteException {
		synchronized (studentLoginToBroker) {
			BrokerUIInterface broker = this.studentIdToBroker.get(eval.studentId);
			broker.setParsonsEvaluation(eval);
		}
	}

	@Override
	public Stats getStudentStats(int studentId) throws RemoteException {
		synchronized (studentLoginToBroker) {
			BrokerUIInterface broker = this.studentIdToBroker.get(studentId);
			return broker.getStudentStats(studentId);
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


