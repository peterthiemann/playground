package info3.regexp;

import java.util.HashSet;
import java.util.Set;

public class RAlt implements Regexp {
	private Regexp r,s;

	public RAlt(Regexp r, Regexp s) {
		this.r = r;
		this.s = s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
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
		RAlt other = (RAlt) obj;
		if (r == null) {
			if (other.r != null)
				return false;
		} else if (!r.equals(other.r))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RAlt [r=" + r + ", s=" + s + "]";
	}

	@Override
	public boolean nullable() {
		return r.nullable() || s.nullable();
	}

	@Override
	public Set<Regexp> step(char c) {
		Set<Regexp> result = new HashSet<Regexp>();
		result.addAll(r.step(c));
		result.addAll(s.step(c));
		return result;
	}

	@Override
	public Set<String> generate(int nr, int size) {
		Set<String> rgen = r.generate(1, 1);
		Set<String> sgen = s.generate(1, 1);
		rgen.addAll(sgen);
		return rgen;
	}
	
}
