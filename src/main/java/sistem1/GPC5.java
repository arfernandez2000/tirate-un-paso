package sistem1;

import models.Particle;

import java.util.ArrayList;
import java.util.List;

public class GPC5 {

    public static double k = 10000;
    public static double gamma = 100;

    public static List<List<Double>> run(Particle particle, double dT){

        List<List<Double>> states = new ArrayList<>();

        double time = 0;
        double[] alpha = {3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};
        double dA;
        double dR2;
        double force = -k * particle.getX() - gamma * particle.getSpeedX();

        //First state
        List<Double> derivatives = new ArrayList<>();
        derivatives.add(particle.getX());
        derivatives.add(particle.getSpeedX());
        derivatives.add(force/particle.getMass());
        derivatives.add((-k * derivatives.get(1) - gamma * derivatives.get(2)) / particle.getMass());
        derivatives.add((-k * derivatives.get(2) - gamma * derivatives.get(3)) / particle.getMass());
        derivatives.add((-k * derivatives.get(3) - gamma * derivatives.get(4)) / particle.getMass());

        while(time <= 5) {

            // Saving current state
            ArrayList<Double> state = new ArrayList<>();
            state.add(time);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            //Predictions
            List<Double> newDerivatives = predictor(derivatives, dT);

            //Evaluate
            dA = (-k * newDerivatives.get(0) - gamma * newDerivatives.get(1)) / particle.getMass()
                    - newDerivatives.get(2);
            dR2 = dA * dT*dT / 2;

            //Corrections
            derivatives = corrector(newDerivatives, dT, alpha, dR2);

            particle.setX(derivatives.get(0));
            particle.setSpeedX(derivatives.get(1));

            time += dT;
        }
        return states;
    }


    public static List<Double> predictor(List<Double> der, double dT){
        List<Double> newDerivatives = new ArrayList<>();

        newDerivatives.add(der.get(0)
                + der.get(1) * dT
                + der.get(2) * dT * dT / 2
                + der.get(3) * dT * dT * dT / 6
                + der.get(4) * dT * dT * dT * dT / 24
                + der.get(5) * dT * dT * dT * dT * dT / 120
        );
        newDerivatives.add(der.get(1)
                + der.get(2) * dT
                + der.get(3) * dT * dT / 2
                + der.get(4) * dT * dT * dT / 6
                + der.get(5) * dT * dT * dT * dT / 24
        );
        newDerivatives.add(der.get(2)
                + der.get(3) * dT
                + der.get(4) * dT * dT / 2
                + der.get(5) * dT * dT * dT / 6
        );
        newDerivatives.add(der.get(3)
                + der.get(4) * dT
                + der.get(5) * dT * dT / 2
        );
        newDerivatives.add(der.get(4)
                + der.get(5) * dT
        );
        newDerivatives.add(der.get(5));
        return newDerivatives;
    }

    public static List<Double> corrector(List<Double> der, double dT, double[] alpha, double dR2){
        List<Double> newDerivatives = new ArrayList<>();

        newDerivatives.add(der.get(0)
                + (alpha[0] * dR2 * 1)
        );
        newDerivatives.add(der.get(1)
                + (alpha[1] * dR2 * 1) / (dT)
        );
        newDerivatives.add(der.get(2)
                + (alpha[2] * dR2 * 2) / (dT * dT)
        );
        newDerivatives.add(
                der.get(3) + (alpha[3] * dR2 * 6) / (dT * dT * dT)
        );
        newDerivatives.add(
                der.get(4) + (alpha[4] * dR2 * 24) / (dT * dT * dT * dT)
        );
        newDerivatives.add(
                der.get(5) + (alpha[5] * dR2 * 120) / (dT * dT * dT * dT * dT)
        );
        return newDerivatives;
    }


}
