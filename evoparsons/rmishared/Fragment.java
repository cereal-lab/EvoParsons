package evoparsons.rmishared;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@SuppressWarnings("serial")
public class Fragment implements Serializable {
	final public int index;
	final public List<String> lines;
	final public Optional<Integer> distracterId;
	public Fragment(List<String> lines, int index, Optional<Integer> distracterId) {
		this.lines = lines;
		this.index = index;
		this.distracterId = distracterId;
	}
}
