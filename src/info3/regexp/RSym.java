package info3.regexp;

import java.util.HashSet;
import java.util.Set;

public class RSym implements Regexp {
	private char x;

	public RSym(char x) {
		this.x = x;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
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
		RSym other = (RSym) obj;
		if (x != other.x)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RSym [x=" + x + "]";
	}

	@Override
	public boolean nullable() {
		return false;
	}

	@Override
	public Set<Regexp> step(char c) {
		Set<Regexp> result = new HashSet<Regexp>();
		if (c == x) {
			result.add(new REmpty());
		}
		return result;
	}

}
