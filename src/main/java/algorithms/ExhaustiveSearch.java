package algorithms;

import model.NonDominatedSet;
import model.Solution;
import model.TravelingThiefProblem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is an exhaustive search over all possible solutions. Please only use this for very small problems.
 *
 * Also, note that this implementation is not very memory-efficient, because all permutations for tours
 * are stored in memory.
 */
public class ExhaustiveSearch implements Algorithm {


    public List<Solution> solve(TravelingThiefProblem problem) {

        // start form an empty set of non-dominated solutions
        NonDominatedSet nds = new NonDominatedSet();

        // index vector to permute
        List<Integer> index = getIndex(1, problem.numOfCities);

        // iterate over all possible tours
        for (List<Integer> pi : permute(index)) {

            // make tour starting at city 0
            pi.add(0, 0);

            // for all possible item combinations
            for (int i = 0; i <= problem.numOfItems; i++) {

                // iterate over all combinations for items
                Combination combination = new Combination(problem.numOfItems, i);
                while (combination.hasNext()) {

                    // create the corresponding packing plan from the combination
                    List<Boolean> z = new ArrayList<>(problem.numOfItems);
                    for (int j = 0; j < problem.numOfItems; j++) z.add(false);
                    for (int j : combination.next()) z.set(j, true);

                    // evaluate the solution and add to non-dominated set
                    Solution s = problem.evaluate(pi,z, true);
                    nds.add(s);


                }
            }
        }


        return nds.entries;
    }



    private <T> Collection<List<T>> permute(Collection<T> input) {
        Collection<List<T>> output = new ArrayList<>();
        if (input.isEmpty()) {
            output.add(new ArrayList<>());
            return output;
        }

        List<T> list = new ArrayList<>(input);
        T head = list.get(0);
        List<T> rest = list.subList(1, list.size());
        for (List<T> permutations : permute(rest)) {
            List<List<T>> subLists = new ArrayList<>();
            for (int i = 0; i <= permutations.size(); i++) {
                List<T> subList = new ArrayList<>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }




    private class Combination {
        private int n, r;
        private int[] index;
        private boolean hasNext = true;
        protected List<Integer> l;

        public Combination(int n, int r) {
            this.n = n;
            this.r = r;
            index = new int[r];
            for (int i = 0; i < r; i++) index[i] = i;
        }

        public boolean hasNext() {
            return hasNext;
        }

        private void moveIndex() {
            int i = rightmostIndexBelowMax();
            if (i >= 0) {
                index[i] = index[i] + 1;
                for (int j = i + 1; j < r; j++)
                    index[j] = index[j - 1] + 1;
            } else
                hasNext = false;
        }

        public List<Integer> next() {
            if (!hasNext) return null;
            List<Integer> result = new ArrayList<>(r);
            for (int i = 0; i < r; i++) {
                if (l == null) result.add(index[i]);
                else result.add(l.get(index[i]));
            }
            moveIndex();
            return result;
        }

        // return int,the index which can be bumped up.
        private int rightmostIndexBelowMax() {
            for (int i = r - 1; i >= 0; i--)
                if (index[i] < n - r + i)
                    return i;
            return -1;
        }
    }

    private List<Integer> getIndex(int low, int high) {
        List<Integer> l = new ArrayList<>();
        for (int j = low; j < high; j++) {
            l.add(j);
        }
        return l;
    }





}
