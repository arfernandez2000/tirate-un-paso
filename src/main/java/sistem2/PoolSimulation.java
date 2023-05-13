package sistem2;

import models.Ball;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static sistem2.FileGenerator.setupBalls;
import static sistem2.GPC5.gear;
import static sistem2.GPC5.initialRs;

public class PoolSimulation {
    public static void main(String[] args) {

        initialRs(setupBalls());

        List<List<List<Double>>> states = gear();

        generateOutput(states);
    }

    private static void generateOutput(List<List<Double>> states) {
        try {
            FileWriter myWriter = new FileWriter("src/resources/states1_1.txt");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
