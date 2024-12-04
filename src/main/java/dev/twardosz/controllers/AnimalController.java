package dev.twardosz.controllers;

import dev.twardosz.dto.AnimalCreateRequest;
import dev.twardosz.models.Animal;
import dev.twardosz.repositories.AnimalRepository;
import dev.twardosz.repositories.AnimalShelterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    private final AnimalRepository animalRepository;
    private final AnimalShelterRepository animalShelterRepository;

    public AnimalController(AnimalRepository animalRepository, AnimalShelterRepository animalShelterRepository) {
        this.animalRepository = animalRepository;
        this.animalShelterRepository = animalShelterRepository;
    }

    @PostMapping
    public Animal createAnimal(@RequestBody AnimalCreateRequest request) {
        return animalRepository.save(request.createAnimal(animalShelterRepository));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAnimal(@PathVariable("id") Long animalId) {
        animalRepository.deleteById(animalId);
    }

    @GetMapping("/{id}")
    public Animal getAnimal(@PathVariable("id") Long animalId) {
        return animalRepository.getReferenceById(animalId);
    }

}
