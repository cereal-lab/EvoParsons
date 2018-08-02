package evoparsons.broker;

// RMI-related imports 
import java.rmi.RemoteException;// RMI-related imports 
import java.util.List;
import java.util.function.Consumer;

import evoparsons.rmishared.BrokerInterface;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import evoparsons.rmishared.Stats;

public class ParsonsBroker implements Broker, BrokerInterface
{
	private ParsonsGenotypeIndex genotypeStore;
	private EvaluationDataStore evalStore;
	private ParsonsLibrary lib;
	private SelectionPolicy selectionPolicy;
	private Log log;
	private Consumer<ParsonsFitness> fitnessConsumer = 
		f -> {
			log.log("[ParsonsBroker.fitnessConsumer] discarding fitness: %s", f.toString());
		};

	public ParsonsBroker(Log log, ParsonsGenotypeIndex genotypeStore, EvaluationDataStore evalStore, 
			ParsonsLibrary lib, SelectionPolicy selectionPolicy) {
		this.genotypeStore = genotypeStore;
		this.evalStore = evalStore;
		this.lib = lib;
		this.log = log;
		this.selectionPolicy = selectionPolicy;
	}

	@Override
	public void setFitnessConsumer(Consumer<ParsonsFitness> consumer) {
		this.fitnessConsumer = consumer;
	}	

    public ParsonsPuzzle getParsonsPuzzle(int studentId) throws RemoteException
    {	
		try {
		synchronized (evalStore)	
		{
			ParsonsPuzzle puzzle = evalStore.getPuzzle(studentId, selectionPolicy, lib);
			evalStore.printInteractions(lib);
			return puzzle;
		}
		} catch (Exception e) {
			log.err("[ParsonsBroker.getParsonsPuzzle] Error: %s", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
    public int getStudentID(String login) throws RemoteException
	{
		try {
		synchronized (evalStore)
		{
			int studentId = evalStore.addStudent(login);		
			evalStore.saveStudents();
			evalStore.saveStudentStats();
			return studentId;
		}	
		} catch (Exception e) {
			log.err("[ParsonsBroker.getStudentID] Error: %s", e.getMessage());
			e.printStackTrace();
			throw e;			
		}
	}

	public void setParsonsEvaluation(ParsonsEvaluation eval) throws RemoteException
	{				
		try {
			log.log("[ParsonsBroker.setParsonsEvaluation] %s", eval.toString());		
			ParsonsFitness fitness;
			synchronized (evalStore)
			{
				evalStore.addEvaluation(eval, selectionPolicy);	
				evalStore.saveGenotypes();			
				evalStore.saveStudentStats();			
				evalStore.printInteractions(lib);			
				fitness = evalStore.getFitness(eval, lib);			
			}
			if (fitness != null)
				fitnessConsumer.accept(fitness); 	
		} catch (Exception e) {
			log.err("[ParsonsBroker.setParsonsEvaluation] Error: %s", e.getMessage());
			e.printStackTrace();
			throw e;						
		}
	}

	@Override
	public void setGenotypes(List<ParsonsGenotype> genotypes, int generation)
	{		
        //log("Received genotype { hashvalue = " + o.getHashValue() + " }");
        synchronized (evalStore)
        {
			evalStore.addGenotypes(genotypes, generation);	
			evalStore.printInteractions(lib);
            evalStore.saveGenotypes();	            
        }
	}

	@Override
	public ParsonsGenotypeIndex getGenotypeStore() {
		return genotypeStore;
	}

	@Override
	public Log getLog() {
		return log;
	}

	@Override
	public Stats getStudentStats(int studentId) throws RemoteException {
		synchronized (evalStore) {
			return evalStore.getStudentStats(studentId);
		}
	}

}


