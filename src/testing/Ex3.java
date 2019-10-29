package testing;

public class Ex3 {
	  public static int[] merge(int[] x, int[] y)
	  {
	    int[] z = new int[x.length + y.length];
	    int i, j;    
	    for (i = 0, j = 0; i < x.length && j < y.length;) {
	      if (x[i] < y[j]) {
	        z[i + j] = x[i]; i++;
	      } else {
	        z[i + j] = y[j]; j++;
	      }
	    }
	    for (; i < x.length; i++) {
	      z[i + j] = x[i];
	    }
	    for (; j < x.length; j++) {
	      z[i + j] = y[j];
	    }
	    return z;
	  }
}
