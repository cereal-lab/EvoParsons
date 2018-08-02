package evoparsons.rmishared;

import java.io.*;

/**
 * This class is used to pass the results of the student evaluations of programs back to the broker.
 * @author paul
 */
 
public class ParsonsEvaluation implements Serializable{
	private static final long serialVersionUID = 1L;
	public final int studentId;
	public final double fitness;
	public final int puzzleIndex;
	public final long timeInMs;
	public final boolean gaveUp;	
	public long timestamp;
	public void setTimeStamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public ParsonsEvaluation(int studentId, int puzzleIndex, double fitness, long timeInMs, boolean gaveUp, long timestamp) {
		this.studentId = studentId;
		this.puzzleIndex = puzzleIndex;
		this.fitness = fitness;
		this.timeInMs = timeInMs;
		this.gaveUp = gaveUp;
		this.timestamp = timestamp;
	}   
	
	@Override
	public String toString() {
		return String.format("{'studentId':%d,'puzzleId':%d,'fitness':%f,'timeInMs':%d,'gaveUp':%b}", studentId, puzzleIndex, fitness, timeInMs, gaveUp);
	}
}
