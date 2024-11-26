package dev.twardosz.flat;

import dev.twardosz.Figure;
import dev.twardosz.Printable;

public class Rectangle extends Figure implements Printable {

    private double a;
    private double b;

    public Rectangle(double a, double b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("All sides must be positive");
        }

        this.a = a;
        this.b = b;
    }

    @Override
    public double calculateArea() {
        return a * b;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (a + b);
    }

    @Override
    public void print() {
        System.out.println("Rectangle with sides: " + a + ", " + b);
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
}
