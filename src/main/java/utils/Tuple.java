package utils;

public class Tuple {
    double a;
    double b;

    public Tuple(double a, double b){
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double dot(Tuple t){
        return a * t.getA() + b * t.getB();
    }

    public Tuple multiply(double d){
        return new Tuple(a * d, b * d);
    }

    public Tuple add(Tuple t){
        return new Tuple(a + t.getA(), b + t.getB());
    }

    public Tuple subtract(Tuple t){
        return new Tuple(a - t.getA(), b - t.getB());
    }
    public Tuple subtract(double d){
        return new Tuple(a - d, b - d);
    }

    public Tuple divide(double d){
        return new Tuple(a / d, b / d);
    }

    public double norm() {
        return Math.sqrt(a * a + b * b);
    }

    public Tuple versor(Tuple t) {
        return this.subtract(t).divide(this.subtract(t).norm());
    }

}
