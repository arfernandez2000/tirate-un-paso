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

        gear();
        System.out.println("out del gear");
    }
}
