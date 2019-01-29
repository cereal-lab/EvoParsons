package evoparsons.broker;

import java.util.List;
import java.util.function.Consumer;

public interface BrokerEAInterface {
    public Library getLib();
    public boolean startFresh();
    public void setGenotypes(List<ParsonsGenotype> genotype, int generation);    
    public void setFitnessConsumer(Consumer<ParsonsFitness> consumer);    
}