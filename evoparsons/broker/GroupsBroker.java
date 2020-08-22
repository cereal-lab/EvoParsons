package evoparsons.broker;

import java.io.Serializable;
import java.nio.file.Paths;
// RMI-related imports 
import java.rmi.RemoteException;// RMI-related imports 
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import evoparsons.rmishared.Auth;
import evoparsons.rmishared.BrokerUIInterface;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import evoparsons.rmishared.Stats;

public class GroupsBroker implements Broker, BrokerUIInterface, BrokerEAInterface {
	
	private static final String DEFAULT_GROUPS_BROKER_FILE = "groups.bro";

	private static class StudentData implements Serializable {
		private static final long serialVersionUID = 1L;
		public final String ui;
		public final int id;
		public final Auth localAuth;
		public StudentData(String ui, int id, Auth localAuth) {
			this.ui = ui;
			this.id = id;
			this.localAuth = localAuth;
		}
	}	

	private Log log;
	private Library lib;
	private Map<String, Broker> brokers;
	private Map<String, HashSet<String>> brokerToSids;
	private Map<String, StudentData> sidToBroker;
	private String groupsFile;
	private String name;
	private static class SerializedData implements Serializable {
		private static final long serialVersionUID = 1L;
		public final Map<String, HashSet<String>> brokerToSids;
		public final Map<String, StudentData> sidToBroker;
		public SerializedData(Map<String, HashSet<String>> brokerToSids,  Map<String, StudentData> sidToBroker) {
			this.brokerToSids = brokerToSids;
			this.sidToBroker = sidToBroker;
		}
	}

	public GroupsBroker(Config config, Broker parent) {
		this.log = config.getLog();
		name = config.get("broker.name", "GroupsBroker");
		groupsFile = 
			Paths.get(config.getOutputFolder(),
				config.get("broker.file", DEFAULT_GROUPS_BROKER_FILE))
				.toString();
		SerializedData data = Utils.<SerializedData>loadFromFile(log, groupsFile, () -> new SerializedData(new HashMap<>(), new HashMap<>()));
		brokerToSids = data.brokerToSids;
		sidToBroker = data.sidToBroker;
		this.lib = config.<Library>getInstanceOpt("lib", config).orElse(null);
		String childKey = "broker.child.";		
		Set<String> childBrokerPrefixes = new HashSet<>();
		List<String> childKeys = config.getKeyList(childKey);
		childKeys
			.stream().forEach(key -> {
				String[] parts = key.substring(childKey.length()).split("\\.");
				if (parts.length > 1)
					childBrokerPrefixes.add(childKey + parts[0] + ".");
			});
		List<Broker> brokers = 
			(childBrokerPrefixes.size() > 0) ?
				childBrokerPrefixes.stream()
					.map(prefix -> config.getSubconfig(prefix).init(this))
					.collect(Collectors.toList())
			: config.getList(childKey)
				.stream()
				.map(cf -> Config.FromFile(config, cf).init(this))
				.collect(Collectors.toList());
		brokers.stream().forEach(broker -> {
			if (!brokerToSids.containsKey(broker.getName()))
				brokerToSids.put(broker.getName(), new HashSet<>());
		});
		if (brokers.size() == 0) {
			log.err("[GroupsBroker] evoparsons.broker.child was not specified");
			System.exit(1);
		}		
		this.brokers = brokers.stream().collect(Collectors.toMap(b -> b.getName(), b -> b));
	}

	interface ExecF<T> {
		public T e(Broker broker, StudentData data) throws RemoteException;
	}

	private <T> T exec(String sid, ExecF<T> f) throws RemoteException
	{
		final StudentData data = sidToBroker.get(sid);
		if (data == null) {
			log.err("[GroupsBroker] No group for student %s", sid);
			return null; //TODO think
		}
		final Broker broker = brokers.get(data.ui);
		if (broker == null) {
			log.err("[GroupsBroker] No broker for student %s. Name: %s", sid, data.ui);
			return null;
		}
		return f.e(broker, data);
	}

