package info3;

public class Triangle<T> {
	private final T[] buffer;
	private final int n;
	public Triangle(int n) {
		this.n = n;
		buffer = (T[])new Object[n * (n+1) / 2];
	}
	private int index(int i, int j) {
		int b = n * i - i * (i + 1) / 2;
		return b + j;
	}
	public T get(int i, int j) {
		return buffer[index(i, j)];
	}
	public void set(int i, int j, T val) {
		buffer[index(i, j)] = val;
	}
}
