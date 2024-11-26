package dev.twardosz.exception;

public class ShelterNotFound extends Exception {
    public ShelterNotFound(String name) {
        super("Shelter (" + name + ") not found in.");
    }
}
