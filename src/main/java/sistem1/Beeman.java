package sistem1;

import java.util.ArrayList;
import java.util.List;

public class Beeman {

    public static double k = 10000;
    public static double gamma = 100;

    public static List<List<Double>> run(Particle particle, double dT){

        double force = -k * particle.getX() - gamma * particle.getSpeedX();
        double PosPrev = eulerPosition(
                particle.getX(),
                particle.getSpeedX(),
                -dT,
                force,
                particle.getMass()
        );
        double speedPrev = eulerSpeed(
                particle.getSpeedX(),
                force,
                particle.getMass(),
                -dT
        );
        double accBefore = (-k * PosPrev - gamma * speedPrev)/particle.getMass();

        double pos;
        double speed;
        double newV;
        double time = 0;
        double accAfter;

        List<List<Double>> states = new ArrayList<>();

        while(time <= 5){

            // Saving current state
            List<Double> state = new ArrayList<>();
            state.add(time);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            // Calculate position
            force = -k * particle.getX() - gamma * particle.getSpeedX();
            pos = beemanPosition(
                    particle.getX(),
                    particle.getSpeedX(),
                    force/particle.getMass(),
                    accBefore,
                    dT
            );

            // Speed Prediction
            force = -k * pos - gamma * particle.getSpeedX();
            speed = beemanSpeedPredicted(
                    particle.getSpeedX(),
                    force/particle.getMass(),
                    accBefore,
                    dT
            );
            accAfter = (-k * pos - gamma * speed)/particle.getMass();

            // Speed Correction
            force = -k * particle.getX() - gamma * particle.getSpeedX();
            newV = beemanSpeedCorrected(
                    particle.getSpeedX(),
                    accBefore,
                    force/particle.getMass(),
                    accAfter,
                    dT
            );

            accBefore = (-k * particle.getX() - gamma * particle.getSpeedX())/particle.getMass();

            //Update Position and Speed
            particle.setSpeedX(newV);
            particle.setX(pos);

            //Next time
            time += dT;

        }
        return states;

    }

    private static double eulerPosition(double pos, double speed, double dT, double force, double mass){
        return pos
                + speed * dT
                + dT*dT * (force/(2 * mass));
    }

    private static double eulerSpeed(double speed, double force, double mass, double dT){
        return speed
                + dT * force / mass;
    }

    private static double beemanPosition(double pos, double speed, double acc, double accBefore, double dT) {
        return pos + speed * dT
                + (2f/3) * acc * dT * dT
                - (1f/6) * accBefore * dT * dT;
    }

    private static double beemanSpeedCorrected(double speed, double accBefore, double acc, double accAfter, double dT ) {
        return speed
                + (1f/3) * accAfter * dT
                + (5f/6) * acc * dT
                - (1f/6) * accBefore * dT;
    }

    private static double beemanSpeedPredicted(double speed, double acc, double accBefore, double dT) {
        return speed
                + (3f/2) * acc * dT
                - (1f/2) * accBefore * dT;
    }

}
