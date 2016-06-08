package partition;

import java.util.Random;

/**
 * Created by Krzysztof Krol on 6/8/2016.
 */
public class Main {

    public static void main(String[] args) {
        KarmakarKarp karmakarKarp = new KarmakarKarp();
        Annealing annealing = new Annealing();

        int karmakarKarpSum = 0;
        int annealingSum = 0;
        int lowerBoundSum = 0;
        int upperBoundSum = 0;

        int tests = 1;
        int n = 10;


        for( int i = 0; i < tests; ++i ) {
            System.out.println(i);
            //int[] set = generateSet(100, 10000, n);
            int[] set = generateExpSet(n);

            int karmakarKarpResult = solveKarmakar(karmakarKarp, set);
            int annealingResult = annealing.solve(set);
            int upperBoundResult = UpperBound.solve(set);
            int lowerBoundResult = LowerBound.lower_bound(set);

            //System.out.println("Set: " + Arrays.toString(set));
            /*
            System.out.println("KarmakarKarp result: " + karmakarKarpResult);
            System.out.println("Annealing result: " + annealingResult);
            System.out.println("Lower bound result: " + lowerBoundResult);
            System.out.println("Upper bound result: " + upperBoundResult);
            */

            karmakarKarpSum += karmakarKarpResult;
            annealingSum += annealingResult;
            lowerBoundSum += lowerBoundResult;
            upperBoundSum += upperBoundResult;
        }
        System.out.println("KarmakarKarp average: " + (karmakarKarpSum / tests));
        System.out.println("Annealing average: " + (annealingSum / tests));
        System.out.println("Lower bound average: " + (lowerBoundSum / tests));
        System.out.println("Upper bound average: " + (upperBoundSum / tests));
        System.exit(0);
    }

    private static int solveKarmakar(KarmakarKarp karmakarKarp, int[] set) {
       // for (int timeout = 10; timeout <= 100000; timeout += 2000) {
       //     System.out.println(String.format("Karmakar timeout: %d result: %d ", timeout, karmakarKarp.solve(set, timeout)));
       // }
        return karmakarKarp.solve(set, 1000);
    }

    public static int[] generateSet(int lowerBound, int upperBound, int count) {
        Random random = new Random();
        int[] set = new int[count];
        for (int i = 0; i < count; i++) {
            set[i] = (random.nextInt(upperBound - lowerBound) + lowerBound);
        }
        return set;
    }

    public static int[] generateExpSet(int count) {
        Random random = new Random();
        int[] set = new int[count];
        for( int i = 0; i < count; ++i ) {
            set[i] = (int) Math.exp(0.7 * Math.exp(Math.exp(random.nextDouble())));
        }
        return set;
    }
}
