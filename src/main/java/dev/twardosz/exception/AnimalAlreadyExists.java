package dev.twardosz.exception;

public class AnimalAlreadyExists extends Exception {
    public AnimalAlreadyExists(String shelterName, String name) {
        super("Animal (" + name + ") already exists in shelter (" + shelterName + ").");
    }
}
