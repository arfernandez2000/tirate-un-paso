package integration_schemes;

import models.Ball;
import models.Particle;

public class Analytical implements IntegrationScheme {

    public double amplitude;
    public double gamma;
    public double k;
    public double t;

    public Analytical(double amplitude, double gamma, double k) {
        this.amplitude = amplitude;
        this.gamma = gamma;
        this.k = k;
        this.t = 0;
    }

    @Override
    public void nextStep(double dT, Particle particle) {
        this.t += dT;
        particle.setX(
                amplitude
                        * Math.exp(-(this.gamma / (2 * particle.getMass())) * this.t)
                        * Math.cos(
                            Math.pow((this.k / particle.getMass())
                                    - ((Math.pow(this.gamma, 2) / (Math.pow(2 * particle.getMass(), 2)))), 0.5)
                                    * this.t
                        )
        );
    }

    @Override
    public void netStep(double dT, Ball ball) {
        //TODO Implement
    }
}
