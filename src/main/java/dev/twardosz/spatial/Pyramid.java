package dev.twardosz.spatial;

import dev.twardosz.Figure;

public class Pyramid extends Figure {

    public Figure base;
    public double height;

    public Pyramid(Figure base, double height) {
        if (base == null || height <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        this.base = base;
        this.height = height;
    }

    public double calculateVolume() {
        return base.calculateArea() * height / 3;
    }

    @Override
    public double calculateArea() {
        double wallHeight = Math.sqrt(this.height * this.height + 2 * this.base.calculateArea() / this.base.calculatePerimeter());
        return base.calculatePerimeter() * wallHeight / 2 + base.calculateArea();
    }

    @Override
    public double calculatePerimeter() {
        return 0;
    }

    @Override
    public void print() {
        System.out.print("Pyramid with base: vol: " + calculateVolume() + " height: " + height + " and base: ");
        base.print();
    }
}
