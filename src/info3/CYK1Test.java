package info3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CYK1Test {

	private CNF grammar;

	@Before
	public void setUp() throws Exception {
		grammar = new CNF();
		grammar.add(0, new Bin_RHS(1, 2));
		grammar.add(1, new Bin_RHS(4, 5)).add(1, new Bin_RHS(4, 3));
		grammar.add(2, new Term_RHS('c')).add(2, new Bin_RHS(6, 2));
		grammar.add(3, new Bin_RHS(1, 5));
		grammar.add(4, new Term_RHS('a'));
		grammar.add(5, new Term_RHS('b'));
		grammar.add(6, new Term_RHS('c'));
	}

	@Test
	public void test() {
		String input = "aaabbbcc";
		boolean result = CYK1.parse(grammar, input);
		assertTrue(result);
		
		input = "aabbccccc";
		result = CYK1.parse(grammar, input);
		assertTrue(result);
		
		input = "ababa";
		result = CYK1.parse(grammar, input);
		assertFalse(result);
		
		input = "aaaaaaaabbbbbbbbc";
		result = CYK1.parse(grammar, input);
		assertTrue(result);
	}

}
