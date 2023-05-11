package sistem2;

import models.Ball;
import utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class GPC5 {

    private static final double[] alpha = { 3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60 };
    private static final List<Ball> balls = new ArrayList<>();
    private static List<List<Tuple>> Rs;
    private static final double dT = 200;

    private static final double tf = 1000;

    public static List<List<Double>> gear() {

        List<List<Double>> states = new ArrayList<>();
        double t = dT;
        List<List<Tuple>> currentRs = Rs;

        while (t <= tf) {
            for (Ball ball : balls) {
                ArrayList<Double> state = new ArrayList<>();
                Tuple position = currentRs.get(ball.getId()).get(0);
                Tuple velocity = currentRs.get(ball.getId()).get(1);

                state.add(t);
                state.add((double) ball.getId());
                state.add(position.getA());
                state.add(position.getB());
                state.add(velocity.getA());
                state.add(velocity.getB());

                states.add(state);
            }

            // Predicciones
            List<List<Tuple>> newDerivatives = gearPredictor(currentRs, dT);

            // Evaluar
            List<Tuple> deltasR2 = getR2(newDerivatives,dT);

            // Correccion
            currentRs = gearCorrector(newDerivatives, dT, deltasR2);

            t += dT;
        }
        return states;
    }
    public static void initialRs(){
//      Lista de particulas -> cada una tiene una lista de Rs (que son tuplas)
        for (Ball ball: balls) {
            List<Tuple> auxR = new ArrayList<>();

            //r0
            auxR.add(new Tuple(ball.getX(), ball.getY()));
            //r1
            auxR.add(new Tuple(ball.getSpeedX(), ball.getSpeedY()));
            //r2
            auxR.add(calculateA(ball,null));
            //r3
            auxR.add(new Tuple(0.0, 0.0));
            //r4
            auxR.add(new Tuple(0.0, 0.0));
            //r5
            auxR.add(new Tuple(0.0, 0.0));

            Rs.add(auxR);
        }
    }

    public static List<List<Tuple>> gearPredictor(List<List<Tuple>> der, double dT){
//       [ Sol:[ [r0x r0y], [r1x r1y].. ] Tierra:[ [r0x r0y], [r1x r1y].. ] ]
        List<List<Tuple>> newDerivatives = new ArrayList<>();

        for(List<Tuple> rs : der) {  // para cada pelota
            List<Tuple> auxNewDerivatives = new ArrayList<>();

            // TODO Habria que refactorear todas las cuentas para que usen la tupla en vez del ArrayList de los doubles x,y

            double r0x = rs.get(0).getA() + rs.get(1).getA() * dT + rs.get(2).getA() * dT * dT / 2 + rs.get(3).getA() * dT * dT * dT / 6 + rs.get(4).getA() * dT * dT * dT * dT / 24 + rs.get(5).getA() * dT * dT * dT * dT * dT / 120;
            double r0y = rs.get(0).getB() + rs.get(1).getB() * dT + rs.get(2).getB() * dT * dT / 2 + rs.get(3).getB() * dT * dT * dT / 6 + rs.get(4).getB() * dT * dT * dT * dT / 24 + rs.get(5).getB() * dT * dT * dT * dT * dT / 120;
            Tuple r0 = new Tuple(r0x, r0y);
            auxNewDerivatives.add(r0);

            double r1x = rs.get(1).getA() + rs.get(2).getA() * dT + rs.get(3).getA() * dT * dT / 2 + rs.get(4).getA() * dT * dT * dT / 6 + rs.get(5).getA() * dT * dT * dT * dT / 24;
            double r1y = rs.get(1).getB() + rs.get(2).getB() * dT + rs.get(3).getB() * dT * dT / 2 + rs.get(4).getB() * dT * dT * dT / 6 + rs.get(5).getB() * dT * dT * dT * dT / 24;
            Tuple r1 = new Tuple(r1x, r1y);
            auxNewDerivatives.add(r1);

            double r2x = rs.get(2).getA() + rs.get(3).getA() * dT + rs.get(4).getA() * dT * dT / 2 + rs.get(5).getA() * dT * dT * dT / 6;
            double r2y = rs.get(2).getB() + rs.get(3).getB() * dT + rs.get(4).getB() * dT * dT / 2 + rs.get(5).getB() * dT * dT * dT / 6;
            Tuple r2 = new Tuple(r2x, r2y);
            auxNewDerivatives.add(r2);

            double r3x = rs.get(3).getA() + rs.get(4).getA() * dT + rs.get(5).getA() * dT * dT / 2;
            double r3y = rs.get(3).getB() + rs.get(4).getB() * dT + rs.get(5).getB() * dT * dT / 2;
            Tuple r3 = new Tuple(r3x, r3y);
            auxNewDerivatives.add(r3);

            double r4x = rs.get(4).getA() + rs.get(5).getA() * dT;
            double r4y = rs.get(4).getB() + rs.get(5).getB() * dT;
            Tuple r4 = new Tuple(r4x, r4y);
            auxNewDerivatives.add(r4);

            double r5x = rs.get(5).getA();
            double r5y = rs.get(5).getB();
            Tuple r5 = new Tuple(r5x, r5y);
            auxNewDerivatives.add(r5);

            newDerivatives.add(auxNewDerivatives);
        }
        return newDerivatives;
    }

    public static List<List<Tuple>> gearCorrector(List<List<Tuple>> der, double dT, List<Tuple> dR2){
        // [ [ [r0x r0y] [r1x r1y] ] [ [r2x r2y] [r3x r3y] ] ]
        List<List<Tuple>> newDerivatives = new ArrayList<>();
        int count = 0;
        for(List<Tuple> rs : der) { // por planeta

            List<Tuple> auxNewDerivatives = new ArrayList<>();

            // dR2 = [ sol: [dr2x dr2y] ,tierra: [dr2x dr2y] ,venus: [dr2x dr2y], nave: [dr2x dr2y] ]
            double r0x = rs.get(0).getA() + (alpha[0] * dR2.get(count).getA());
            double r0y = rs.get(0).getB() + (alpha[0] * dR2.get(count).getB());
            Tuple r0 = new Tuple(r0x, r0y);
            auxNewDerivatives.add(r0);

            double r1x = rs.get(1).getA() + (alpha[1] * dR2.get(count).getA() * 1 ) / (dT);
            double r1y = rs.get(1).getB() + (alpha[1] * dR2.get(count).getA() * 1 ) / (dT);
            Tuple r1 = new Tuple(r1x, r1y);
            auxNewDerivatives.add(r1);


            double r2x = rs.get(2).getA() + (alpha[2] * dR2.get(count).getA() * 2) / (dT * dT);
            double r2y = rs.get(2).getB() + (alpha[2] * dR2.get(count).getB() * 2) / (dT * dT);
            Tuple r2 = new Tuple(r2x, r2y);
            auxNewDerivatives.add(r2);


            double r3x = rs.get(3).getA() + (alpha[3] * dR2.get(count).getA() * 6) / (dT * dT * dT);
            double r3y = rs.get(3).getB() + (alpha[3] * dR2.get(count).getB() * 6) / (dT * dT * dT);
            Tuple r3 = new Tuple(r3x, r3y);
            auxNewDerivatives.add(r3);


            double r4x = rs.get(4).getA() + (alpha[4] * dR2.get(count).getA() * 24) / (dT * dT * dT * dT);
            double r4y = rs.get(4).getB() + (alpha[4] * dR2.get(count).getB() * 24) / (dT * dT * dT * dT);
            Tuple r4 = new Tuple(r4x, r4y);
            auxNewDerivatives.add(r4);

            double r5x = rs.get(5).getA() + (alpha[5] * dR2.get(count).getA() * 120) / (dT * dT * dT * dT * dT);
            double r5y = rs.get(5).getB() + (alpha[5] * dR2.get(count).getB() * 120) / (dT * dT * dT * dT * dT);
            Tuple r5 = new Tuple(r5x, r5y);
            auxNewDerivatives.add(r5);

            count++;
            newDerivatives.add(auxNewDerivatives);
        }
        return newDerivatives;
    }

    private static Tuple calculateA(Ball ball, List<List<Tuple>> Rs) {

        // TODO calcular la fueraza de cada particula

        return new Tuple(0.0, 0.0);
    }

    private static List<Tuple> getR2(List<List<Tuple>> newDerivatives, double dT){
        List<Tuple> deltasR2 = new ArrayList<>();
        for (Ball ball : balls) {

            Tuple A = calculateA(ball, newDerivatives);
            // TODO a checkear
            Tuple r2 = newDerivatives.get(ball.getId()).get(2);

            double dR2X = (A.getA() - r2.getA()) * dT*dT / 2;
            double dR2Y = (A.getB() - r2.getB()) * dT*dT / 2;

            deltasR2.add(new Tuple(dR2X, dR2Y));
        }
        return deltasR2;
        // [ sol: [dr2x dr2y] ,tierra: [dr2x dr2y] ,venus: [dr2x dr2y], nave: [dr2x dr2y] ]
    }
}
