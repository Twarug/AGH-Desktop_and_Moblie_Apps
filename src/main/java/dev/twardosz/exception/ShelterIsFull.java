package dev.twardosz.exception;

public class ShelterIsFull extends Exception {
    public ShelterIsFull(String name) {
        super("Shelter (" + name + ") is full.");
    }
}
