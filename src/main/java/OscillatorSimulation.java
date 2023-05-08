import integration_schemes.*;
import models.Particle;

import java.io.FileWriter;
import java.io.PrintWriter;

public class OscillatorSimulation {
    static double acceleration = 0;
    static double dT = 0.0001;

    static double k = Math.pow(10, 4);

    public static Particle initParticle() {

        return new Particle(1, 0, 0, 0, 70);

    }

    public static void main(String[] args) {
        double time = 0;

        IntegrationScheme beeman = new Beeman((int)Math.pow(10, 4), 100);
        Particle beemanParticle = initParticle();

        IntegrationScheme gpc5 = new GPC5();
        Particle gpc5Particle = initParticle();

        IntegrationScheme verlet = new Verlet((int)Math.pow(10, 4), 100);
        Particle verletParticle = initParticle();

        IntegrationScheme analytical = new Analytical();
        Particle analyticalParticle = initParticle();

        try {
            FileWriter writerBeeman = new FileWriter("src/main/resources/output_beeman.txt");
            PrintWriter printBeeman = new PrintWriter(writerBeeman);

            FileWriter writerGPC5 = new FileWriter("src/main/resources/output_GPC5.txt");
            PrintWriter printGPC5 = new PrintWriter(writerGPC5);

            FileWriter writerVerlet = new FileWriter("src/main/resources/output_Verlet.txt");
            PrintWriter printVerlet = new PrintWriter(writerVerlet);

            FileWriter writerAnalytical = new FileWriter("src/main/resources/output_Analytical.txt");
            PrintWriter printAnalytical = new PrintWriter(writerAnalytical);

            double timeAux = System.currentTimeMillis()/10000000.0;

            while(time < 5) {
                beeman.nextStep(time, beemanParticle);
                printBeeman.write(beemanParticle.getX() + "," + beemanParticle.getSpeedX() + "," + time + "\n");

                gpc5.nextStep(time, gpc5Particle);
                printGPC5.write(gpc5Particle.getX() + "," + gpc5Particle.getSpeedX() + "," + time + "\n");

                verlet.nextStep(time, verletParticle);
                printVerlet.write(verletParticle.getX() + "," + verletParticle.getSpeedX() + "," + time + "\n");

                analytical.nextStep(time, analyticalParticle);
                printAnalytical.write(analyticalParticle.getX() + "," + analyticalParticle.getSpeedX() + "," + time + "\n");

                time += ((System.currentTimeMillis()/10000000.0 - timeAux));
            }

            writerBeeman.close();
            printBeeman.close();

            writerGPC5.close();
            printGPC5.close();

            writerVerlet.close();
            printVerlet.close();

            writerAnalytical.close();
            printAnalytical.close();


        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
