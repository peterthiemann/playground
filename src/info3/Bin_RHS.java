package info3;

public class Bin_RHS implements RHS {
	final int left, right;

	public Bin_RHS(int left, int right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean rhs_is_c(char c) {
		return false;
	}

	@Override
	public String toString() {
		return "Bin_RHS [left=" + left + ", right=" + right + "]";
	}
	
}
