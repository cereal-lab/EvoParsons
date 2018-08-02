package evoparsons.psifx.models;

public class Stats {
    public final int numCorrectPuzzles;
    public final int numTotalPuzzles;
    public final long timeSpan;
    public Stats(int numCorrectPuzzles, int numTotalPuzzles, long timeSpan) {
        this.numCorrectPuzzles = numCorrectPuzzles;
        this.numTotalPuzzles = numTotalPuzzles;
        this.timeSpan = timeSpan;
    }
}