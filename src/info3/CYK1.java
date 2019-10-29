package info3;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CYK1 {

	public static boolean parse(CNF grammar, String input) {
		int n = input.length();
		UpperTriangle<Set<Integer>> m = new UpperTriangle(n);
		
		for (int i = n - 1; i >= 0; i--) {
			// initialize diagonal
			char c_i = input.charAt(i);
			Set<Integer> mii = new HashSet<Integer>();
			for (Map.Entry<Integer, Set<RHS>> prod : grammar.productions.entrySet()) {
				for (RHS rhs : prod.getValue()) {
					if (rhs.rhs_is_c(c_i))
						mii.add(prod.getKey());
				}
			}
			m.set(i, i, mii);
			
			// proceed left to right through rest of line
			for (int j = i + 1; j < n; j++) {
				Set<Integer> mij = new HashSet<Integer>();
				for (int k = i; k < j; k++) {
					for (Map.Entry<Integer, Set<RHS>> prod : grammar.productions.entrySet()) {
						int lhs = prod.getKey();
						for (RHS rhs : prod.getValue()) {
							if (rhs instanceof Bin_RHS) {
								Bin_RHS bin_rhs = (Bin_RHS)rhs;
								if (m.get(i, k).contains(bin_rhs.left) && m.get(k+1, j).contains(bin_rhs.right))
									mij.add(lhs);
							}
						}
					}
				}
				m.set(i, j, mij);
			}
		}
		return m.get(0, n-1).contains(0);
	}

}
