package refl;

public class Experiment {
	public static void main(String[] args) {
		A a = new A(false, 0, "NOPD", (short) 0, 3.14, 0L);
		Display.display(a);
		A a2 = new A(true, 711, null, (short) 4711, 2.7141, 1237891239789218731L);
		Display.display(a2);
		Display.display(null);
		B b = new B(101, new byte[] {48, 45, 52, 52, 55}, a2);
		Display.display(b);
		b.someb = b;
		Display.display(b);
	}
}
