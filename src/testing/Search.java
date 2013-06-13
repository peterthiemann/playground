package testing;

public class Search {
	
	public static int search( int [] array, int target ) {
		int low = 0;
		int high = array . length ;
		int mid ;
		while ( low <= high ) {
			mid = ( low + high ) /2;
			if ( target < array [ mid ] ) {
				high = mid - 1;
			} else if ( target > array [ mid ] ) {
				low = mid + 1;
			} else {
				return mid ;
			}
		}
		return -1;
	}

}
