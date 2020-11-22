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
		public StudentData(final String ui, final int id, final Auth localAuth) {
			this.ui = ui;
			this.id = id;
			this.localAuth = localAuth;
		}
	}	

	private final Log log;
	private final Library lib;
	private final Map<String, Broker> brokers;
	private final Map<String, HashSet<String>> brokerToSids;
	private final Map<String, StudentData> sidToBroker;
	private final String groupsFile;
	private final String name;
	private static class SerializedData implements Serializable {
		private static final long serialVersionUID = 1L;
		public final Map<String, HashSet<String>> brokerToSids;
		public final Map<String, StudentData> sidToBroker;
		public SerializedData(final Map<String, HashSet<String>> brokerToSids,  final Map<String, StudentData> sidToBroker) {
			this.brokerToSids = brokerToSids;
			this.sidToBroker = sidToBroker;
		}
	}

	public GroupsBroker(final Config config, final Broker parent) {
		this.log = config.getLog();
		name = config.get("broker.name", "GroupsBroker");
		groupsFile = 
			Paths.get(config.getOutputFolder(),
				config.get("broker.file", DEFAULT_GROUPS_BROKER_FILE))
				.toString();
		final SerializedData data = Utils.<SerializedData>loadFromFile(log, groupsFile, () -> new SerializedData(new HashMap<>(), new HashMap<>()));
		brokerToSids = data.brokerToSids;
		sidToBroker = data.sidToBroker;
		this.lib = config.<Library>getInstanceOpt("lib", config).orElse(null);
		final String childKey = "broker.child.";		
		final Set<String> childBrokerPrefixes = new HashSet<>();
		final List<String> childKeys = config.getKeyList(childKey);
		childKeys
			.stream().forEach(key -> {
				final String[] parts = key.substring(childKey.length()).split("\\.");
				if (parts.length > 1)
					childBrokerPrefixes.add(childKey + parts[0] + ".");
			});
		final List<Broker> brokers = 
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

	private <T> T exec(final String sid, final ExecF<T> f) throws RemoteException
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
	public synchronized ParsonsPuzzle getParsonsPuzzle(final String sid) throws RemoteException {
		return exec(sid, (b, data) -> b.getUIInterface().getParsonsPuzzle(data.localAuth.getSid()));
	}

	private Student allocateStudent(final String sid, final String ssig, final String skey) throws RemoteException {
		final String selectedBrokerName = brokerToSids.entrySet().stream()
			.min(Comparator.comparing(entry -> entry.getValue().size()))
			.map(e -> e.getKey()).orElse(null);
		final Broker fullBroker = brokers.get(selectedBrokerName);
		final BrokerUIInterface broker = fullBroker.getUIInterface();
		final Student student = broker.authenticateStudent(sid, ssig, skey);				
		if (student == null) return null;
		final Auth auth = student.getAuth();
		final StudentData data = new StudentData(selectedBrokerName, sidToBroker.size(), auth);
		//Auth auth = new Auth(sid, ssig, skey);
		log.log("New student: %s [%s]", auth.getSid(), selectedBrokerName);
		sidToBroker.put(auth.getSid(), data);
		brokerToSids.get(selectedBrokerName).add(auth.getSid());
		save();
		return student;
	}

	@Override
	public synchronized Student authenticateStudent(final String sid, final String ssig, final String skey) throws RemoteException {
		final StudentData data = sidToBroker.get(sid);
		if (data == null) {
			log.err("[GroupsBroker.getStudent] student with id %s does not have data. Creating", sid);
			return allocateStudent(sid, ssig, skey);
		} 
		final Broker broker = brokers.get(data.ui);		
		log.log("Reconnect: %s [%s]", sid, data.ui);
		final Student student = broker.getUIInterface().authenticateStudent(sid, ssig, skey);
		//data.localAuth.setSkey(skey);
		return student;
	}

	public synchronized void setParsonsEvaluation(final ParsonsEvaluation eval) throws RemoteException {
		exec(eval.sid, (b, data) -> 
			{
				b.getUIInterface().setParsonsEvaluation(eval);
				return 0;
			});
	}

	@Override
	public synchronized Stats getStudentStats(final String sid) throws RemoteException {
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
	public Map<String, List<Stats>> getStudentStats(final String iid, final String isig, final List<String> ssig, final Set<String> sid) throws RemoteException {
		// final Map<String, List<Stats>> stats = new HashMap<>();
		final Map<String, List<Stats>> stats = brokers.entrySet().stream()
			.flatMap(b -> {
				try { 	
					Set<String> sids = brokerToSids.get(b.getKey());
					return b.getValue().getUIInterface().getStudentStats(iid, isig, ssig, sids)
							.entrySet().stream();
				} catch (Exception e) {
					//should not be here
					throw new IllegalArgumentException(e);
				}
			})
			.collect(Collectors.groupingBy(s -> s.getKey()))
			.entrySet().stream()
			.collect(Collectors.toMap(g -> g.getKey(), 
				g -> g.getValue().stream().flatMap(s -> s.getValue().stream()).collect(Collectors.toList())));
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
	public void setGenotypes(final List<ParsonsGenotype> genotype, final int generation) {
		brokers.entrySet().stream().forEach(kv -> kv.getValue().getEAInterface().setGenotypes(genotype, generation));
	}

	@Override
	public void setFitnessConsumer(final Consumer<ParsonsFitness> consumer) {
		//TODO - think here
		//brokers.entrySet().stream().forEach(kv -> kv.getValue().getEAInterface().setFitnessConsumer(consumer));
	}

	@Override
	public String getName() {
		return name;
	}

	private void save()
	{
		final SerializedData data = new SerializedData(brokerToSids, sidToBroker);
		Utils.saveToFile(log, data, groupsFile);
	}

}


