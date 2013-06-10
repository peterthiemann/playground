package delta;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dubbel extends DD<Integer> {

	private final static Logger logger = Logger.getLogger(Dubbel.class.getName());

    public int test(List<Integer> config) {

    	Integer last = Integer.valueOf(-1);
    	int pos = 0;
    	while (pos < config.size()) {
    		if ((config.get(pos)).equals(last)) {
    			return FAIL;
    		}
    		last = config.get(pos);
    		pos++;
    	}	
    	return PASS;
    }

    public static final void main(final String[] args) {
    	logger.setLevel(Level.FINEST);
    	logger.finer("1: " + logger.getLevel().toString());
    	logger.entering("Dubbel", "main");
    	
    	Dubbel t = new Dubbel();
    	logger.info("3: " + logger.getLevel().toString());

    	int[] s = {1,3,5,3,9,17,44,3,6,1,1,0,44,1,44,0};
    	LinkedList<Integer> l = new LinkedList<Integer>();
    	for (int i = 0; i < s.length; i++) {
    		logger.log(Level.FINER, "Now adding " + s[i]);
    		l.add(Integer.valueOf(s[i]));
    	}

    	System.out.println(l);
    	System.out.println("==> " + t.ddmin(l));
    }

}
