package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * This is an example implementation of a non-dominated set. It updates the set whenever new
 * solutions are added.
 *
 */
public class NonDominatedSet {

    //! entries of the non-dominated set
    public List<Solution> entries = new LinkedList<>();

    /**
     * Add a solution to the non-dominated set
     * @param s The solution to be added.
     * @return true if the solution was indeed added. Otherwise false.
     */
    public boolean add(Solution s) {

        boolean isAdded = true;

        for (Iterator<Solution> it = entries.iterator(); it.hasNext();) {
            Solution other = it.next();

            int rel = s.getRelation(other);

            // if dominated by or equal in design space
            if (rel == -1 || (rel == 0 && s.equalsInDesignSpace(other))) {
                isAdded = false;
                break;
            } else if (rel == 1) it.remove();

        }

        if (isAdded) entries.add(s);

        return isAdded;

    }


}
