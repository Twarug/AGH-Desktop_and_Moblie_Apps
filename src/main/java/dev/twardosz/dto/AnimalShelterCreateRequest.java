package dev.twardosz.dto;

import dev.twardosz.models.AnimalShelter;

public record AnimalShelterCreateRequest(
    String name,
    int capacity
) {
    public AnimalShelter createAnimalShelter() {
        return new AnimalShelter(name, capacity);
    }
}
