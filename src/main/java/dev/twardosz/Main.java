package dev.twardosz;

import dev.twardosz.ui.ShelterAppUI;
import dev.twardosz.utils.HibernateUtils;
import org.hibernate.query.Query;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ShelterManager manager = new ShelterManager();

        Query<Long> query = HibernateUtils.getSession().createQuery("SELECT COUNT(s) FROM AnimalShelter s", Long.class);
        Long shelterCount = query.uniqueResult();
        if (shelterCount <= 0) {
            // Create shelters
            AnimalShelter happyPaws = manager.addShelter("Happy Paws", 15);
            AnimalShelter safeHaven = manager.addShelter("Safe Haven", 10);

            // Add animals to a shelter
            happyPaws.addAnimal("Buddy", "Dog", AnimalCondition.Healthy, 3, 150.00);
            happyPaws.addAnimal("Whiskers", "Cat", AnimalCondition.Sick, 2, 80.00);
            happyPaws.addAnimal("Coco", "Guinea Pig", AnimalCondition.Quarantined, 1, 199.47);
            happyPaws.addAnimal("Milo", "Fish", AnimalCondition.Healthy, 6, 122.24);
            happyPaws.addAnimal("Ruby", "Snake", AnimalCondition.Sick, 5, 393.32);
            happyPaws.addAnimal("Rocky", "Parrot", AnimalCondition.Healthy, 1, 246.08);
            happyPaws.addAnimal("Rocky", "Rabbit", AnimalCondition.Quarantined, 14, 151.00);
            happyPaws.addAnimal("Oscar", "Cat", AnimalCondition.Quarantined, 1, 497.29);
            happyPaws.addAnimal("Ruby", "Turtle", AnimalCondition.Quarantined, 3, 249.08);
            happyPaws.addAnimal("Oscar", "Snake", AnimalCondition.Sick, 2, 114.80);
            happyPaws.addAnimal("Ruby", "Hamster", AnimalCondition.Healthy, 15, 97.11);
            happyPaws.addAnimal("Daisy", "Snake", AnimalCondition.Healthy, 4, 94.46);

            safeHaven.addAnimal("Luna", "Dog", AnimalCondition.Healthy, 3, 299.99);
            safeHaven.addAnimal("Charlie", "Goldfish", AnimalCondition.Quarantined, 2, 49.75);
            safeHaven.addAnimal("Bella", "Iguana", AnimalCondition.Sick, 5, 179.85);
            safeHaven.addAnimal("Max", "Cockatoo", AnimalCondition.Healthy, 1, 325.49);
            safeHaven.addAnimal("Oliver", "Rabbit", AnimalCondition.Quarantined, 7, 215.30);
            safeHaven.addAnimal("Simba", "Cat", AnimalCondition.Sick, 4, 410.70);
            safeHaven.addAnimal("Chloe", "Tortoise", AnimalCondition.Quarantined, 10, 199.99);
            safeHaven.addAnimal("Milo", "Ferret", AnimalCondition.Healthy, 2, 150.20);
            safeHaven.addAnimal("Buddy", "Guinea Pig", AnimalCondition.Healthy, 6, 85.99);
            safeHaven.addAnimal("Lily", "Snake", AnimalCondition.Quarantined, 3, 120.50);
        }

//        manager.saveToFile("run/shelters.bin");
        manager.saveToCsv("run/test");

        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> new ShelterAppUI(manager));
        });
    }
}
