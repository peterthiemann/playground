/**
 * 
 */
package testing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author thiemann
 *
 */
public class Ex1aTest {

	/**
	 * Test method for {@link testing.Ex1a#find_min(int[])}.
	 */
	@Test
	public void testFind_min() {
		int [] a1 = {1};
		int r1 = Ex1a.find_min(a1);
		assertEquals(1, r1);
		
		int [] a2 = {1,2,3,4};
		int r2 = Ex1a.find_min(a2);
		assertEquals(1, r2);
		
		int [] a3 = {3,2,1};
		int r3 = Ex1a.find_min(a3);
		assertEquals(1, r3);
		
		int [] a4 = {1, -1, 0};
		int r4 = Ex1a.find_min(a4);
		assertEquals(-1, r4);
		
		int [] a5 = {Integer.MAX_VALUE, Integer.MIN_VALUE};
		int r5 = Ex1a.find_min(a5);
		assertEquals(Integer.MIN_VALUE, r5);
		
	}
	
	@Test(expected=Exception.class)
	public void testFind_min_null() {
		int [] a = null;
		int r = Ex1a.find_min(a);
	}
	
	@Test(expected=Exception.class)
	public void testFind_min_empty() {
		int [] a =  {};
		int r = Ex1a.find_min(a);
	}

	/**
	 * Test method for {@link testing.Ex1a#insert(int[], int)}.
	 */
	@Test
	public void testInsert() {
		int [] x1 = {};
		int n1 = 0;
		int [] r1 = Ex1a.insert(x1, n1);
		int [] e1 = {0};
		assertArrayEquals(e1, r1);
		
		int [] x2 = {1};
		int n2 = 0;
		int [] r2 = Ex1a.insert(x2, n2);
		int [] e2 = {0,1};
		assertArrayEquals(e2, r2);
		
		int [] x3 = {1,3,5};
		int n3 = 4;
		int [] r3 = Ex1a.insert(x3, n3);
		int [] e3 = {1,3,4,5};
		assertArrayEquals(e3, r3);
		
		int [] x4 = {1,3,3,5};
		int n4 = 4;
		int [] r4 = Ex1a.insert(x4, n4);
		int [] e4 = {1,3,3,4,5};
		assertArrayEquals(e4, r4);
		
		int [] x5 = {1,2,0};
		int n5 = 3;
		int [] r5 = Ex1a.insert(x5, n5);
		int [] e5 = {1,2,0,3};
		assertArrayEquals(e5, r5);
		
		int [] x6 = {4,3,2};
		int n6 = 2;
		int [] r6 = Ex1a.insert(x6, n6);
		int [] e6 = {2,4,3,2};
		assertArrayEquals(e6, r6);
	}
	
	private static final int ITERATIONS = 10000;
	
	@Test
	public void testInsert_randomly() {
		// test for ITERATIONS
		for(int i=1; i<ITERATIONS; i++) {
			testInsert_sortedInput();
		}
	}

	private void testInsert_sortedInput() {
		int x [] = generateRandomSortedArray();
		int n = generateRandomInteger();
		int r [] = Ex1a.insert(x, n);
		assertTrue(isSorted(r));
		assertTrue(x.length + 1 == r.length);
		int testedForLessThanN = -1;
		for(int i=0; i<x.length; i++) {
			if(x[i]<=n) {
				assertTrue(x[i] == r[i]);
				testedForLessThanN = i;
			} else {
				assertTrue(x[i] == r[i+1]);
			}
		}
		assertTrue(r[testedForLessThanN+1]==n);
	}

	private static final int MAX_ARRAY_SIZE = 17;
	private int[] generateRandomSortedArray() {
		int arraySize = (int)(Math.random() * MAX_ARRAY_SIZE);
		int [] r = new int[arraySize];
		int previousMax = Integer.MIN_VALUE;
		for(int i=0; i<arraySize; i++) {
			r[i] = previousMax = Math.max(previousMax, generateRandomInteger());
		}
		return r;
	}

	private boolean isSorted(int[] r) {
		int previousMax = Integer.MIN_VALUE;
		for(int i=0; i<r.length; i++) {
			if(r[i]>=previousMax) {
				previousMax = r[i];
			} else {
				return false;
			}
		}
		return true;
	}

	private int generateRandomInteger() {
		double minint = (double) Integer.MIN_VALUE;
		double maxint = (double) Integer.MAX_VALUE;
		double span = maxint + 1 - minint;
		return (int) Math.rint(Math.random() * span - minint);
	}

}
