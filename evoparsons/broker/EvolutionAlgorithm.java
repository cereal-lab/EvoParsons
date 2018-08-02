package evoparsons.broker;

public interface EvolutionAlgorithm {
    //public static EvolutionAlgorithm Current;
    public abstract void runFresh(String config);
    public abstract void runCheckpoint(String config);    
    public abstract void setFitness(ParsonsFitness fitness);
    public abstract Broker getBroker();    

}