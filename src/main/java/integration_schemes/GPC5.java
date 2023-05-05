package integration_schemes;

import models.Ball;
import models.Particle;
import utils.Tuple;

public class GPC5 implements IntegrationScheme {

    Tuple[] previous = new Tuple[6];
    double k = Math.pow(10,4);
    double gamma = 100;

    public void calculatePrevious(Particle particle){
        previous[0] = new Tuple(particle.getX(), particle.getSpeedY());;
        previous[1] = new Tuple(particle.getX(), particle.getSpeedY());

        double coef = -k / particle.getMass();

        previous[2] = previous[0].multiply(coef);
        previous[3] = previous[1].multiply(coef);
        previous[4] = previous[2].multiply(coef);
        previous[5] = previous[3].multiply(coef);

    }

    @Override
    public void nextStep(double dT, Particle particle) {
        Tuple [] actual = new Tuple[6];

        double secondCoef = Math.pow(dT, 2) / 2;
        double thirdCoef = Math.pow(dT, 3) / 6;
        double fourthCoef = Math.pow(dT, 4) / 24;
        double fifthCoef = Math.pow(dT, 5) / 120;

        //prediction
        actual[0] = previous[0]
                .add(previous[1].multiply(dT))
                .add(previous[2].multiply(secondCoef))
                .add(previous[3].multiply(thirdCoef))
                .add(previous[4].multiply(fourthCoef))
                .add(previous[5].multiply(fifthCoef));

        actual[1] = previous[1]
                .add(previous[2].multiply(dT))
                .add(previous[3].multiply(secondCoef))
                .add(previous[4].multiply(thirdCoef))
                .add(previous[5].multiply(fourthCoef));

        actual[2] = previous[2]
                .add(previous[3].multiply(dT))
                .add(previous[4].multiply(secondCoef))
                .add(previous[5].multiply(thirdCoef));

        actual[3] = previous[3]
                .add(previous[4].multiply(dT))
                .add(previous[5].multiply(secondCoef));

        actual[4] = previous[4]
                .add(previous[5].multiply(dT));

        actual[5] = previous[5];

        //evaluation
        Tuple deltaA = new Tuple((-k * previous[0].getA() - gamma * previous[1].getA())/particle.getMass(), 0)
                .subtract(actual[2]);

        Tuple deltaR2 = deltaA.multiply( Math.pow(dT,2))
                .divide(2);

        //correction
        previous[0] = actual[0]
                .add(deltaR2.multiply(((3.0/20))));

        previous[1] = actual[1]
                .add(deltaR2.multiply(((251.0/360)) / (dT)));

        previous[2] = actual[2]
                .add(deltaR2.multiply((2 / Math.pow(dT, 2))));

        previous[3] = actual[3]
                .add(deltaR2.multiply(((11.0/18)*6) / Math.pow(dT, 3)));

        previous[4] = actual[4]
                .add( deltaR2.multiply(((1.0/6)*24) / Math.pow(dT, 4)));

        previous[5] = actual[5]
                .add( deltaR2.multiply(((1.0/60)*120) / Math.pow(dT, 5)));

        //set new values
        particle.setX(previous[0].getA());
        particle.setSpeedX(previous[1].getA());

        particle.setY(previous[0].getB());
        particle.setSpeedY(previous[1].getB());
    }


    @Override
    public void netStep(double dT, Ball ball) {
        //TODO Implement
    }
}
