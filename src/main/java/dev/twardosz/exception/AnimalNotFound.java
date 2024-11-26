package dev.twardosz.exception;

public class AnimalNotFound extends Exception {
    public AnimalNotFound(String name, String shelterName) {
        super("Animal (" + name + ") not found in shelter (" + shelterName + ").");
    }
}
