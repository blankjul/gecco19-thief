import model.Solution;
import model.TravelingThiefProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Verify {
    /**
     * To verify your solution, you have to do the following:
     *
     * 1) Create a subfolder in "results" which is equal to your team name
     * 2) Modify the string TEAM accordingly
     * 3) Run the script. It will check if your submission is correct.
     *
     */

    static final ClassLoader LOADER = Runner.class.getClassLoader();

    public static void main(String[] args)  throws IOException {

        final String TEAM = "jomar";

        List<String> instances = Arrays.asList("a280_n279", "a280_n1395", "a280_n2790",
        "fnl4461_n4460", "fnl4461_n22300", "fnl4461_n44600", "pla33810_n33809", "pla33810_n169045", "pla33810_n338090");

        for (String instance : instances) {

            //instance = instance.replace("_", "-");

            // readProblem the problem from the file
            String fname = String.format("resources/%s.txt", instance.replace("_", "-"));
            InputStream is = LOADER.getResourceAsStream(fname);

            TravelingThiefProblem problem = Util.readProblem(is);
            problem.name = instance;

            Path pathToX = Paths.get("results", TEAM, String.format("%s_%s.x", TEAM, instance));
            Path pathToF = Paths.get("results", TEAM, String.format("%s_%s.f", TEAM, instance));

            String[] objectives = new String(Files.readAllBytes(pathToF)).split("\n");
            int counter = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(pathToX.toFile()))) {

                while (true) {

                    String line = br.readLine();

                    if (line == null) break;

                    if (line.equals("")) continue;


                    List<Integer> pi = Arrays.asList(line.split("\\s+|,"))
                            .stream()
                            .map(String::trim)
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .collect(Collectors.toList());

                    if (pi.get(0) == 1) {
                               pi = pi.stream()
                                       .map(x -> x - 1)
                                       .collect(Collectors.toList());
                    }

                    if (pi.size() != problem.numOfCities) {
                        System.out.println("ERROR");
                        System.out.println(String.format("Solution %s", counter));
                        System.out.println(String.format("Wrong tour length %s != %s", pi.size(), problem.numOfCities));
                        System.out.println("Submission can not be accepted.");
                        System.exit(1);
                    }

                    ArrayList<Boolean> list = new ArrayList<>(problem.numOfCities);
                    list.addAll(Collections.nCopies(problem.numOfCities, Boolean.FALSE));
                    for (int c : pi) {
                        list.set(c, true);
                    }

                    if (list.contains(false)) {
                        System.out.println("ERROR");
                        System.out.println(String.format("Solution %s", counter));
                        System.out.println("Not all cities are visited.");
                        System.out.println("Submission can not be accepted.");
                        System.exit(1);
                    }

                    line = br.readLine();

                    List<Boolean> z = Arrays.asList(line.split("\\s+|,"))
                            .stream()
                            .map(String::trim)
                            .map(b -> b.equals("1") ? true : false)
                            .collect(Collectors.toList());

                    Solution solution = problem.evaluate(pi, z);

                    String[] vals = objectives[counter].split("\\s+");
                    double time = Double.valueOf(vals[0]);
                    double profit = Double.valueOf(vals[1]);


                    double precision = 1e-6;
                    if (Math.abs(time - solution.time) > precision || Math.abs(profit - solution.profit) > precision) {
                        System.out.println("ERROR");
                        System.out.println(String.format("Solution %s", counter));
                        System.out.println(String.format("Reported time is %s. Evaluated %s.", time, solution.time));
                        System.out.println(String.format("Reported profit is %s. Evaluated %s.", profit, solution.profit));
                        System.out.println("Submissions file do not match!");
                        System.exit(1);
                    }

                    br.readLine();
                    counter++;

                }

            }


            int numberOfSolutions = Competition.numberOfSolutions(problem);
            if (counter > numberOfSolutions) {
                System.out.println(String.format("WARNING: Finally the competition allows only %s solutions to be submitted. " +
                        "Your algorithm found %s solutions.", numberOfSolutions, counter));
            }

            System.out.println(String.format("%s: Submission is correct (%s / %s).", instance, counter, numberOfSolutions));
            //System.out.println();

        }


    }
}
