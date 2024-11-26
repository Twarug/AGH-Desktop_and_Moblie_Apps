package dev.twardosz;

import dev.twardosz.exception.AnimalAlreadyExists;
import dev.twardosz.exception.ShelterIsFull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTests {
    private AnimalShelter shelter;
    private Animal buddy;

    @BeforeEach
    public void setUp() throws ShelterIsFull, AnimalAlreadyExists {
        shelter = new AnimalShelter("Safe Haven", 50);

        buddy = shelter.addAnimal("Buddy", "Dog", AnimalCondition.Healthy, 3, 150.00);
        shelter.addAnimal("Whiskers", "Cat", AnimalCondition.Sick, 2, 80.00);
    }

    @Test
    public void testChangeAge() {
        int originalAge = buddy.getAge();
        shelter.changeAge(buddy, 4);
        assertNotEquals(originalAge, buddy.getAge(), "Animal age should be updated.");
        assertEquals(4, buddy.getAge(), "Animal age should be updated.");
    }

    @Test
    public void testCountByCondition() {
        long healthyCount = shelter.countByCondition(AnimalCondition.Healthy);
        long sickCount = shelter.countByCondition(AnimalCondition.Sick);

        assertEquals(1, healthyCount, "There should be one healthy animal.");
        assertEquals(1, sickCount, "There should be one sick animal.");
    }
}

