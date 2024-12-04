package dev.twardosz.repositories;

import dev.twardosz.models.AnimalShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalShelterRepository extends JpaRepository<AnimalShelter, Long> {
}
