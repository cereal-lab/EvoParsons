package evoparsons.broker;

public interface EAStarter {
    public abstract void runFresh();
    public abstract void runCheckpoint();    
}