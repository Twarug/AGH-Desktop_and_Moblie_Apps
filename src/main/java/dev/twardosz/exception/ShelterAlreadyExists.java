package dev.twardosz.exception;

public class ShelterAlreadyExists extends Exception {
    public ShelterAlreadyExists(String name) {
        super("Shelter (" + name + ") already exists.");
    }
}
