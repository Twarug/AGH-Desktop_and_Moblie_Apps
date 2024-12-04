package dev.twardosz.models;

import dev.twardosz.exception.AnimalAlreadyExists;
import dev.twardosz.exception.ShelterIsFull;
import jakarta.persistence.*;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class AnimalShelter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shelterName;

    private final int capacity;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animals = new ArrayList<>();

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public AnimalShelter(String shelterName, int capacity) {
        this.shelterName = shelterName;
        this.capacity = capacity;
    }

    public AnimalShelter() {
        this("", 0);
    }

    @Transient
    public Animal addAnimal(String name, String species, AnimalCondition condition, int age, double price) throws ShelterIsFull, AnimalAlreadyExists {
        if (animals.size() >= capacity)
            throw new ShelterIsFull(shelterName);

        Animal animal = new Animal(name, species, condition, age, price, this);

        if (animals.contains(animal))
            throw new AnimalAlreadyExists(shelterName, animal.getName());

        animals.add(animal);

        return animal;
    }

    @Transient
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    @Transient
    public Long getId() {
        return id;
    }

    @Transient
    public Animal getAnimal(int index) {
        return animals.get(index);
    }

    public void changeCondition(Animal animal, AnimalCondition condition) {
        animal.setCondition(condition);
    }

    public void changeAge(Animal animal, int age) {
        animal.setAge(age);
    }

    public long countByCondition(AnimalCondition condition) {
        return animals.stream().filter(animal -> animal.getCondition() == condition).count();
    }

    public boolean isEmpty() {
        return animals.isEmpty();
    }

    public String getShelterName() {
        return shelterName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int size() {
        return animals.size();
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public double getAvgRating() {
        if (ratings.isEmpty())
            return 0;

        return ratings.stream().map(Rating::getRating).reduce(0, Integer::sum) / (double)ratings.size();
    }

    public int getRatingCount() {
        return ratings.size();
    }
}
