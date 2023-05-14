package sistem2;

import models.Ball;
import models.Wall;
import utils.Tuple;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class GPC5 {
    private static final int PRECISION = 3;
    private static final double FINAL_TIME = 100;
    private static final double[] alpha = { 3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60 };
    private static List<Ball> balls;
    private static final Map<Integer, List<Tuple>> Rs = new HashMap<>();
    private static final double dT = Math.pow(10, -PRECISION);

    public static void gear() {

        try {
            FileWriter myWriter = new FileWriter("src/main/resources/states_" + PRECISION +".txt");
            PrintWriter printWriter = new PrintWriter(myWriter);

            double t = dT;
            Map<Integer, List<Tuple>> currentRs = Rs;
            int gen = 0;
            while (balls.stream().filter(b -> b.isDisabled()).count() < 16) {
                for (Ball ball : balls) {
                    if (ball.getId() >= 16 || ball.isDisabled())
                        break;
                    ball.setX(currentRs.get(ball.getId()).get(0).getA());
                    ball.setY(currentRs.get(ball.getId()).get(0).getB());
                    ball.setSpeedX(currentRs.get(ball.getId()).get(1).getA());
                    ball.setSpeedY(currentRs.get(ball.getId()).get(1).getB());
                    ball.setAccX(currentRs.get(ball.getId()).get(3).getA());
                    ball.setAccY(currentRs.get(ball.getId()).get(3).getB());
                }

                printWriter.println(printBalls(balls, balls.size(), t, gen));
//                if(gen >= 1062)
//                    System.out.println("debug");
                // Prediction
                Map<Integer, List<Tuple>> newDerivatives = gearPredictor(currentRs);
                // Evaluate
                Map<Integer, Tuple> deltasR2 = getR2(newDerivatives);

                // Correction
                currentRs = gearCorrector(newDerivatives, deltasR2);

                t += dT;
                gen ++;
            }


            System.out.println("T: " + t);

            printWriter.close();
            myWriter.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public static void initialRs(List<Ball> ballList){
        //Lista de particulas -> cada una tiene una lista de Rs (que son tuplas)
        balls = ballList;
        for (Ball ball: balls) {
            if (ball.getId() >= 16)
                break;
            List<Tuple> auxR = new ArrayList<>();

            //r0
            auxR.add(new Tuple(ball.getX(), ball.getY()));
            //r1
            auxR.add(new Tuple(ball.getSpeedX(), ball.getSpeedY()));
            //r2
            auxR.add(sumForces(ball, new ArrayList<>()));
            //r3
            auxR.add(new Tuple(0.0, 0.0));
            //r4
            auxR.add(new Tuple(0.0, 0.0));
            //r5
            auxR.add(new Tuple(0.0, 0.0));

            Rs.put(ball.getId(), auxR);
        }
    }

    public static Map<Integer, List<Tuple>> gearPredictor(Map<Integer, List<Tuple>> der){
        Map<Integer, List<Tuple>> newDerivatives = new HashMap<>();
        for (Map.Entry<Integer, List<Tuple>> rs : der.entrySet()) {  // para cada pelota
            List<Tuple> auxNewDerivatives = new ArrayList<>();

            double r0x = rs.getValue().get(0).getA() + rs.getValue().get(1).getA() * dT + rs.getValue().get(2).getA() * dT * dT / 2 + rs.getValue().get(3).getA() * dT * dT * dT / 6 + rs.getValue().get(4).getA() * dT * dT * dT * dT / 24 + rs.getValue().get(5).getA() * dT * dT * dT * dT * dT / 120;
            double r0y = rs.getValue().get(0).getB() + rs.getValue().get(1).getB() * dT + rs.getValue().get(2).getB() * dT * dT / 2 + rs.getValue().get(3).getB() * dT * dT * dT / 6 + rs.getValue().get(4).getB() * dT * dT * dT * dT / 24 + rs.getValue().get(5).getB() * dT * dT * dT * dT * dT / 120;
            Tuple r0 = new Tuple(r0x, r0y);
            balls.get(rs.getKey()).setX(r0x);
            balls.get(rs.getKey()).setY(r0y);
            auxNewDerivatives.add(r0);

            double r1x = rs.getValue().get(1).getA() + rs.getValue().get(2).getA() * dT + rs.getValue().get(3).getA() * dT * dT / 2 + rs.getValue().get(4).getA() * dT * dT * dT / 6 + rs.getValue().get(5).getA() * dT * dT * dT * dT / 24;
            double r1y = rs.getValue().get(1).getB() + rs.getValue().get(2).getB() * dT + rs.getValue().get(3).getB() * dT * dT / 2 + rs.getValue().get(4).getB() * dT * dT * dT / 6 + rs.getValue().get(5).getB() * dT * dT * dT * dT / 24;
            Tuple r1 = new Tuple(r1x, r1y);
            balls.get(rs.getKey()).setSpeedX(r1x);
            balls.get(rs.getKey()).setSpeedY(r1y);
            auxNewDerivatives.add(r1);

            double r2x = rs.getValue().get(2).getA() + rs.getValue().get(3).getA() * dT + rs.getValue().get(4).getA() * dT * dT / 2 + rs.getValue().get(5).getA() * dT * dT * dT / 6;
            double r2y = rs.getValue().get(2).getB() + rs.getValue().get(3).getB() * dT + rs.getValue().get(4).getB() * dT * dT / 2 + rs.getValue().get(5).getB() * dT * dT * dT / 6;
            Tuple r2 = new Tuple(r2x, r2y);
            balls.get(rs.getKey()).setAccX(r2x);
            balls.get(rs.getKey()).setAccY(r2y);
            auxNewDerivatives.add(r2);

            double r3x = rs.getValue().get(3).getA() + rs.getValue().get(4).getA() * dT + rs.getValue().get(5).getA() * dT * dT / 2;
            double r3y = rs.getValue().get(3).getB() + rs.getValue().get(4).getB() * dT + rs.getValue().get(5).getB() * dT * dT / 2;
            Tuple r3 = new Tuple(r3x, r3y);
            auxNewDerivatives.add(r3);

            double r4x = rs.getValue().get(4).getA() + rs.getValue().get(5).getA() * dT;
            double r4y = rs.getValue().get(4).getB() + rs.getValue().get(5).getB() * dT;
            Tuple r4 = new Tuple(r4x, r4y);
            auxNewDerivatives.add(r4);

            double r5x = rs.getValue().get(5).getA();
            double r5y = rs.getValue().get(5).getB();
            Tuple r5 = new Tuple(r5x, r5y);
            auxNewDerivatives.add(r5);

            newDerivatives.put(rs.getKey(), auxNewDerivatives);
        }
        return newDerivatives;
    }

    public static Map<Integer, List<Tuple>> gearCorrector(Map<Integer, List<Tuple>> der, Map<Integer, Tuple> dR2){
        // [ [ [r0x r0y] [r1x r1y] ] [ [r2x r2y] [r3x r3y] ] ]
        Map<Integer, List<Tuple>> newDerivatives = new HashMap<>();
        for (Map.Entry<Integer, List<Tuple>> rs : der.entrySet()) { // por pelota

            List<Tuple> auxNewDerivatives = new ArrayList<>();

            double r0x = rs.getValue().get(0).getA() + (alpha[0] * dR2.get(rs.getKey()).getA());
            double r0y = rs.getValue().get(0).getB() + (alpha[0] * dR2.get(rs.getKey()).getB());
            Tuple r0 = new Tuple(r0x, r0y);
            auxNewDerivatives.add(r0);

            double r1x = rs.getValue().get(1).getA() + (alpha[1] * dR2.get(rs.getKey()).getA() * 1 ) / (dT);
            double r1y = rs.getValue().get(1).getB() + (alpha[1] * dR2.get(rs.getKey()).getA() * 1 ) / (dT);
            Tuple r1 = new Tuple(r1x, r1y);
            auxNewDerivatives.add(r1);

            double r2x = rs.getValue().get(2).getA() + (alpha[2] * dR2.get(rs.getKey()).getA() * 2) / (dT * dT);
            double r2y = rs.getValue().get(2).getB() + (alpha[2] * dR2.get(rs.getKey()).getB() * 2) / (dT * dT);
            Tuple r2 = new Tuple(r2x, r2y);
            auxNewDerivatives.add(r2);

            double r3x = rs.getValue().get(3).getA() + (alpha[3] * dR2.get(rs.getKey()).getA() * 6) / (dT * dT * dT);
            double r3y = rs.getValue().get(3).getB() + (alpha[3] * dR2.get(rs.getKey()).getB() * 6) / (dT * dT * dT);
            Tuple r3 = new Tuple(r3x, r3y);
            auxNewDerivatives.add(r3);

            double r4x = rs.getValue().get(4).getA() + (alpha[4] * dR2.get(rs.getKey()).getA() * 24) / (dT * dT * dT * dT);
            double r4y = rs.getValue().get(4).getB() + (alpha[4] * dR2.get(rs.getKey()).getB() * 24) / (dT * dT * dT * dT);
            Tuple r4 = new Tuple(r4x, r4y);
            auxNewDerivatives.add(r4);

            double r5x = rs.getValue().get(5).getA() + (alpha[5] * dR2.get(rs.getKey()).getA() * 120) / (dT * dT * dT * dT * dT);
            double r5y = rs.getValue().get(5).getB() + (alpha[5] * dR2.get(rs.getKey()).getB() * 120) / (dT * dT * dT * dT * dT);
            Tuple r5 = new Tuple(r5x, r5y);
            auxNewDerivatives.add(r5);

            newDerivatives.put(rs.getKey(), auxNewDerivatives);
        }
        return newDerivatives;
    }

    private static Tuple sumForces(Ball ball, List<Integer> ballIdToDelete) {
        Tuple force = new Tuple(0.0, 0.0);
        for (Ball target : balls) {
            if (target.isDisabled())
                continue;
            if (ball.getId() != target.getId()) {
                Tuple forces = ball.force(target);
                if (!forces.isEmpty() && target.getId() >= 16) {
                    ballIdToDelete.add(ball.getId());
                    return new Tuple(0.0, 0.0);
                }
                force.add(forces);
            }
        }

        force.add(ball.forceHorizontalWall(Wall.TOP));
        force.add(ball.forceHorizontalWall(Wall.BOTTOM));
        force.add(ball.forceVerticalWall(Wall.RIGHT));
        force.add(ball.forceVerticalWall(Wall.LEFT));

        return force;
    }

    private static Map<Integer, Tuple> getR2(Map<Integer, List<Tuple>> newDerivatives){
        Map<Integer, Tuple> deltasR2 = new HashMap<>();
        for (Ball ball : balls) {
            if (ball.getId() >= 16)
                break;
            List<Integer> toDelete = new ArrayList<>();
            Tuple F = sumForces(ball, toDelete);
            if (!toDelete.isEmpty()) {
                newDerivatives.remove(toDelete.get(0));
                ball.disable();
                continue;
            }
            ball.setForce(F);

            Tuple r2 = newDerivatives.get(ball.getId()).get(2);

            double dR2X = (F.getA() / ball.getMass() - r2.getA()) * dT*dT / 2;

            double dR2Y = (F.getB() / ball.getMass() - r2.getB()) * dT*dT / 2;

            deltasR2.put(ball.getId(), new Tuple(dR2X, dR2Y));
        }
        return deltasR2;
    }

    static String printBalls(List<Ball> balls, int balls_left, double time, int gen) {
        StringBuilder sb = new StringBuilder();
        sb.append(balls.stream().filter(b -> !b.isDisabled()).count()).append("\n");
        sb.append(time).append("\n");
//        sb.append(gen).append("\n");
        for (Ball ball : balls) {
            if (ball.isDisabled())
                continue;
            String sb_line = ball.getId() + "\t" +
                    ball.getX() + "\t" +
                    ball.getY() + "\t" +
                    ball.getSpeedX() + "\t" +
                    ball.getSpeedY() + "\t" +
                    ball.getRadius() + "\t" +
                    ball.getMass() + "\t";
            sb.append(sb_line).append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }
}
