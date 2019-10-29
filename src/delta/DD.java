package delta;

import java.util.LinkedList;
import java.util.List;

public class DD<X> {
    public static final int FAIL       = -1;
    public static final int PASS       = +1;
    public static final int UNRESOLVED = 0;

    public static <X>List<X> minus(List<X> a, int splits, int slot) {
    	List<X> result = new LinkedList<X>();
    	int pos = 0;
    	int length = a.size() / splits;

    	while (pos < length * slot) {
    		result.add(a.get(pos));
    		pos++;
    	}
    	pos = pos + length;
    	while (pos < a.size()) {
    		result.add(a.get(pos));
    		pos++;
    	}
    	return result;
    }

    // to be overwritten by child class
    public int test(List<X> config) {return UNRESOLVED;}

    public List<X> ddmin(List<X> _circumstances) {
    	
    	List<X> circumstances = _circumstances;
    		
    	int n = 2;

    	while (circumstances.size() >= 2) {
	    
    		boolean some_complement_is_failing = false;
    		
    		for (int i = n - 1; i >= 0; i--) {
    			List<X> complement = minus(circumstances, n, i);
		
    			System.out.print("==> n: " + n + " i: " + i + " " + complement);

    			if (test(complement) == FAIL) {
    				System.out.println(" FAIL");
    				circumstances = complement;
    				n = Math.max(n - 1, 2);
    				System.out.println("Adjust number of chunks to " + n);
    				some_complement_is_failing = true;
    				break;
    			}
    			System.out.println(" PASS");
    		}
	    
    		if (!some_complement_is_failing) {
    			if (n == circumstances.size()) {
    				System.out.println("Number of chunks is maximal!");
    				break;
    			}
    			n = Math.min(n * 2, circumstances.size());
    			System.out.println("Increase number of chunks to " + n);
    		}
    	}
	
    	return circumstances;
    }

}