	@Override
	public synchronized ParsonsPuzzle getParsonsPuzzle(String sid) throws RemoteException {
		return exec(sid, (b, data) -> b.getUIInterface().getParsonsPuzzle(data.localAuth.getSid()));
	}

	private Student allocateStudent(String sid, String ssig, String skey) throws RemoteException {
		String selectedBrokerName = brokerToSids.entrySet().stream()
			.min(Comparator.comparing(entry -> entry.getValue().size()))
			.map(e -> e.getKey()).orElse(null);
		Broker fullBroker = brokers.get(selectedBrokerName);
		BrokerUIInterface broker = fullBroker.getUIInterface();
		Student student = broker.authenticateStudent(sid, ssig, skey);				
		if (student == null) return null;
		Auth auth = student.getAuth();
		StudentData data = new StudentData(selectedBrokerName, sidToBroker.size(), auth);
		//Auth auth = new Auth(sid, ssig, skey);
		log.log("New student: %s [%s]", auth.getSid(), selectedBrokerName);
		sidToBroker.put(auth.getSid(), data);
		brokerToSids.get(selectedBrokerName).add(auth.getSid());
		save();
		return student;
	}

	@Override
	public synchronized Student authenticateStudent(String sid, String ssig, String skey) throws RemoteException {
		StudentData data = sidToBroker.get(sid);
		if (data == null) {
			log.err("[GroupsBroker.getStudent] student with id %s does not have data. Creating", sid);
			return allocateStudent(sid, ssig, skey);
		} 
		final Broker broker = brokers.get(data.ui);		
		log.log("Reconnect: %s [%s]", sid, data.ui);
		Student student = broker.getUIInterface().authenticateStudent(sid, ssig, skey);
		//data.localAuth.setSkey(skey);
		return student;
	}

	public synchronized void setParsonsEvaluation(ParsonsEvaluation eval) throws RemoteException {
		exec(eval.sid, (b, data) -> 
			{
				b.getUIInterface().setParsonsEvaluation(eval);
				return 0;
			});
	}

	@Override
	public synchronized Stats getStudentStats(String sid) throws RemoteException {
		return exec(sid, (b, data) -> 
				b.getUIInterface().getStudentStats(data.localAuth.getSid()));
	}

	// @Override
	// public synchronized int recordAttempt(String sid, String puzzleId) throws RemoteException {
	// 	return 
	// 		exec(sid, (b, data) -> {
	// 			int attempt = b.getUIInterface().recordAttempt(data.localAuth.getSid(), puzzleId);
	// 			return attempt;
	// 		});		
	// }

	@Override
	public Map<String, Stats> getStudentStats(String iid, String isig, List<String> ssig) throws RemoteException {
		final Map<String, Stats> stats = new HashMap<>();
		for (String brokerName: brokers.keySet())
		{
			var brokerStats = brokers.get(brokerName).getUIInterface().getStudentStats(iid, isig, ssig);
			brokerStats.entrySet().stream().forEach(s -> {
				if (stats.containsKey(s.getKey())) {
					Stats existingStats = stats.get(s.getKey());
					Stats newStats = s.getValue();
					existingStats.duration += newStats.duration;
					existingStats.puzzlesSeen += newStats.puzzlesSeen;
					existingStats.puzzlesSolved += newStats.puzzlesSolved;
				} else {
					stats.put(s.getKey(), s.getValue());
				}
			});
		}
		return stats;
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
		return brokers.entrySet().stream().allMatch(b -> b.getValue().getEAInterface().startFresh());
	}

	@Override
	public void setGenotypes(List<ParsonsGenotype> genotype, int generation) {
		brokers.entrySet().stream().forEach(kv -> kv.getValue().getEAInterface().setGenotypes(genotype, generation));
	}

	@Override
	public void setFitnessConsumer(Consumer<ParsonsFitness> consumer) {
		//TODO - think here
		//brokers.entrySet().stream().forEach(kv -> kv.getValue().getEAInterface().setFitnessConsumer(consumer));
	}

	@Override
	public String getName() {
		return name;
	}

	private void save()
	{
		SerializedData data = new SerializedData(brokerToSids, sidToBroker);
		Utils.saveToFile(log, data, groupsFile);
	}

}


