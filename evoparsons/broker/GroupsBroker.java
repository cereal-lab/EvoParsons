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
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
		public final int localId;
		public StudentData(String ui, int id, int localId) {
			this.ui = ui;
			this.id = id;
			this.localId = localId;
		}
	}	
	private Log log;
	private Library lib;
	private Map<String, Broker> brokers;
	private Map<String, HashSet<Integer>> brokerStudents;
	private Map<Integer, StudentData> studentIdToBroker;
	private Map<String, Integer> loginToStudentId;
	private String groupsFile;
	private String name;
	private static class SerializedData implements Serializable {
		private static final long serialVersionUID = 1L;
		public final Map<String, HashSet<Integer>> brokerStudents;
		public final Map<Integer, StudentData> studentIdToBroker;
		public final Map<String, Integer> loginToStudentId;
		public SerializedData(Map<String, HashSet<Integer>> brokerStudents,  Map<Integer, StudentData> studentIdToBroker, Map<String, Integer> loginToStudentId) {
			this.brokerStudents = brokerStudents;
			this.studentIdToBroker = studentIdToBroker;
			this.loginToStudentId = loginToStudentId;
		}
	}

	public GroupsBroker(Config config, Broker parent) {
		this.log = config.getLog();
		name = config.get("evoparsons.broker.name", "GroupsBroker");
		groupsFile = 
			Paths.get(config.getOutputFolder(),
				config.get("evoparsons.broker.file", DEFAULT_GROUPS_BROKER_FILE))
				.toString();
		SerializedData data = Utils.<SerializedData>loadFromFile(log, groupsFile, () -> new SerializedData(new HashMap<>(), new HashMap<>(), new HashMap<>()));
		brokerStudents = data.brokerStudents;
		studentIdToBroker = data.studentIdToBroker;
		loginToStudentId = data.loginToStudentId;
		this.lib = config.<Library>getInstanceOpt("evoparsons.lib", config).orElse(null);
		List<Broker> brokers = 
			config.getList("evoparsons.broker.child.")
				.stream()
				.map(cf -> Config.FromFile(config, cf).init(this))
				.collect(Collectors.toList());
		brokers.stream().forEach(broker -> {
			if (!brokerStudents.containsKey(broker.getName()))
				brokerStudents.put(broker.getName(), new HashSet<>());
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

	private <T> T exec(int studentId, ExecF<T> f) throws RemoteException
	{
		final StudentData data = studentIdToBroker.get(studentId);
		if (data == null) {
			log.err("[GroupsBroker] No group for student %d", studentId);
			return null; //TODO think
		}
		final Broker broker = brokers.get(data.ui);
		if (broker == null) {
			log.err("[GroupsBroker] No broker for student %d. Name: %s", studentId, data.ui);
			return null;
		}
		return f.e(broker, data);
	}

	@Override
	public synchronized ParsonsPuzzle getParsonsPuzzle(int studentId) throws RemoteException {
		return exec(studentId, (b, data) -> 
				b.getUIInterface().getParsonsPuzzle(data.localId));
	}

	private int allocateStudent(String login) throws RemoteException {
		String selectedBrokerName = brokerStudents.entrySet().stream()
			.min(Comparator.comparing(entry -> entry.getValue().size()))
			.map(e -> e.getKey()).orElse(null);
		Broker fullBroker = brokers.get(selectedBrokerName);
		BrokerUIInterface broker = fullBroker.getUIInterface();
		int localId = broker.getStudentID(login);
		StudentData data = new StudentData(selectedBrokerName, brokerStudents.size(), localId);
		log.log("New student: %s --> %d --> %d [%s]", login, data.id, data.localId, selectedBrokerName);
		loginToStudentId.put(login, data.id);			
		studentIdToBroker.put(data.id, data);
		brokerStudents.get(selectedBrokerName).add(data.id);
		save();
		return data.id;
	}

	@Override
	public synchronized int getStudentID(String login) throws RemoteException {
		Integer studentId = loginToStudentId.get(login);
		//StudentData data = studentLoginToBroker.get(login);
		if (studentId == null) {
			return allocateStudent(login);
		} else {
			StudentData data = studentIdToBroker.get(studentId);
			if (data == null)
			{
				log.err("[GroupsBroker.getStudentID] student with id %d,%s does not have data. Realocating", studentId, login);
				loginToStudentId.remove(login);
				return allocateStudent(login);
			} else {
				log.log("Reconnect: %s --> %d --> %d [%s]", login, data.id, data.localId, data.ui);
				return data.id;
			}
		}
		
	}

	public synchronized void setParsonsEvaluation(ParsonsEvaluation eval) throws RemoteException {
		exec(eval.studentId, (b, data) -> 
			{
				eval.studentId = data.localId;
				b.getUIInterface().setParsonsEvaluation(eval);
				return 0;
			});
	}

	@Override
	public synchronized Stats getStudentStats(int studentId) throws RemoteException {
		return exec(studentId, (b, data) -> 
				b.getUIInterface().getStudentStats(data.localId));
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
		SerializedData data = new SerializedData(brokerStudents, studentIdToBroker, loginToStudentId);
		Utils.saveToFile(log, data, groupsFile);
	}


}


