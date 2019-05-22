package evoparsons.repo;

public class Events {
    public final String sid; 
    public final int puzzleId; 
    public final String events;
    public Events(String sid, int puzzleId, String events) {
        this.sid = sid;
        this.puzzleId = puzzleId;
        this.events = events;
    }
}