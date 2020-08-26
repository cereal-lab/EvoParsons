package evoparsons.rmishared;

import java.io.Serializable;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
	public int puzzlesSolved; 
    public int puzzlesSeen;
    //public long start;
    public long durationMs;
    public long inactivityMs;
    //public Map<String, Integer> attemptsPerPuzzle;
    public Stats() {}
    public Stats(int puzzlesSolved, int puzzlesSeen) {
        this.puzzlesSolved = puzzlesSolved;
        this.puzzlesSeen = puzzlesSeen;
        this.durationMs = 0;
        this.inactivityMs = 0;
        //this.start = System.currentTimeMillis();
        //this.attemptsPerPuzzle = new HashMap<>();
    }
}