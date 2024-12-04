package dev.twardosz.controllers;

import dev.twardosz.dto.RatingCreateRequest;
import dev.twardosz.repositories.AnimalShelterRepository;
import dev.twardosz.repositories.RatingRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    private final RatingRepository ratingRepository;
    private final AnimalShelterRepository animalShelterRepository;

    public RatingController(RatingRepository ratingRepository, AnimalShelterRepository animalShelterRepository) {
        this.ratingRepository = ratingRepository;
        this.animalShelterRepository = animalShelterRepository;
    }


    @PostMapping
    public void rate(@RequestBody RatingCreateRequest request) {
        ratingRepository.save(request.createRating(animalShelterRepository));
    }

}
