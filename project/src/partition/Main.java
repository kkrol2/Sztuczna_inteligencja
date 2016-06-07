package partition;

import java.util.Random;

/**
 * Created by Krzysztof Krol on 6/8/2016.
 */
public class Main {

    public static void main(String[] args) {
        KarmakarKarp karmakarKarp = new KarmakarKarp();
        Annealing annealing = new Annealing();
        int[] set = generateSet(100, 300, 150);
        int karmakarKarpResult = solveKarmakar(karmakarKarp, set);
        int annealingResult = annealing.solve(set);
        //System.out.println("Set: " + Arrays.toString(set));
        System.out.println("KarmakarKarp result: " + karmakarKarpResult);
        System.out.println("Annealing result: " + annealingResult);
        System.out.println("Lower bound result: " + LowerBound.lower_bound(set));
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
            set[i] = random.nextInt(upperBound - lowerBound) + lowerBound;
        }
        return set;
    }
}
