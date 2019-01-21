import model.Solution;
import model.TravelingThiefProblem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class reads a problem into the object class.
 */
public abstract class Util {


    public static void writeSolutions(String outputFolder, String teamName, TravelingThiefProblem problem, List<Solution> solutions) throws IOException {

        int numberOfSolutions = Competition.numberOfSolutions(problem);
        if (solutions.size() > numberOfSolutions) {
            System.out.println(String.format("WARNING: Finally the competition allows only %s solutions to be submitted. " +
                    "Your algorithm found %s solutions.", numberOfSolutions, solutions.size()));
        }

        BufferedWriter varBw = Files.newBufferedWriter(Paths.get(outputFolder,
                String.format("%s_%s.x", teamName, problem.name)));

        BufferedWriter objBw = Files.newBufferedWriter(Paths.get(outputFolder,
                String.format("%s_%s.f", teamName, problem.name)));


        for (Solution solution : solutions) {

            // add one to the index of each city to match the index of the input format
            List<Integer> modTour = new ArrayList<>(solution.pi);
            for (int i = 0; i < modTour.size(); i++) {
                modTour.set(i, modTour.get(i) + 1);
            }

            // write the variables
            varBw.write(String.join(" ",
                    modTour.stream().map(Object::toString).collect(Collectors.toList())) + "\n");
            varBw.write(String.join(" ",
                    solution.z.stream().map(b -> b ? "1" : "0").collect(Collectors.toList())) + "\n");
            varBw.write("\n");

            // write into the objective file
            objBw.write(String.format("%.16f %.16f", solution.time, solution.profit) + "\n");

        }

        varBw.close();
        objBw.close();



    }



    public static void printSolutions(List<Solution> solutions, boolean printVariable) {

        System.out.println(String.format("Number of non-dominated solutions: %s", solutions.size()));

        for (Solution solution : solutions) {

            if(printVariable) {
                System.out.print(String.join(" ",
                        solution.pi.stream().map(Object::toString).collect(Collectors.toList())));

                System.out.print(" , ");
                System.out.print(String.join(" ",
                        solution.z.stream().map(b -> b ? "1" : "0").collect(Collectors.toList())));
                System.out.print(" ");

            }
            System.out.println(String.format("%.2f %.2f", solution.time, solution.profit));
        }
    }


	public static TravelingThiefProblem readProblem(InputStream is) throws IOException {

        TravelingThiefProblem problem = new TravelingThiefProblem();

        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        String line = br.readLine();
        while(line != null){

            if (line.contains("PROBLEM NAME")) {
            } else if (line.contains("KNAPSACK DATA TYPE")) {
            } else if (line.contains("DIMENSION")) {
                problem.numOfCities = Integer.valueOf(line.split(":")[1].trim());
                problem.coordinates = new double[problem.numOfCities][2];
            } else if (line.contains("NUMBER OF ITEMS")) {
                problem.numOfItems = Integer.valueOf(line.split(":")[1].trim());
                problem.cityOfItem = new int[problem.numOfItems];
                problem.weight = new double[problem.numOfItems];
                problem.profit = new double[problem.numOfItems];
            } else if (line.contains("RENTING RATIO")) {
                problem.R = Double.valueOf(line.split(":")[1].trim());
            } else if (line.contains("CAPACITY OF KNAPSACK")) {
                problem.maxWeight = Integer.valueOf(line.split(":")[1].trim());
            } else if (line.contains("MIN SPEED")) {
                problem.minSpeed = Double.valueOf(line.split(":")[1].trim());
            } else if (line.contains("MAX SPEED")) {
                problem.maxSpeed = Double.valueOf(line.split(":")[1].trim());
            } else if (line.contains("EDGE_WEIGHT_TYPE")) {
                String edgeWeightType = line.split(":")[1].trim();
                if (!edgeWeightType.equals("CEIL_2D")) {
                    throw new RuntimeException("Only edge weight type of CEIL_2D supported.");
                }
            } else if (line.contains("NODE_COORD_SECTION")) {
                for (int i = 0; i < problem.numOfCities; i++) {
                    line = br.readLine();
                    String[] a = line.split("\\s+");
                    problem.coordinates[i][0] = Double.valueOf(a[1].trim());
                    problem.coordinates[i][1] = Double.valueOf(a[2].trim());
                }

            } else if (line.contains("ITEMS SECTION")) {

                for (int i = 0; i < problem.numOfItems; i++) {
                    line = br.readLine();
                    String[] a = line.split("\\s+");
                    problem.profit[i] = Double.valueOf(a[1].trim());
                    problem.weight[i] = Double.valueOf(a[2].trim());
                    problem.cityOfItem[i] = Integer.valueOf(a[3].trim()) - 1;
                }

            }
            line = br.readLine();
        }

        br.close();

        problem.initialize();
        return problem;
	}





}
