package models;

import utils.Tuple;

public class Ball {
    private static int global_id = 0;
    private int id;
    private double x;
    private double y;
    private double radius;
    private double mass;
    private double speedX;
    private double speedY;
    private double accX;
    private double accY;
    private Tuple force;
    private boolean isHole;
    private boolean isDisabled = false;

    public Ball(double x, double y, double radius, double speedX, double speedY, double mass,  boolean isHole) {
        this.id = global_id++;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.mass = mass;
        this.isHole = isHole;
        this.accX = 0;
        this.accY = 0;
    }

    public Ball(double x, double y, double radius, double mass, boolean isHole) {
        this.id = global_id++;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = 0;
        this.speedY = 0;
        this.mass = mass;
        this.isHole = isHole;
        this.accX = 0;
        this.accY = 0;
    }

    public Tuple force(Ball b) {
        double diffX = b.getX() - getX();
        double diffY = b.getY() - getY();
        double distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
        double radius = getRadius() + b.getRadius();

        if(distance > radius) {
            return new Tuple(0, 0);
        }

        double f = 100 * (distance - radius);
        return new Tuple(f * diffX / distance, f * diffY / distance);
    }

    public Tuple forceVerticalWall(Wall wall) {
        double f = 0;
        int k = 100;

        if(wall.isRightWall()) {
            if(getX() + getRadius() <= wall.getX()) {
                return new Tuple(0, 0);
            }
            double xDiff = Math.abs(getX() + getRadius() - wall.getX());
            f = -k * Math.abs(xDiff);
        } else {
            if(getX() - getRadius() >= wall.getX()) {
                return new Tuple(0, 0);
            }
            double xDiff = Math.abs(getX() - getRadius() - wall.getX());
            f = k * Math.abs(xDiff);
        }

        return new Tuple(f, 0);

    }

    public Tuple forceHorizontalWall(Wall wall) {
        double f = 0;
        int k = 100;


        if(wall.isTopWall()) {
            if(getY() + getRadius() <= wall.getY()) {
                return new Tuple(0, 0);
            }
            double yDiff = Math.abs(getY() + getRadius() - wall.getY());
            f = -k * Math.abs(yDiff);
        } else {
            if(getY() - getRadius() >= wall.getY()) {
                return new Tuple(0, 0);
            }
            double yDiff = Math.abs(getY() - getRadius() - wall.getY());
            f = k * Math.abs(yDiff);
        }

        return new Tuple(0, f);
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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

    public boolean isHole() {
        return isHole;
    }

    public void setHole(boolean hole) {
        isHole = hole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Tuple getForce() {
        return force;
    }

    public void setForce(Tuple force) {
        this.force = force;
    }

    public double getAccX() {
        return accX;
    }

    public void setAccX(double accX) {
        this.accX = accX;
    }

    public double getAccY() {
        return accY;
    }

    public void setAccY(double accY) {
        this.accY = accY;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void disable() {
        isDisabled = true;
    }

    public static void resetGlobalId() {
        global_id = 0;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
