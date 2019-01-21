package algorithms;

import model.Solution;
import model.TravelingThiefProblem;

import java.util.List;

public interface Algorithm {

    /**
     * This method should be overriden by your algorithm to solve the problem
     * @param problem traveling thief problem instance
     * @return A non-dominated set of solutions
     */
    List<Solution> solve(TravelingThiefProblem problem);

}
