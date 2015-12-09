package info3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CNF {
	Map<Integer, Set<RHS>> productions;

	public CNF() {
		this.productions = new HashMap<Integer, Set<RHS>>();
	}

	public CNF add(int i, RHS rhs) {
		Set<RHS> rhss = productions.get(i);
		if (rhss==null) {
			rhss = new HashSet<RHS>();
			productions.put(i, rhss);
		}
		rhss.add(rhs);
		return this;
	}

	@Override
	public String toString() {
		return "CNF [productions=" + productions + "]";
	}
}
