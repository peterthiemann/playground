package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Ex2_SetTest {
	private Ex2_Set<String> s, s2;

	@Before
	public void setUp() throws Exception {
	    s = new Ex2_Set<String>();
	    s.add("one"); s.add("two");
	    s2 = new Ex2_Set<String>();
	    s2.add("two"); s2.add("three");
	}
	
	private void testset(String[] exp, Ex2_Set<String> s) {
	    assertTrue(s.size() == exp.length);
	    for (int i = 0; i < s.size(); i++) {
	      assertTrue(s.member(exp[i]));
	    }
	}

	@Test
	public void testUnion() {
	    s.union(s2);
	    String[] exp = {"one", "two", "three"};
	    testset(exp, s);
	}

}
