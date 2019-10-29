package info3;

public class Term_RHS implements RHS {
	final char ch;

	public Term_RHS(char ch) {
		super();
		this.ch = ch;
	}

	@Override
	public boolean rhs_is_c(char c) {
		return c == this.ch;
	}

	@Override
	public String toString() {
		return "Term_RHS [ch=" + ch + "]";
	}
	
}
