package models;

import utils.MathUtils;

public class Ball {

    private static int global_id = 0;
    private int id;
    private double x;
    private double y;
    private double radius;
    private int mass;
    private double speedX;
    private double speedY;
    private boolean isHole;
//
//    private Set<Collision> collisions = new TreeSet<>();

    public Ball(double x, double y, double radius, double speedX, double speedY, int mass,  boolean isHole) {
        this.id = global_id++;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.mass = mass;
        this.isHole = isHole;
    }

    public Ball(double x, double y, double radius, int mass, boolean isHole) {
        this.id = global_id++;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = 0;
        this.speedY = 0;
        this.mass = mass;
        this.isHole = isHole;
    }

    public double collidesX(){
        double timeLeft = MathUtils.timeToCollisionParticleWallVertical(this, Wall.LEFT);
        double timeRight = MathUtils.timeToCollisionParticleWallVertical(this, Wall.RIGHT);
        return timeLeft >= 0? timeLeft: timeRight >= 0? timeRight: -1;
    }

    public double collidesY(){
        double timeTop = MathUtils.timeToCollisionParticleWallHorizontal(this, Wall.TOP);
        double timeBottom = MathUtils.timeToCollisionParticleWallHorizontal(this, Wall.BOTTOM);
        return timeTop >= 0? timeTop: timeBottom >= 0? timeBottom: -1;
    }

    public double collides(Ball b) {
        return MathUtils.timeToCollisionTwoParticles(this, b);
    }

    public void bounceX(double time){
        setSpeedX(-getSpeedX());
    }

    public void bounceY(double time) {
        setSpeedY(-getSpeedY());
    }

    //returns true if the balls bounced, false if one of them is a hole
    public boolean bounce(Ball b, double time) {

        if(this.isHole || b.isHole)
            return false;

        double jx = MathUtils.Jx(this, b)/this.getMass();
        double jy = MathUtils.Jy(this, b)/this.getMass();

        setSpeedX(getSpeedX() + jx);
        setSpeedY(getSpeedY() + jy);

        b.setSpeedX(b.getSpeedX() - jx);
        b.setSpeedY(b.getSpeedY() - jy);
        return true;
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

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
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
