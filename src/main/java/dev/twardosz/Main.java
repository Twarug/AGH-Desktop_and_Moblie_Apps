package dev.twardosz;

public class Main {
    public static void main(String[] args) {
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

        System.out.println("------------------------------------------");
        // Remove non-existing shelter
        manager.removeShelter("Safe haven");
        // Remove existing shelter
        manager.removeShelter("Safe Haven");
        manager.summary();

        happyPaws.summary();

        // Sort by Name
        happyPaws.sortByName();
        happyPaws.summary();

        // Sort by price
        happyPaws.sortByPrice();
        happyPaws.summary();

        // Change Age
        buddy.print();
        happyPaws.changeAge(buddy, 4);
        buddy.print();

        // Counts of animals in different states
        System.out.println("Healthy animals: " + happyPaws.countByCondition(AnimalCondition.Healthy));
        System.out.println("Sick animals: " + happyPaws.countByCondition(AnimalCondition.Sick));
        System.out.println("Adopted animals: " + happyPaws.countByCondition(AnimalCondition.Adopted));
        System.out.println("Quarantined animals: " + happyPaws.countByCondition(AnimalCondition.Quarantined));

        // Search for an animal
        Animal buddy2 = happyPaws.search("Buddy");
        if (buddy2 != null)
            buddy2.print();
        else
            System.out.println("Animal not found.");

        // Search for an animal with a partial name
        Animal buddy3 = happyPaws.searchPartial("Bud");
        if (buddy3 != null)
            buddy3.print();
        else
            System.out.println("Animal not found.");

        // Get the animal with the highest age
        Animal buddy4 = happyPaws.max();
        if (buddy4 != null)
            buddy4.print();
        else
            System.out.println("Animal not found.");

        // Get an animal from a shelter
        buddy = happyPaws.getAnimal(buddy);
        if (buddy != null)
            buddy.print();
        else
            System.out.println("Animal not found.");

        // Remove an animal
        happyPaws.removeAnimal(whiskers);

        // Change an animal's condition
        happyPaws.changeCondition(whiskers, AnimalCondition.Quarantined);

        // Display summary
        happyPaws.summary();
    }
}
