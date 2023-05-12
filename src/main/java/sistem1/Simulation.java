package sistem1;

import models.Particle;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {
        int accuracy = 6;
        double dT = Math.pow(10, -accuracy);
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
            FileWriter myWriter = new FileWriter("src/main/resources/" + args[0] + "_" + accuracy +".txt");
            for (List<Double> states : finalStates) {
                myWriter.write(states.get(0) + "\t" + states.get(1) + "\t" + states.get(2) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
