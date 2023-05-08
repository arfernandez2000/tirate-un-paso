package integration_schemes;

import models.Ball;
import models.Particle;

public class Beeman implements IntegrationScheme {

    Integer k;
    Integer gamma;

    int simulation;

    public Beeman(int k, int gamma) {
        this.k = k;
        this.gamma = gamma;
        this.simulation = 0;
    }

    public Beeman() {
        this.simulation = 1;
    }

    private double force(double x, double speed) {
        return -k * x - gamma * speed;
    }

    @Override
    public void nextStep(double dT, Particle particle) {
        double f = force(particle.getX(), particle.getSpeedX());
        double a = f/particle.getMass();
        particle.setX(
                particle.getX()
                        + particle.getSpeedX() * dT
                        + (2.0/3) * (a * dT * dT)
                        - particle.getAcceleration() * dT * dT/6
        );

        double speedP = particle.getSpeedX() + (3.0/2) * (a * dT) - particle.getAcceleration() * dT/2;
        double nextF = force(particle.getX(), speedP);
        double nextA = nextF/particle.getMass();
        particle.setSpeedX(
                particle.getSpeedX()
                        + nextA * dT/3
                        + (5.0/6.0) * (a * dT)
                        - particle.getAcceleration() * dT/6
        );

        particle.setAcceleration(a);
    }

    @Override
    public void nextStep(double dT, Ball ball) {
        //TODO Implement
    }


}
