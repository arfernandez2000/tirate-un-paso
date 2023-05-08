package integration_schemes;

import models.Ball;
import models.Particle;

public interface IntegrationScheme {

    void nextStep(double dT, Particle ball);

    void nextStep(double dT, Ball ball);

    public interface Integrator {
    }

}
