package evoparsons.rmishared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Fragment implements Serializable {
	final public int index;
	final public String line;
	final public ParsonsPuzzle.AppliedDistracter distracter;
	public Fragment(String line, int index, ParsonsPuzzle.AppliedDistracter distracter) {
		this.line = line;
		this.index = index;
		this.distracter = distracter;
	}
}
