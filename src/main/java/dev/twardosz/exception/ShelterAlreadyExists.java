package dev.twardosz.exception;

public class ShelterAlreadyExists extends Exception {
    public ShelterAlreadyExists(String name, Exception e) {
        super("Shelter (" + name + ") already exists.", e);
    }
}
