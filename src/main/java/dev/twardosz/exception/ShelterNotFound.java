package dev.twardosz.exception;

public class ShelterNotFound extends Exception {
    public ShelterNotFound(String name, Exception e) {
        super("Shelter (" + name + ") not found in.", e);
    }
}
