package info3.regexp;

import java.util.HashSet;
import java.util.Set;

public class REmpty implements Regexp {

	@Override
	public String toString() {
		return "REmpty []";
	}

	@Override
	public boolean nullable() {
		return true;
	}

	@Override
	public Set<Regexp> step(char c) {
		return new HashSet<Regexp>();
	}

	@Override
	public Set<String> generate(int nr, int size) {
		HashSet<String> r = new HashSet<String>();
		r.add("");
		return r;
	}

}
