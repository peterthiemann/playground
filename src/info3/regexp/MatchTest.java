package info3.regexp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MatchTest {
	Regexp r;
	
	Regexp rn = new RNull();
	Regexp re = new REmpty();
	Regexp rx = new RSym('x');
	Regexp ry = new RSym('y');
	
	@Before
	public void setUp() throws Exception {
	}

	private void checkFalse(String w) {
		String msg = "input \"" + w + "\" matched by " + r.toString();
		assertFalse(msg, Match.match(w, r));
	}

	private void checkTrue(String w) {
		String msg = "input \"" + w + "\" not matched by " + r.toString();
		assertTrue(msg, Match.match(w, r));
	}

	private void checkForSymX() {
		checkFalse("");
		checkTrue("x");
		checkFalse("xx");
		checkFalse("z");
	}

	@Test
	public void testEmpty() {
		r = new REmpty();
		checkForEmpty();
	}

	private void checkForEmpty() {
		checkTrue("");
		checkFalse("something");
	}
	
	@Test
	public void testNull() {
		r = new RNull();
		checkForNull();
	}

	private void checkForNull() {
		checkFalse("");
		checkFalse("something");
	}
	
	@Test
	public void testSym() {
		r = new RSym('x');
		checkForSymX();
	}
	
	@Test
	public void testSeq() {
		r = new RSeq(re, re);
		checkForEmpty();
		
		r = new RSeq(re, rx);
		checkForSymX();
		r = new RSeq(rx, re);
		checkForSymX();
		
		r = new RSeq(rn, re);
		checkForNull();
		r = new RSeq(rn, rn);
		checkForNull();
		r = new RSeq(rn, rx);
		checkForNull();
		r = new RSeq(re, rn);
		checkForNull();
		r = new RSeq(rx, rn);
		checkForNull();
		
		r = new RSeq(rx, rx);
		checkFalse("");
		checkFalse("x");
		checkFalse("y");
		checkFalse("xy");
		checkTrue("xx");
		checkFalse("yy");
		checkFalse("something");
		
		r = new RSeq(rx, ry);
		checkFalse("");
		checkFalse("x");
		checkFalse("y");
		checkTrue("xy");
		checkFalse("xx");
		checkFalse("yy");
		checkFalse("something");
		
	}

	@Test
	public void testStar() {
		r = new RStar(rn);
		checkForEmpty();
		
		r = new RStar(re);
		checkForEmpty();
		
		r = new RStar(rx);
		checkTrue("");
		checkTrue("x");
		checkTrue("xx");
		checkTrue("xxxxx");
		checkFalse("y");
		checkFalse("something");
	}
	
}
