import model.TravelingThiefProblem;

import java.util.*;

public class Competition {


    //! Please type your team name here. Do not use "_" in your name please.
    static final String TEAM_NAME = "MY-TEAM";

    static final List<String> INSTANCES = new ArrayList<>(Arrays.asList(
            // a280_n279_bounded-strongly-corr_01.ttp
            "a280-n279",
            // a280_n1395_uncorr-similar-weights_05.ttp
            "a280-n1395",
            // a280_n2790_uncorr_10.ttp
            "a280-n2790",
            // fnl4461_n4460_bounded-strongly-corr_01.ttp
            "fnl4461-n4460",
            // fnl4461_n22300_uncorr-similar-weights_05.ttp
            "fnl4461-n22300",
            // fnl4461_n44600_uncorr_10.ttp
            "fnl4461-n44600",
            // pla33810_n33809_bounded-strongly-corr_01.ttp
            "pla33810-n33809",
            // pla33810_n169045_uncorr-similar-weights_05.ttp
            "pla33810-n169045",
            // pla33810_n338090_uncorr_10.ttp
            "pla33810-n338090"

    ));

    /**
     * @return Number of solutions to be submitted for the corresponding problem
     */
    public static int numberOfSolutions(TravelingThiefProblem problem) {
        final String name = problem.name;
        if (name.contains("a280")) return 100;
        else if (name.contains("fnl4461")) return 50;
        else if (name.contains("pla33810")) return 20;
        else return Integer.MAX_VALUE;
    }



}
