package info3;

import java.util.HashSet;
import java.util.Set;

public class CYK {
	public boolean parse(CNF grammar, String input) {
		int n = input.length();
		Set<Integer> [][] m = new HashSet [n][n];
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				m[i][j] = new HashSet<Integer>();
		return true;
	}

}
