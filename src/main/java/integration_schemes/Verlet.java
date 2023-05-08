package integration_schemes;

import models.Ball;
import models.Particle;

public class Verlet implements IntegrationScheme{

    Integer k;
    Integer gamma;

    int simulation;

    public Verlet(int k, int gamma) {
        this.k = k;
        this.gamma = gamma;
        this.simulation = 0;
    }

    public Verlet() {
        this.simulation = 1;
    }

    private double force(double x, double speed) {
        return -k * x - gamma * speed;
    }

    public void setNewPosition(double delta, Particle particle, double force) {
        particle.setX(
                particle.getX()
                        + delta * particle.getSpeedX()
                        + (Math.pow(delta, 2) / particle.getMass()) * force
        );
    }

    public void setNewSpeed(double delta, Particle particle, double prevF, double newF) {
        particle.setSpeedX(
                particle.getSpeedX()
                        + (delta / (2 * particle.getMass())) * (prevF + newF)
        );
    }

    private double getMiddleSpeed(double dT, Particle particle, double force) {
        double delta = dT / 2;
        double middlePosition = particle.getX()
                + delta * particle.getSpeedX()
                + (Math.pow(delta, 2) / particle.getMass()) * force;
        double newForce = force(middlePosition, particle.getSpeedX());
        return particle.getSpeedX() + (delta / (2 * particle.getMass())) * (force + newForce);
    }

    @Override
    public void nextStep(double dT, Particle particle) {
        double force = force(particle.getX(), particle.getSpeedX());
        double middleVel = getMiddleSpeed(dT, particle, force);
        setNewPosition(dT, particle, force);

        double newForce = force(particle.getX(), middleVel);
        setNewSpeed(dT, particle, force, newForce);
    }

    @Override
    public void nextStep(double dT, Ball ball) {

    }

}
