package dev.twardosz;

import dev.twardosz.exception.AnimalAlreadyExists;
import dev.twardosz.exception.ShelterIsFull;
import dev.twardosz.utils.HibernateUtils;
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

    public Animal addAnimal(String name, String species, AnimalCondition condition, int age, double price) throws ShelterIsFull, AnimalAlreadyExists {
        if (animals.size() >= capacity)
            throw new ShelterIsFull(shelterName);

        Animal animal = new Animal(name, species, condition, age, price, this);

        if (animals.contains(animal))
            throw new AnimalAlreadyExists(shelterName, animal.getName());

        animals.add(animal);

        HibernateUtils.getSession().persist(animal);
        HibernateUtils.commit();

        return animal;
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        HibernateUtils.getSession().delete(animal);
        HibernateUtils.commit();
    }

    public Long getId() {
        return id;
    }

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

    public void sortByName() {
        animals.sort(Comparator.comparing(Animal::getName));
    }

    public void sortByPrice() {
        animals.sort(Comparator.comparing(Animal::getPrice));
    }

    public Animal search(String name) {
        return animals.stream().filter(animal -> animal.getName().equals(name)).findFirst().orElse(null);
    }

    public Animal searchPartial(String name) {
        return animals.stream().filter(animal -> animal.getName().contains(name)).findFirst().orElse(null);
    }

    public  void summary() {
        System.out.println("Summary of the shelter " + shelterName);
        System.out.println("Number of animals: " + animals.size());
        for (Animal animal : animals) {
            animal.print();
        }
        System.out.println();
    }

    public Animal max() {
        return animals.stream().max(Comparator.comparing(Animal::getAge)).orElse(null);
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

    public void saveToCsv(String directory) {
        Path dir = Path.of(directory);
        dir.toFile().mkdirs();

        try {
            File file = dir.resolve(shelterName + ".csv").toFile();
            if (!file.exists())
                if (!file.createNewFile())
                    throw new IOException("Cannot create file " + file.getName());

            try (FileWriter fileWriter = new FileWriter(file)) {
                for (Animal animal : animals) {
                    fileWriter.write(animal.getId() + "," + animal.getName() + "," + animal.getSpecies() + "," + animal.getCondition() + "," + animal.getAge() + "," + animal.getPrice() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRating(int rating, String description) {
        Rating ratingEntity = new Rating(this, rating, description, LocalDateTime.now());
        ratings.add(ratingEntity);

        HibernateUtils.getSession().persist(ratingEntity);
        HibernateUtils.commit();
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
