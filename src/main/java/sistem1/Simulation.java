package sistem1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        double dT = 0.00002;
        Particle particle = new Particle(1, 0, -(100.0 / (2 * 70)), 0, 70);

        List<ArrayList<Double>> finalStates = new ArrayList<>();
        IntegrationSchemes integrationSchemes = new IntegrationSchemes();

        if(args[0].equals("verlet"))
            finalStates = integrationSchemes.verlet(particle, dT);
        else if(args[0].equals("beeman"))
            finalStates = integrationSchemes.beeman(particle, dT);
        else if(args[0].equals("gpc5"))
            finalStates = integrationSchemes.gear(particle, dT);
        else
            System.out.println("Invalid method");

        try {
            FileWriter myWriter = new FileWriter("src/main/resources/" + args[0] + ".txt");
            for (ArrayList<Double> frame : finalStates) {
                myWriter.write(frame.get(0) + "\t" + frame.get(1) + "\t" + frame.get(2) + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote " + args[0] + ".txt.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
