package evoparsons.broker;

public interface EAStarter {
    public abstract void runFresh(String config);
    public abstract void runCheckpoint(String config);    
}