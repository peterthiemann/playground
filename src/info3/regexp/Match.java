package info3.regexp;

import java.util.HashSet;
import java.util.Set;

public class Match {
	static boolean match(String w, Regexp r0) {
		int len = w.length();
		int i = 0;
		Set<Regexp> rset = new HashSet<Regexp>();
		rset.add(r0);
		while (!rset.isEmpty()) {
			if (i==len) {
				return nullable(rset);
			} else {
				char c = w.charAt(i++);
				rset = step(rset, c);
			}
		}
		return false;
	}

	static private Set<Regexp> step(Set<Regexp> rset, char c) {
		Set<Regexp> sset = new HashSet<Regexp>();
		for (Regexp r : rset) {
			sset.addAll(r.step(c));
		}
		return sset;
	}

	static private boolean nullable(Set<Regexp> rset) {
		for (Regexp r : rset) {
			if (r.nullable()) 
				return true;
		}
		return false;
	}
}
