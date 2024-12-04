package dev.twardosz.dto;

import dev.twardosz.models.Animal;
import dev.twardosz.models.AnimalCondition;
import dev.twardosz.repositories.AnimalShelterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Optional;

public record AnimalCreateRequest(
    String name,
    String species,
    String condition,
    int age,
    double price,
    long shelterId
) {
    public Animal createAnimal(AnimalShelterRepository animalShelterRepository) {
        Optional<AnimalCondition> condition =
                Arrays.stream(AnimalCondition.values()).filter(animalCondition -> animalCondition.name().equalsIgnoreCase(condition())).findFirst();

        if (condition.isEmpty())
            throw HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request", null, null, null);

        return new Animal(
            name,
                species,
                condition.get(),
                age,
                price,
                animalShelterRepository.getReferenceById(shelterId)
        );
    }
}
