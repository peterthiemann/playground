package info3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TriangleTest {
	
	@Test
	public void test() {
		int n = 10;
		Triangle<Integer> out = new Triangle<Integer>(n);
		out.set(0, 0, 42);
		assertEquals(new Integer(42), out.get(0, 0));
		out.set(0, 9, 43);
		assertNull(out.get(1, 1));
		out.set(1, 9, 44);
		assertNull(out.get(2, 2));
		out.set(2, 9, 45);
		assertNull(out.get(3, 3));
		out.set(4, 4, 46);
		assertNull(out.get(3, 0));
		out.set(9, 9, 47);
		assertEquals(new Integer(47), out.get(9, 9));
	}

}
