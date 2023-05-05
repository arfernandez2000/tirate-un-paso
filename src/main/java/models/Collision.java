package models;

import java.util.List;
import java.util.Map;

public class Collision implements Comparable<Collision> {
    Ball ball1;
    Ball ball2;


    double time;

    double timeToCollision;

    public Collision(Ball ball1, Ball ball2, double timeToCollision, double time) {
        this.ball1 = ball1;
        this.ball2 = ball2;
        this.time = time;
        this.timeToCollision = timeToCollision;
    }

    public boolean wasSuperveningEvent(Map<Ball, List<Double>> balls, double currentTime){
        return false;
    }

    public Ball getBall1() {
        return ball1;
    }

    public void setBall1(Ball ball1) {
        this.ball1 = ball1;
    }

    public Ball getBall2() {
        return ball2;
    }

    public void setBall2(Ball ball2) {
        this.ball2 = ball2;
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTimeToCollision() {
        return timeToCollision;
    }

    public void setTimeToCollision(double timeToCollision) {
        this.timeToCollision = timeToCollision;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Collision collision = (Collision) o;
//
//        if (!Objects.equals(ball1, collision.ball1)) return false;
//        return Objects.equals(ball2, collision.ball2);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = ball1 != null ? ball1.hashCode() : 0;
//        result = 31 * result + (ball2 != null ? ball2.hashCode() : 0);
//        return result;
//    }

    @Override
    public int compareTo(Collision o) {
        if (this.timeToCollision < o.timeToCollision) {
            return -1;
        } else if (this.timeToCollision > o.timeToCollision) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Collision{" +
                "ball1=" + ball1 +
                ", ball2=" + ball2 +
                ", time=" + timeToCollision +
                '}';
    }
}
