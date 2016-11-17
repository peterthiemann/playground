package info3.regexp;

import java.util.HashSet;
import java.util.Set;

public class RStar implements Regexp {
	private Regexp r;

	public RStar(Regexp r) {
		this.r = r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RStar other = (RStar) obj;
		if (r == null) {
			if (other.r != null)
				return false;
		} else if (!r.equals(other.r))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RStar [r=" + r + "]";
	}

	@Override
	public boolean nullable() {
		return true;
	}

	@Override
	public Set<Regexp> step(char c) {
		Set<Regexp> result = new HashSet<Regexp>();
		for (Regexp s : r.step(c)) {
			result.add(new RSeq(s, this));
		}
		return result;
	}
	

}
