package sistem1;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        double dT = 0.00002;
        Particle particle = new Particle(1, 0, -(100.0 / (2 * 70)), 0, 70);

        List<ArrayList<Double>> finalStates = new ArrayList<>();
        IntegrationSchemes integrationSchemes = new IntegrationSchemes();
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/output.txt");
            PrintWriter printWriter = new PrintWriter(myWriter);
            printWriter.println("Position\tVelocity\tTime");
//        finalStates = verlet(particle,dT);
            finalStates = integrationSchemes.beeman(particle, dT, printWriter);

//        finalStates = gear(particle, dT);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

    }

}
