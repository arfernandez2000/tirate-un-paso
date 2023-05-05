package models;

public class Particle {

    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private int mass;
    private double acceleration;


    public Particle(double xPos, double yPos, double xVel, double yVel, int weight) {
        this.x = xPos;
        this.y = yPos;
        this.speedX = xVel;
        this.speedY = yVel;
        this.mass = weight;
        this.acceleration = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
}
