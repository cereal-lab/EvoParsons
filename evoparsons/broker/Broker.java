package evoparsons.broker;

import java.util.List;
import java.util.function.Consumer;

public interface Broker {
    public void setGenotypes(List<ParsonsGenotype> genotype, int generation);    
    public ParsonsGenotypeIndex getGenotypeStore();
    public Log getLog();
    public void setFitnessConsumer(Consumer<ParsonsFitness> consumer);    
}