package info3.regexp;

import java.util.Set;

public interface Regexp {

	boolean nullable();

	Set<Regexp> step(char c);
	
	Set<String> generate(int nr, int size);

}
