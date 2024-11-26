package dev.twardosz;

import dev.twardosz.flat.*;
import dev.twardosz.spatial.Pyramid;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Figure> figures = new ArrayList<>();

        boolean running = true;
        do {
            System.out.println("-------------------------------");
            System.out.println("  1) Add figure");
            System.out.println("  2) Print all figures");
            System.out.println("  _) Exit");
            System.out.println("-------------------------------");

            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextInt()) {
                case 1: {
                    var figure = selectFigure(scanner, true);
                    if (figure == null) break;
                    figures.add(figure);

                    System.out.print("Figure added: ");
                    figure.print();
                    break;
                }
                case 2: {
                    for (Figure figure : figures) {
                        figure.print();
                        System.out.println("  Area: " + figure.calculateArea());
                        System.out.println("  Perimeter: " + figure.calculatePerimeter());
                        System.out.println();
                    }
                    break;
                }
                default: running = false; break;
            }
        } while (running);
    }

    private static Figure selectFigure(Scanner scanner, boolean canCircle) {
        System.out.println("Enter figure type:");
        System.out.println("  1) Rectangle");
        System.out.println("  2) Triangle");
        System.out.println("  3) Pyramid");
//        if (canCircle)
            System.out.println("  4) Circle");
        System.out.println("  _) Exit");
        System.out.println("-------------------------------");

        Figure figure;

        int type = scanner.nextInt();
        switch (type) {
            case 1:
                System.out.println("Enter a:");
                double a = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Enter b:");
                double b = scanner.nextDouble();
                scanner.nextLine();

                figure = new Rectangle(a, b);
                break;
            case 2:
                System.out.println("Enter a:");
                double a1 = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Enter b:");
                double b1 = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Enter c:");
                double c1 = scanner.nextDouble();
                scanner.nextLine();

                figure = new Triangle(a1, b1, c1);
                break;
            case 3:
                System.out.println("Enter base:");
                Figure base = selectFigure(scanner, false);

                if (base == null) {
                    System.out.println("Nie podano poprawnej podstawy");
                    return null;
                }

                scanner.nextLine();
                System.out.println("Enter height:");
                double height = scanner.nextDouble();
                scanner.nextLine();

                figure = new Pyramid(base, height);
                break;

            case 4:
//                if (!canCircle)
//                    return null;

                System.out.println("Enter radius:");
                double radius = scanner.nextDouble();
                scanner.nextLine();
                scanner.nextLine();

                figure = new Circle(radius);
                break;

            default:
                clearScreen();
                return null;
        }

        return figure;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
