package delta;

import java.util.LinkedList;
import java.util.List;

public class GeeGG extends DD<Character> {

    public int test(List<Character> config) {

    int eCount = 0;
	int gCount = 0;
	int pos = 0;
	while (pos < config.size()) {
		char c = config.get(pos);
	    if ( c == 'e' ) {
		eCount++;
		if (eCount == 2) {return FAIL;}
	    }
	    if ( c == 'g' ) {
		gCount++;
		if (gCount == 3) {return FAIL;}
	    }
	    pos++;
	}
	return PASS;
    }

    public static final void main(final String[] args) {
    	GeeGG t = new GeeGG();

    	String s = "a-debugging-exam";
    	LinkedList<Character> l = new LinkedList<Character>();
    	for (int i = 0; i < s.length(); i++) {
    		l.add(Character.valueOf(s.charAt(i)));
    	}

    	System.out.println(l);
    	System.out.println("==> " + t.ddmin(l));
    }

}
