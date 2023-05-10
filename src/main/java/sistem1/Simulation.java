package sistem1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        double dT = 0.00002;
        Particle particle = new Particle(1, 0, -(100.0 / (2 * 70)), 0, 70);

        List<List<Double>> finalStates = new ArrayList<>();

        switch (args[0]) {
            case "verlet":
                finalStates = Verlet.run(particle, dT);
                break;
            case "beeman":
                finalStates = Beeman.run(particle, dT);
                break;
            case "gpc5":
                finalStates = GPC5.run(particle, dT);
                break;
            default:
                System.out.println("Invalid method");
                break;
        }

        try {
            FileWriter myWriter = new FileWriter("src/main/resources/" + args[0] + ".txt");
            for (List<Double> frame : finalStates) {
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
