package dev.twardosz.dto;

import dev.twardosz.models.Rating;
import dev.twardosz.repositories.AnimalShelterRepository;

import java.time.LocalDateTime;

public record RatingCreateRequest(
    Long shelterId,
    int rating,
    String comment
) {
    public Rating createRating(AnimalShelterRepository animalShelterRepository) {
        return new Rating(
            animalShelterRepository.getReferenceById(shelterId),
            rating,
            comment,
            LocalDateTime.now()
        );
    }
}
