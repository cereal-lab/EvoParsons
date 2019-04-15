package evoparsons.rmishared;

import java.io.*;

/**
 * This class is used to pass the results of the student evaluations of programs back to the broker.
 * @author paul
 */
 
public class ParsonsEvaluation implements Serializable{
	private static final long serialVersionUID = 1L;
	public String sid;
	public final int moves;
	public final double fitness;
	public final int puzzleIndex;
	public final long timeInMs;
	public final boolean gaveUp;	
	public long timestamp;
	public void setTimeStamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public ParsonsEvaluation(String sid, int puzzleIndex, int moves, long timeInMs, double fitness, boolean gaveUp, long timestamp) {
		this.sid = sid;
		this.puzzleIndex = puzzleIndex;
		this.moves = moves;
		this.fitness = fitness;//!important - fitness is decided on client - it depends on UI
		this.timeInMs = timeInMs;
		this.gaveUp = gaveUp;
		this.timestamp = timestamp;
	}   
	
	@Override
	public String toString() {
		return String.format("{'sid':'%s','puzzleId':%d,'moves':%d,'timeInMs':%d,'fitness':%.2f,'gaveUp':%b}", sid, puzzleIndex, moves, timeInMs, fitness, gaveUp);
	}
}
