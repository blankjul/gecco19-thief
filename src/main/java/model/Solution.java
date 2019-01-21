package model;

import java.util.List;

/**
 * This is a solution objective which stores the tour, packing plan and the objective values.
 */
public class Solution {

    //! the tour of the thief
    public List<Integer> pi;

    //! the packing plan
    public List<Boolean> z;

    //! the time the thief needed for traveling
    public double time = -1.0;

    //! the profit the thief made on that tour
    public double profit = -1.0;

    //! objective value if you want to solve the single-objective problem using R
    public double singleObjective = -1.0;

    //! the objective values of the function
    public List<Double> objectives;


    /**
     * This is used for non-dominated sorting and returns the dominance relation
     * @param other solution to compare with
     * @return returns 1 if dominates, -1 if dominated and 0 if indifferent
     */
    public int getRelation(Solution other) {
        int val = 0;
        for (int i = 0; i < objectives.size(); i++) {

            if (objectives.get(i) < other.objectives.get(i)) {
                if (val == -1) return 0;
                val = 1;
            } else if (objectives.get(i) > other.objectives.get(i)) {
                if (val == 1) return 0;
                val = -1;
            }

        }

        return val;

    }

    /**
     * @param other solution to compare with
     * @return True if tour and packing plan is equal
     */
    public boolean equalsInDesignSpace(Solution other) {
        return pi.equals(other.pi) && z.equals(other.z);
    }


}
