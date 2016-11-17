package info3.regexp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class MatchTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String w;
		Regexp r;
		
		w = "";
		r = new REmpty();
		assertEquals(true, Match.match(w, r));
	}

}
