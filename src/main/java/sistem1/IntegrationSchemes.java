package sistem1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IntegrationSchemes {

    public static double k = 10000;
    public static double gamma = 100;

    public void analytical(Particle particle, double dT) {
        double force = -k*particle.getX() - gamma*particle.getSpeedX();

        double x;
        double v;
        double t = 0;
        int amplitude = 1;

        List<ArrayList<Double>> states = new ArrayList<>();

        while(t <= 5) {

            ArrayList<Double> state = new ArrayList<>();
            state.add(t);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            force = -k*particle.getX() - gamma*particle.getSpeedX();

            double T1 = Math.exp(-(gamma / (2 * particle.getMass())) * t);
            double T3 = Math.pow((k / particle.getMass()) - ((Math.pow(gamma, 2) / (Math.pow(2 * particle.getMass(), 2)))), 0.5);
            double T2 = Math.cos(T3 * t);

            x = amplitude * T1 * T2;

        }
    }

    public List<ArrayList<Double>> verlet(Particle particle, double dT){

        double force = -k*particle.getX() - gamma*particle.getSpeedX();
        //calculo el valor anterior con euler con -dT
        double xBefore = eulerX(particle.getX(), particle.getSpeedX(), -dT, force, particle.getMass());

        double x;
        double v;
        double t = 0;
        List<ArrayList<Double>> states = new ArrayList<>();

        while(t <= 5){

            ArrayList<Double> state = new ArrayList<>();
            state.add(t);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            force = -k*particle.getX() - gamma*particle.getSpeedX();
            x = verletX(particle.getX(), xBefore, force, particle.getMass(), dT);

            if(t != 0) {
                v = verletV(x, xBefore, dT);
                particle.setSpeedX(v);
            }

            //update
            xBefore = particle.getX();
            particle.setX(x);
            t += dT;
        }
        return states;
    }

    public List<ArrayList<Double>> beeman(Particle particle, double dT){

        double force = -k*particle.getX() - gamma*particle.getSpeedX();
        double xBefore = eulerX(particle.getX(), particle.getSpeedX(),-dT,force,particle.getMass());
        double vBefore = eulerV(particle.getSpeedX(),force,particle.getMass(),-dT);
        double aBefore = (-k*xBefore - gamma*vBefore)/particle.getMass();

        double x;
        double v;
        double newV;
        double t = 0;
        double aAfter;
        List<ArrayList<Double>> states = new ArrayList<>();

        while(t <= 5){
            // me guardo el estado
            ArrayList<Double> state = new ArrayList<>();
            state.add(t);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            // x
            force = -k*particle.getX() - gamma*particle.getSpeedX();
            x = beemanX(particle.getX(), particle.getSpeedX(), force/particle.getMass(),aBefore,dT);
            // v prediccion
            force = -k*x - gamma*particle.getSpeedX();
            v = beemanVPredicted(particle.getSpeedX(),force/particle.getMass(),aBefore,dT);
            aAfter = (-k*x - gamma*v)/particle.getMass();
            // v correcto
            force = -k* particle.getX() - gamma*particle.getSpeedX();
            newV = beemanVCorrected(particle.getSpeedX(),aBefore,force/particle.getMass(),aAfter,dT);

            aBefore = (-k*particle.getX() - gamma*particle.getSpeedX())/particle.getMass();

            //update
            particle.setSpeedX(newV);
            particle.setX(x);

            t += dT;

        }
        return states;

    }

    public List<ArrayList<Double>> gear(Particle particle, double dT){

        List<ArrayList<Double>> states = new ArrayList<>();
        double t = 0;
        double[] alpha = {3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};
        double dA;
        double dR2;
        double force = -k*particle.getX() - gamma*particle.getSpeedX();
        List<Double> derivatives = new ArrayList<>();
        derivatives.add(particle.getX());
        derivatives.add(particle.getSpeedX());
        derivatives.add(force/particle.getMass());
        derivatives.add((-k *derivatives.get(1) - gamma*derivatives.get(2))/particle.getMass());
        derivatives.add((-k *derivatives.get(2) - gamma*derivatives.get(3))/particle.getMass());
        derivatives.add((-k *derivatives.get(3) - gamma*derivatives.get(4))/particle.getMass());

        while(t <= 5) {
            // me guardo el estado
            ArrayList<Double> state = new ArrayList<>();
            state.add(t);
            state.add(particle.getX());
            state.add(particle.getSpeedX());
            states.add(state);

            //predicciones
            List<Double> newDerivatives = gearPredictor(derivatives, dT);
            //evaluar
            dA = (-k*newDerivatives.get(0) - gamma*newDerivatives.get(1))/particle.getMass() - newDerivatives.get(2);
            dR2 = dA * dT*dT / 2;
            //correccion
            derivatives = gearCorrector(newDerivatives, dT, alpha, dR2);
            //TODO lo pisara bien?

            particle.setX(derivatives.get(0));
            particle.setSpeedX(derivatives.get(1));

            t += dT;
        }
        return states;
    }


    public static double eulerX(double x, double v, double dT, double f, double mass){
        return x + v * dT + dT*dT * (f/(2*mass));
    }

    public static double eulerV(double v, double f, double m, double dT){
        return v + dT * f / m;
    }

    public static double verletX(double x, double xBefore,double f, double mass, double dT){
        return (2 * x) - xBefore + ((dT*dT) * (f/mass));
    }

    public static double verletV(double xAfter, double xBefore, double dT){
        return (xAfter - xBefore) / (2 * dT);
    }

    public static double beemanX(double x, double v, double a, double aBefore, double dT) {
        return x + v * dT + (2f/3)*a*dT*dT-(1f/6)*aBefore*dT*dT;
    }

    public static double beemanVCorrected(double v,double aBefore, double a, double aAfter, double dT ) {
        return v + (1f/3)*aAfter*dT + (5f/6)*a*dT - (1f/6)*aBefore*dT;
    }

    public static double beemanVPredicted(double v, double a, double aBefore, double dT) {
        return v + (3f/2)*a*dT - (1f/2)*aBefore*dT;
    }

    public static List<Double> gearPredictor(List<Double> der, double dT){
        List<Double> newDerivatives = new ArrayList<>();
        newDerivatives.add(der.get(0) + der.get(1)*dT + der.get(2)*dT*dT/2 + der.get(3)*dT*dT*dT/6 + der.get(4)*dT*dT*dT*dT/24 + der.get(5)*dT*dT*dT*dT*dT/120);
        newDerivatives.add(der.get(1) + der.get(2)*dT + der.get(3)*dT*dT/2 + der.get(4)*dT*dT*dT/6 + der.get(5)*dT*dT*dT*dT/24);
        newDerivatives.add(der.get(2) + der.get(3)*dT + der.get(4)*dT*dT/2 + der.get(5)*dT*dT*dT/6);
        newDerivatives.add(der.get(3) + der.get(4)*dT + der.get(5)*dT*dT/2);
        newDerivatives.add(der.get(4) + der.get(5)*dT);
        newDerivatives.add(der.get(5));
        return newDerivatives;
    }

    public static List<Double> gearCorrector(List<Double> der, double dT, double[] alpha, double dR2){
        List<Double> newDerivatives = new ArrayList<>();
        newDerivatives.add(der.get(0) + (alpha[0] * dR2 * 1));
        newDerivatives.add(der.get(1) + (alpha[1] * dR2 * 1) / (dT));
        newDerivatives.add(der.get(2) + (alpha[2] * dR2 * 2) / (dT*dT));
        newDerivatives.add(der.get(3) + (alpha[3] * dR2 * 6) / (dT*dT*dT));
        newDerivatives.add(der.get(4) + (alpha[4] * dR2 * 24) / (dT*dT*dT*dT));
        newDerivatives.add(der.get(5) + (alpha[5] * dR2 * 120) / (dT*dT*dT*dT*dT));
        return newDerivatives;
    }

    public static void print(Particle particle, double t, PrintWriter pw) {
        pw.println(particle.getX() + "\t" + particle.getSpeedX() + "\t" + t);
    }

}
