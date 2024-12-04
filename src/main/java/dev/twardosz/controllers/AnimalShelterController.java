package dev.twardosz.controllers;

import dev.twardosz.dto.AnimalShelterCreateRequest;
import dev.twardosz.models.Animal;
import dev.twardosz.models.AnimalShelter;
import dev.twardosz.repositories.AnimalRepository;
import dev.twardosz.repositories.AnimalShelterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animalShelter")
public class AnimalShelterController {
    private final AnimalRepository animalRepository;
    private final AnimalShelterRepository animalShelterRepository;

    public AnimalShelterController(AnimalRepository animalRepository, AnimalShelterRepository animalShelterRepository) {
        this.animalRepository = animalRepository;
        this.animalShelterRepository = animalShelterRepository;
    }

    @GetMapping
    public List<AnimalShelter> getAllAnimalShelters() {
        return animalShelterRepository.findAll();
    }

    @PostMapping
    public AnimalShelter createAnimalShelter(@RequestBody AnimalShelterCreateRequest request) {
        return animalShelterRepository.save(request.createAnimalShelter());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAnimalShelter(@PathVariable("id") Long shelterId) {
        animalShelterRepository.deleteById(shelterId);
    }

    @GetMapping("/{id}/animal")
    public Iterable<Animal> getAnimalFormShelter(@PathVariable("id") Long shelterId) {
        return animalRepository.findAllByShelterId(shelterId);
    }

    @GetMapping("/{id}/fill")
    public float fillShelter(@PathVariable("id") Long shelterId) {
        AnimalShelter animalShelter = animalShelterRepository.getReferenceById(shelterId);
        if (animalShelter.getAnimals() == null) return 0;
        return animalShelter.size() / (float)animalShelter.getCapacity() * 100;
    }

    @GetMapping(value = "/csv", produces = "text/csv")
    public String getSheltersCsv() {
        StringBuilder builder = new StringBuilder();

        List<AnimalShelter> shelters = animalShelterRepository.findAll();
        for (var shelter : shelters) {
            builder.append(shelter.getShelterName());
            builder.append(',');

            builder.append(shelter.size());
            builder.append(',');

            builder.append(shelter.getCapacity());
            builder.append(',');

            builder.append(shelter.getAvgRating());
            builder.append('\n');
        }

        return builder.toString();
    }

}
