package evoparsons.repo;

public class Attempt {
    public final String sid; 
    public final String puzzleId;
    public final String attemptId; 
    public final String attempt;
    public Attempt(String sid, String puzzleId, String attemptId, String attempt) {
        this.sid = sid;
        this.puzzleId = puzzleId;
        this.attemptId = attemptId;
        this.attempt = attempt;
    }
}