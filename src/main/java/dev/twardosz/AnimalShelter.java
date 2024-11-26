package dev.twardosz;

import dev.twardosz.exception.AnimalAlreadyExists;
import dev.twardosz.exception.AnimalNotFound;
import dev.twardosz.exception.ShelterIsFull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnimalShelter {
    private final String shelterName;
    private final List<Animal> animals;
    private final int capacity;

    public AnimalShelter(String shelterName, int capacity) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        animals = new ArrayList<>();
    }

    public Animal addAnimal(Animal animal) throws ShelterIsFull, AnimalAlreadyExists {
        if (animals.size() >= capacity)
            throw new ShelterIsFull(shelterName);

        if (animals.contains(animal))
            throw new AnimalAlreadyExists(shelterName, animal.getName());

        animals.add(animal);
        return animal;
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public Animal getAnimal(int index) {
        return animals.get(index);
    }

    public Animal getAnimal(Animal animal) throws AnimalNotFound {
        if (!animals.remove(animal))
            throw new AnimalNotFound(animal.getName(), shelterName);

        animal.setCondition(AnimalCondition.Adopted);
        return animal;
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
}