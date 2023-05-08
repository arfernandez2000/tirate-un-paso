package integration_schemes;

import models.Ball;
import models.Particle;

public class Analytical implements IntegrationScheme {

    public double amplitude = 1;
    public double gamma = 100;
    public double k = Math.pow(10, 4);
    public double t = 0;

    public Analytical() {
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
    public void nextStep(double dT, Ball ball) {
        //TODO Implement
    }
}
