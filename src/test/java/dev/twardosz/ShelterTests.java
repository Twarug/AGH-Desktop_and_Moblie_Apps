package dev.twardosz;

import dev.twardosz.exception.AnimalAlreadyExists;
import dev.twardosz.exception.ShelterIsFull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShelterTests {
    private AnimalShelter shelter;
    private Animal buddy;

    @BeforeEach
    public void setUp() throws ShelterIsFull, AnimalAlreadyExists {
        shelter = new AnimalShelter("Safe Haven", 50);
        buddy = new Animal("Buddy", "Dog", AnimalCondition.Healthy, 3, 150.00);
        shelter.addAnimal(buddy);
    }

    @Test
    public void testAddAnimal() {
        Animal newAnimal = new Animal("Max", "Dog", AnimalCondition.Healthy, 1, 100.00);
        assertDoesNotThrow(() -> shelter.addAnimal(newAnimal), "Adding an animal should not throw an exception.");
        assertTrue(shelter.getAnimals().contains(newAnimal), "Animal should be added to the shelter.");
    }

    @Test
    public void testRemoveAnimal() {
        shelter.removeAnimal(buddy);
        assertFalse(shelter.getAnimals().contains(buddy), "Animal should be removed from the shelter.");
    }

    @Test
    public void testChangeAnimalCondition() {
        shelter.changeCondition(buddy, AnimalCondition.Sick);
        assertEquals(AnimalCondition.Sick, buddy.getCondition(), "Animal's condition should be updated.");
    }
}

