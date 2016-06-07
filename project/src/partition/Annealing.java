package partition;

import java.util.Random;
import java.util.ArrayList;

public class Annealing {
	Random r;

	Annealing() {
		r = new Random();
	}

    public int solve( int[] set ) {
        return solve(set, 0.99);
    }

	public int solve( int[] set, double alpha ) {
		double T = 1.0;
		double T_min = 0.000001;
		State s = new State(set);
        State best = new State(set);
        int steps = 0;

		while( T > T_min ) {
			State neighbour = s.get_random_neighbour();

			if( neighbour.cost() < s.cost() ) {
				s = neighbour;
			} else {
				double prob = Math.exp((s.cost() - neighbour.cost()) / T);
				if( prob > r.nextDouble() ) {
					s = neighbour;
				}
			}

            if( s.cost() < best.cost() ) {
                best = s;
            }

            T = T * alpha;
            steps += 1;
		}
        System.out.println(steps);
		return best.cost();
	}
    
    private class State {
    	public ArrayList<Integer> s1, s2, s3;

    	State(int[] set) {
    		s1 = new ArrayList<Integer>();
            for( int elem : set ) {
                s1.add(elem);
            }
    		s2 = new ArrayList<Integer>();
    		s3 = new ArrayList<Integer>();
    	}

    	State(ArrayList<Integer> r1, ArrayList<Integer> r2, ArrayList<Integer> r3) {
            s1 = new ArrayList<Integer>();
            for( int elem : r1 ) {
                s1.add(elem);
            }
            s2 = new ArrayList<Integer>();
            for( int elem : r2 ) {
                s2.add(elem);
            }
            s3 = new ArrayList<Integer>();
            for( int elem : r3 ) {
                s3.add(elem);
            }
    	}

    	State get_random_neighbour() {
    		int n1 = s1.size();
    		int n2 = s2.size();
    		int n3 = s3.size();

    		int possibilities = 2 * n1 + 2 * n2 + 2 * n3 + n1 * n2 + n1 * n3 + n2 * n3 + 2 * n1 * n2 * n3;

    		int choice = r.nextInt(possibilities);

    		State n = new State(s1, s2, s3);

    		if( choice >= 2 * n1 ) {
    			choice -= 2 * n1;
    		} else if( choice >= n1 ) {
    			choice -= n1;
    			int elem = n.s1.remove(choice);
    			n.s2.add(elem);
    			return n;
    		} else {
    			int elem = n.s1.remove(choice);
    			n.s3.add(elem);
    			return n;  			
    		}

    		if( choice >= 2 * n2 ) {
    			choice -= 2 * n2;
    		} else if( choice >= n2 ) {
    			choice -= n2;
    			int elem = n.s2.remove(choice);
    			n.s1.add(elem);
    			return n;
    		} else {
    			int elem = n.s2.remove(choice);
    			n.s3.add(elem);
    			return n;  			
    		}

    		if( choice >= 2 * n3 ) {
    			choice -= 2 * n3;
    		} else if( choice >= n3 ) {
    			choice -= n3;
    			int elem = n.s3.remove(choice);
    			n.s1.add(elem);
    			return n;
    		} else {
    			int elem = n.s3.remove(choice);
    			n.s2.add(elem);
    			return n;  			
    		}

    		if( choice >= n1 * n2 ) {
    			choice -= n1 * n2;
    		} else {
    			int e1 = n.s1.remove(choice % n1);
    			int e2 = n.s2.remove(choice / n1);
    			n.s1.add(e2);
    			n.s2.add(e1);
    			return n;
    		}

    		if( choice >= n1 * n3 ) {
    			choice -= n1 * n3;
    		} else {
    			int e1 = n.s1.remove(choice % n1);
    			int e2 = n.s3.remove(choice / n1);
    			n.s1.add(e2);
    			n.s3.add(e1);
    			return n;
    		}

    		if( choice >= n3 * n2 ) {
    			choice -= n3 * n2;
    		} else {
    			int e1 = n.s3.remove(choice % n3);
    			int e2 = n.s2.remove(choice / n3);
    			n.s3.add(e2);
    			n.s2.add(e1);
    			return n;
    		}

    		if( choice >= n1 * n2 * n3) {
                choice -= n1 * n2 * n3;
    			int e1 = n.s1.remove(choice % n1);
    			int e2 = n.s2.remove((choice / n1) % n2);
    			int e3 = n.s3.remove((choice / n1) / n2);
    			n.s2.add(e1);
    			n.s3.add(e2);
    			n.s1.add(e3);
    			return n;
    		} else {
                int e1 = n.s1.remove(choice % n1);
                int e2 = n.s2.remove((choice / n1) % n2);
                int e3 = n.s3.remove((choice / n1) / n2);
    			n.s3.add(e1);
    			n.s1.add(e2);
    			n.s2.add(e3);
    			return n;
    		}
    	}

    	int cost() {
    		int sum1 = 0;
    		for( int i = 0; i < s1.size(); ++i ) {
    			sum1 += s1.get(i);
    		}

    		int sum2 = 0;
    		for( int i = 0; i < s2.size(); ++i ) {
    			sum2 += s2.get(i);
    		}

    		int sum3 = 0;
    		for( int i = 0; i < s3.size(); ++i ) {
    			sum3 += s3.get(i);
    		}

    		return Math.abs(sum1 - sum2) + Math.abs(sum1 - sum3) + Math.abs(sum2 - sum3);
    	}

    }
}
