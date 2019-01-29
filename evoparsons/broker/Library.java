package evoparsons.broker;

import java.util.stream.Stream;

public interface Library
{
	public int getProgramCount();
	public int getTransformCount();
	public Program getProgram(int ProgNum);
	public Stream<Program> programStream();
	public Stream<Transform> transformStream();
	public Transform getTransform(int TransNum);	
}
