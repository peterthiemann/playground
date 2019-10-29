package info3.regexp;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RNull implements Regexp {

	@Override
	public String toString() {
		return "RNull []";
	}

	@Override
	public boolean nullable() {
		return false;
	}

	@Override
	public Set<Regexp> step(char c) {
		return new HashSet<Regexp>();
	}

	@Override
	public Set<String> generate(int nr, int size) {
		return Collections.emptySet();
	}

}
