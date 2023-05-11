package sistem1;

import models.Particle;

import java.util.ArrayList;
import java.util.List;

public class Verlet {

    public static double k = 10000;
    public static double gamma = 100;

    public static List<List<Double>> run(Particle particle, double dT){

        double force = -k * particle.getX() - gamma * particle.getSpeedX();


        double posBefore = eulerPosition(
                particle.getX(),
                particle.getSpeedX(),
                -dT,
                force,
                particle.getMass()
        );

        double pos;
        double speed;
        double time = 0;
        List<List<Double>> states = new ArrayList<>();

        while(time <= 5){

            // Saving current state
            ArrayList<Double> state = new ArrayList<>();
            state.add(time);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            // Calculate position
            force = -k * particle.getX() - gamma * particle.getSpeedX();
            pos = verletPosition(
                    particle.getX(),
                    posBefore,
                    force,
                    particle.getMass(),
                    dT
            );

            // Calculate speed
            if(time != 0) {
                speed = verletSpeed(
                        pos,
                        posBefore,
                        dT
                );
                particle.setSpeedX(speed);
            }

            //update
            posBefore = particle.getX();
            particle.setX(pos);
            time += dT;
        }
        return states;
    }

    private static double eulerPosition(double pos, double speed, double dT, double force, double mass){
        return pos
                + speed * dT
                + dT * dT * (force/(2 * mass));
    }

    private static double verletPosition(double pos, double posBefore, double force, double mass, double dT){
        return (2 * pos)
                - posBefore
                + dT * dT * (force/mass);
    }

    private static double verletSpeed(double posAfter, double posBefore, double dT){
        return (posAfter - posBefore) / (2 * dT);
    }

}
