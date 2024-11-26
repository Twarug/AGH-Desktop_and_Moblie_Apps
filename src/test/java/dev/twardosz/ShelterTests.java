package dev.twardosz;

import dev.twardosz.exception.AnimalAlreadyExists;
import dev.twardosz.exception.ShelterIsFull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterTests {
    private AnimalShelter shelter;
    private Animal buddy;

    @BeforeEach
    public void setUp() throws ShelterIsFull, AnimalAlreadyExists {
        shelter = new AnimalShelter("Safe Haven", 50);
        buddy = shelter.addAnimal("Buddy", "Dog", AnimalCondition.Healthy, 3, 150.00);
    }

    @Test
    public void testAddAnimal() {
        AtomicReference<Animal> newAnimal = new AtomicReference<>();
        assertDoesNotThrow(() -> newAnimal.set(shelter.addAnimal("Max", "Dog", AnimalCondition.Healthy, 1, 100.00)), "Adding an animal should not throw an exception.");
        assertTrue(shelter.getAnimals().contains(newAnimal.get()), "Animal should be added to the shelter.");
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

