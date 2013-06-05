package testing;

import static org.junit.Assert.*;

import org.junit.Test;

public class Ex1Test {

	@Test
	public void testFind_min() {
		int[] a = {5, 1, 7};
		int res = Ex1.find_min(a);
		assertEquals("Minimum of " + a, 1, res);
	}

	@Test
	public void testInsert() {
		int x[] = {2, 7};
		int n = 6;
		int res[] = Ex1.insert(x, n);
		int expected[] = {2, 6, 7};
		assertArrayEquals("Inserting "+n+" into "+x, expected, res);
	}

}
