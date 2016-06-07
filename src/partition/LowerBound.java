package partition;
import java.util.Arrays;

public class LowerBound {
    public static int lower_bound(int[] set) {
    	Arrays.sort(set);

    	int sum = 0;
    	int lb = 0;
    	for( int i = 0; i < set.length; ++i ) {
    		sum += set[i];
    	}

    	for( int i = 1; i <= set.length; ++i ) {
    		int s = 0;
    		for( int j = set.length - i; j < set.length - (2 * i) / 3; ++j ) {
    			s += set[j];
    		}

    		int remain = (sum - s) / 2;
    		if( 2 * (s - remain) > lb ) {
    			lb = 2 * (s - remain);
    		}
    	}

    	return lb;
    }
}
