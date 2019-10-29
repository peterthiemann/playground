package info3;

public class UpperTriangle<T> {
	private final T[] buffer;
	private final int n;
	
	/**
	 * Upper triangle n x n matrix including diagonal.
	 * @param n span of matrix
	 */
	public UpperTriangle(int n) {
		this.n = n;
		buffer = (T[])new Object[n * (n+1) / 2];
	}
	
	/**
	 * Compute index into private buffer. Precondition: 0 <= i <= j < n.
	 * @param i row index
	 * @param j column index
	 * @return index into private buffer.
	 */
	private int index(int i, int j) {
		int b = n * i - i * (i + 1) / 2;
		return b + j;
	}
	
	/**
	 * Get entry at (i, j). Precondition: 0 <= i <= j < n.
	 * @param i row index
	 * @param j column index
	 * @return data at (i, j).
	 */
	public T get(int i, int j) {
		return buffer[index(i, j)];
	}
	
	/**
	 * Set entry at (i, j). Precondition: 0 <= i <= j < n.
	 * @param i row index
	 * @param j column index
	 * @param val to be stored at (i, j)
	 */
	public void set(int i, int j, T val) {
		buffer[index(i, j)] = val;
	}
}
