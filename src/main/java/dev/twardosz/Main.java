package dev.twardosz;

import dev.twardosz.ui.ShelterAppUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ShelterManager manager = new ShelterManager();

        // Create shelters
        AnimalShelter happyPaws = manager.addShelter("Happy Paws", 15);
        manager.addShelter("Safe Haven", 10);

        // Add animals to a shelter
        Animal buddy = happyPaws.addAnimal(new Animal("Buddy", "Dog", AnimalCondition.Healthy, 3, 150.00));
        Animal whiskers = happyPaws.addAnimal(new Animal("Whiskers", "Cat", AnimalCondition.Sick, 2, 80.00));

        // Add more animals to shelter
        happyPaws.addAnimal(new Animal("Coco", "Guinea Pig", AnimalCondition.Quarantined, 1, 199.47));
        happyPaws.addAnimal(new Animal("Milo", "Fish", AnimalCondition.Healthy, 6, 122.24));
        happyPaws.addAnimal(new Animal("Ruby", "Snake", AnimalCondition.Sick, 5, 393.32));
        happyPaws.addAnimal(new Animal("Rocky", "Parrot", AnimalCondition.Healthy, 1, 246.08));
        happyPaws.addAnimal(new Animal("Rocky", "Rabbit", AnimalCondition.Quarantined, 14, 151.00));
        happyPaws.addAnimal(new Animal("Oscar", "Cat", AnimalCondition.Quarantined, 1, 497.29));
        happyPaws.addAnimal(new Animal("Ruby", "Turtle", AnimalCondition.Quarantined, 3, 249.08));
        happyPaws.addAnimal(new Animal("Oscar", "Snake", AnimalCondition.Sick, 2, 114.80));
        happyPaws.addAnimal(new Animal("Ruby", "Hamster", AnimalCondition.Healthy, 15, 97.11));
        happyPaws.addAnimal(new Animal("Daisy", "Snake", AnimalCondition.Healthy, 4, 94.46));

        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> new ShelterAppUI(manager));
        });
    }
}
