package partition;

import java.util.Arrays;
import java.util.ArrayList;

public class UpperBound {
    public static int solve( int[] set ) {
    	Arrays.sort(set);

    	ArrayList<Integer> s1 = new ArrayList<>();
    	ArrayList<Integer> s2 = new ArrayList<>();
    	ArrayList<Integer> s3 = new ArrayList<>();

    	int sum1 = 0;
    	int sum2 = 0;
    	int sum3 = 0;

    	for( int i = set.length - 1; i >= 0; --i ) {

    		if( sum1 <= sum2 && sum1 <= sum3 ) {
    			s1.add(set[i]);
    			sum1 += set[i];
    		} else if( sum2 <= sum3 ) {
    			s2.add(set[i]);
    			sum2 += set[i];
    		} else {
    			s3.add(set[i]);
    			sum3 += set[i];
    		}
    	}

    	return Math.abs(sum1 - sum2) + Math.abs(sum1 - sum3) + Math.abs(sum2 - sum3);
	}
}
