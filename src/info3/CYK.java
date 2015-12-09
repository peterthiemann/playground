package info3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CYK {
	public static boolean parse(CNF grammar, String input) {
		int n = input.length();
		Set<Integer> [][] m = new HashSet [n][n];
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				m[i][j] = new HashSet<Integer>();
		
		for (int j=0; j<n; j++) {
			char c_j = input.charAt(j);
			for (Map.Entry<Integer, Set<RHS>> prod : grammar.productions.entrySet()) {
				for (RHS rhs : prod.getValue()) {
					if (rhs.rhs_is_c(c_j))
						m[0][j].add(prod.getKey());
				}
			}
		}
		System.out.println("Line 0: " + Arrays.deepToString(m[0]));
		
		for (int i=1; i<n; i++) {
			for (int j=0; j < n-i; j++)
				for (int k=0; k < i; k++) {
					for (Map.Entry<Integer, Set<RHS>> prod : grammar.productions.entrySet()) {
						int lhs = prod.getKey();
						for (RHS rhs : prod.getValue()) {
							if (rhs instanceof Bin_RHS) {
								Bin_RHS bin_rhs = (Bin_RHS)rhs;
								if (m[k][j].contains(bin_rhs.left) && m[i-k-1][j+k+1].contains(bin_rhs.right))
									m[i][j].add(lhs);
							}
						}
					}
				}
			System.out.println("Line " + i + ": " + Arrays.deepToString(m[i]));
		}
					
		return m[n-1][0].contains(0);
	}
	
	public static void main(String[] arg) {
		CNF grammar = new CNF();
		grammar.add(0, new Bin_RHS(1, 2));
		grammar.add(1, new Bin_RHS(4, 5)).add(1, new Bin_RHS(4, 3));
		grammar.add(2, new Term_RHS('c')).add(2, new Bin_RHS(6, 2));
		grammar.add(3, new Bin_RHS(1, 5));
		grammar.add(4, new Term_RHS('a'));
		grammar.add(5, new Term_RHS('b'));
		grammar.add(6, new Term_RHS('c'));
		System.out.println("Grammar : " + grammar);
		
		String input = arg[0];
		System.out.println("Parsing input: " + input);
		
		boolean parsed = parse(grammar, input);
		
		System.out.println(parsed ? "Yes" : "No");
	}

}
