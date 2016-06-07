package partition;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Krzysztof Krol on 6/7/2016.
 */
public class KarmakarKarp {

    public int solve(int[] set, long timeout) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        KarmakarKarpTask task = new KarmakarKarpTask(set);
        try {
            return executor.submit(task).get(timeout, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            //e.printStackTrace();
            return task.getBestAvailableResult();
        } finally {
            executor.shutdownNow();
        }
    }

    private class KarmakarKarpTask implements Callable<Integer> {

        private final List<Tuple> root;
        private int bestAvailableResult = Integer.MAX_VALUE;

        public KarmakarKarpTask(int[] set) {
            root = new ArrayList<>();
            for (int member : set) {
                root.add(new Tuple(member, 0, 0));
            }
            root.sort(new TupleComparator());
        }

        @Override
        public Integer call() throws Exception {
            return solve(root);
        }

        private int solve(List<Tuple> node) {
            int best = Integer.MAX_VALUE;
            if (node.size() == 1) {
                int result = 2 * node.get(0).get(0);
                bestAvailableResult = Math.min(bestAvailableResult, result);
                return result;
            }
            for (int i = 1; i < node.size(); i++) {
                ArrayList<Tuple>newNode = new ArrayList<>(node);
                newNode.remove(i);
                newNode.remove(0);
                newNode.add(node.get(0).combine(node.get(i)));
                newNode.sort(new TupleComparator());
                best = Math.min(best, solve(newNode));
            }
            return best;
        }


        public int getBestAvailableResult() {
            return bestAvailableResult;
        }
    }

    private class Tuple {
        private List<Integer> content;

        public Tuple(int first, int second, int third) {
            this.content = new ArrayList<>();
            content.add(first);
            content.add(second);
            content.add(third);
            content.sort(Collections.reverseOrder());
            normalize();
        }

        public Tuple combine(Tuple tuple) {
            Tuple copy = new Tuple(content.get(0), content.get(1), content.get(2));
            copy.set(0, copy.get(0) + tuple.get(2));
            copy.set(1, copy.get(1) + tuple.get(1));
            copy.set(2, copy.get(2) + tuple.get(0));
            copy.getContent().sort(Collections.reverseOrder());
            copy.normalize();
            return copy;
        }

        private Integer get(int index) {
            return content.get(index);
        }

        private Integer set(int index, int value) {
            return content.set(index, value);
        }

        private void normalize() {
            content.set(0, content.get(0) - content.get(2));
            content.set(1, content.get(1) - content.get(2));
            content.set(2, 0);
        }

        public List<Integer> getContent() {
            return content;
        }
    }

    private class TupleComparator implements Comparator<Tuple> {

        @Override
        public int compare(Tuple t1, Tuple t2) {
            return -Integer.compare(t1.get(0), t2.get(0));
        }
    }
}
