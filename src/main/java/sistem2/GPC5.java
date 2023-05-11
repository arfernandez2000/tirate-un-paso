package sistem2;

import models.Ball;
import utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class GPC5 {

    private static final ArrayList<Ball> balls = new ArrayList<>();

    public static List<ArrayList<Double>> gear(double dT, double tf){

        List<ArrayList<Double>> states = new ArrayList<>();
        double t = dT;
        List<List<ArrayList<Double>>> currentRs = Rs;

        while(t <= tf) {
//            System.out.println("t: " + t);
            // me guardo el estado
            for (Ball ball : balls) {
                ArrayList<Double> state = new ArrayList<>();
                ArrayList<Double> position = currentRs.get(ball.getId()).get(0);
                ArrayList<Double> velocities = currentRs.get(ball.getId()).get(1);

                state.add(t);
                state.add((double) ball.getId());
                state.add(position.get(0));
                state.add(position.get(1));
                state.add(velocities.get(0));
                state.add(velocities.get(1));
                states.add(state);
            }

            //predicciones
            List<List<ArrayList<Double>>> newDerivatives = gearPredictor(currentRs, dT);

            //evaluar
            List<ArrayList<Double>> deltasR2 = getR2(newDerivatives,dT);

            //correccion
            currentRs = gearCorrector(newDerivatives, dT, deltasR2);

            t += dT;
//            System.out.println("dps de una vuelta" + currentRs);
        }
        return states;
    }
    public static List<List<Tuple>> initialRs(){
        List<List<Tuple>> R = new ArrayList<>();
//        Lista de particulas -> cada una tiene una lista de Rs (que son tuplas)
//       [Sol: [ [r0x r0y] [r1x r1y] ] [ [r0x r0y] [r1x r1y] ], Tierra: [ [r0x r0y] [r1x r1y] ] [ [r0x r0y] [r1x r1y] ]]
        Tuple r;
        for (Ball ball: balls) {
            List<Tuple> auxR = new ArrayList<>();
            //r0
            r = new Tuple(ball.getX(), ball.getY());
            auxR.add(r);
            //r1
            r = new Tuple(ball.getVx(), ball.getVy());
            auxR.add(r);
            //r2
            Tuple a = calculateA(ball,null);
            r = new Tuple(a.getA(), a.getB());
            auxR.add(r);
            //r3
            r = new Tuple(0.0, 0.0);
            auxR.add(r);
            //r4
            auxR.add(r);
            //r5
            auxR.add(r);

            R.add(auxR);
        }
        return R;
    }

    public static List<List<ArrayList<Double>>> gearPredictor(List<List<ArrayList<Double>>> der, double dT){
//       [ Sol:[ [r0x r0y], [r1x r1y].. ] Tierra:[ [r0x r0y], [r1x r1y].. ] ]
        List<List<ArrayList<Double>>> newDerivatives = new ArrayList<>();

        for(List<ArrayList<Double>> rs : der) {  // para cada pelota
            List<ArrayList<Double>> auxNewDerivatives = new ArrayList<>();

            double r0x = rs.get(0).get(0) + rs.get(1).get(0) * dT + rs.get(2).get(0) * dT * dT / 2 + rs.get(3).get(0) * dT * dT * dT / 6 + rs.get(4).get(0) * dT * dT * dT * dT / 24 + rs.get(5).get(0) * dT * dT * dT * dT * dT / 120;
            double r0y = rs.get(0).get(1) + rs.get(1).get(1) * dT + rs.get(2).get(1) * dT * dT / 2 + rs.get(3).get(1) * dT * dT * dT / 6 + rs.get(4).get(1) * dT * dT * dT * dT / 24 + rs.get(5).get(1) * dT * dT * dT * dT * dT / 120;
            ArrayList<Double> r0 = new ArrayList<>();
            r0.add(r0x);
            r0.add(r0y);
            auxNewDerivatives.add(r0);

            double r1x = rs.get(1).get(0) + rs.get(2).get(0) * dT + rs.get(3).get(0) * dT * dT / 2 + rs.get(4).get(0) * dT * dT * dT / 6 + rs.get(5).get(0) * dT * dT * dT * dT / 24;
            double r1y = rs.get(1).get(1) + rs.get(2).get(1) * dT + rs.get(3).get(1) * dT * dT / 2 + rs.get(4).get(1) * dT * dT * dT / 6 + rs.get(5).get(1) * dT * dT * dT * dT / 24;
            ArrayList<Double> r1 = new ArrayList<>();
            r1.add(r1x);
            r1.add(r1y);
            auxNewDerivatives.add(r1);

            double r2x = rs.get(2).get(0) + rs.get(3).get(0) * dT + rs.get(4).get(0) * dT * dT / 2 + rs.get(5).get(0) * dT * dT * dT / 6;
            double r2y = rs.get(2).get(1) + rs.get(3).get(1) * dT + rs.get(4).get(1) * dT * dT / 2 + rs.get(5).get(1) * dT * dT * dT / 6;
            ArrayList<Double> r2 = new ArrayList<>();
            r2.add(r2x);
            r2.add(r2y);
            auxNewDerivatives.add(r2);

            double r3x = rs.get(3).get(0) + rs.get(4).get(0) * dT + rs.get(5).get(0) * dT * dT / 2;
            double r3y = rs.get(3).get(1) + rs.get(4).get(1) * dT + rs.get(5).get(1) * dT * dT / 2;
            ArrayList<Double> r3 = new ArrayList<>();
            r3.add(r3x);
            r3.add(r3y);
            auxNewDerivatives.add(r3);

            double r4x = rs.get(4).get(0) + rs.get(5).get(0) * dT;
            double r4y = rs.get(4).get(1) + rs.get(5).get(1) * dT;
            ArrayList<Double> r4 = new ArrayList<>();
            r4.add(r4x);
            r4.add(r4y);
            auxNewDerivatives.add(r4);

            double r5x = rs.get(5).get(0);
            double r5y = rs.get(5).get(1);
            ArrayList<Double> r5 = new ArrayList<>();
            r5.add(r5x);
            r5.add(r5y);
            auxNewDerivatives.add(r5);
            newDerivatives.add(auxNewDerivatives);
        }
        return newDerivatives;
    }

    public static List<List<ArrayList<Double>>> gearCorrector(List<List<ArrayList<Double>>> der, double dT, List<ArrayList<Double>>  dR2){
        // [ [ [r0x r0y] [r1x r1y] ] [ [r2x r2y] [r3x r3y] ] ]
        List<List<ArrayList<Double>>> newDerivatives = new ArrayList<>();
        int count = 0;
        for(List<ArrayList<Double>> rs : der) { // por planeta

            List<ArrayList<Double>> auxNewDerivatives = new ArrayList<>();

            // dR2 = [ sol: [dr2x dr2y] ,tierra: [dr2x dr2y] ,venus: [dr2x dr2y], nave: [dr2x dr2y] ]
            double r0x = rs.get(0).get(0) + (alpha[0] * dR2.get(count).get(0));
            double r0y = rs.get(0).get(1) + (alpha[0] * dR2.get(count).get(1));
            ArrayList<Double> r0 = new ArrayList<>();
            r0.add(r0x);
            r0.add(r0y);
            auxNewDerivatives.add(r0);

            double r1x = rs.get(1).get(0) + (alpha[1] * dR2.get(count).get(0) * 1 ) / (dT);
            double r1y = rs.get(1).get(1) + (alpha[1] * dR2.get(count).get(0) * 1 ) / (dT);
            ArrayList<Double> r1 = new ArrayList<>();
            r1.add(r1x);
            r1.add(r1y);
            auxNewDerivatives.add(r1);


            double r2x = rs.get(2).get(0) + (alpha[2] * dR2.get(count).get(0) * 2) / (dT * dT);
            double r2y = rs.get(2).get(1) + (alpha[2] * dR2.get(count).get(1) * 2) / (dT * dT);
            ArrayList<Double> r2 = new ArrayList<>();
            r2.add(r2x);
            r2.add(r2y);
            auxNewDerivatives.add(r2);


            double r3x = rs.get(3).get(0) + (alpha[3] * dR2.get(count).get(0) * 6) / (dT * dT * dT);
            double r3y = rs.get(3).get(1) + (alpha[3] * dR2.get(count).get(1) * 6) / (dT * dT * dT);
            ArrayList<Double> r3 = new ArrayList<>();
            r3.add(r3x);
            r3.add(r3y);
            auxNewDerivatives.add(r3);


            double r4x = rs.get(4).get(0) + (alpha[4] * dR2.get(count).get(0) * 24) / (dT * dT * dT * dT);
            double r4y = rs.get(4).get(1) + (alpha[4] * dR2.get(count).get(1) * 24) / (dT * dT * dT * dT);
            ArrayList<Double> r4 = new ArrayList<>();
            r4.add(r4x);
            r4.add(r4y);
            auxNewDerivatives.add(r4);

            double r5x = rs.get(5).get(0) + (alpha[5] * dR2.get(count).get(0) * 120) / (dT * dT * dT * dT * dT);
            double r5y = rs.get(5).get(1) + (alpha[5] * dR2.get(count).get(1) * 120) / (dT * dT * dT * dT * dT);
            ArrayList<Double> r5 = new ArrayList<>();
            r5.add(r5x);
            r5.add(r5y);
            auxNewDerivatives.add(r5);

            count++;
            newDerivatives.add(auxNewDerivatives);
        }
        return newDerivatives;
    }

    private static Tuple calculateA(Ball ball, List<List<ArrayList<Double>>> Rs) {

        // TODO calcular la fueraza de cada particula

        return new Tuple(0.0, 0.0);
    }

    private static List<ArrayList<Double>> getR2(List<List<ArrayList<Double>>> newDerivatives, double dT){
        List<ArrayList<Double>> deltasR2 = new ArrayList<>();
        for (Ball ball : balls) {

            Tuple A = calculateA(ball, newDerivatives);
            // TODO a checkear
            ArrayList<Double> r2 = newDerivatives.get(ball.getId()).get(2);

            double dR2X = (A.getA() - r2.get(0)) * dT*dT / 2;
            double dR2Y = (A.getB() - r2.get(1)) * dT*dT / 2;

            ArrayList<Double> deltaR2 = new ArrayList<>();
            deltaR2.add(dR2X);
            deltaR2.add(dR2Y);
            deltasR2.add(deltaR2);
        }
        return deltasR2;
        // [ sol: [dr2x dr2y] ,tierra: [dr2x dr2y] ,venus: [dr2x dr2y], nave: [dr2x dr2y] ]
    }
}
