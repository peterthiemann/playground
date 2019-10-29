package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SearchTest {
	private int[] input;
	
	@Before
	public void setup() {
		input = new int[] { 1, 2 };
	}
	
	@Test
	public void test_Search1() {
		int result = Search.search(input, 1);
		assertEquals(0, result);
	}
	@Test
	public void test_Search2() {
		int result = Search.search(input, 2);
		assertEquals(1, result);
	}
	@Test
	public void test_Search4() {
		int result = Search.search(input, 4);
		assertEquals(-1, result);
	}
}
