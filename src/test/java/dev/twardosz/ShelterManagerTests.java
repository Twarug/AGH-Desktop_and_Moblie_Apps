package dev.twardosz;

import dev.twardosz.exception.ShelterAlreadyExists;
import dev.twardosz.exception.ShelterNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterManagerTests {
    private ShelterManager manager;

    @BeforeEach
    public void setUp() throws ShelterAlreadyExists {
        manager = new ShelterManager();
        manager.addShelter("Safe Haven", 50);
        manager.addShelter("Happy Paws", 15);
    }

    @Test
    public void testRemoveNonExistingShelter() {
        assertThrows(ShelterNotFound.class, () -> manager.removeShelter("NonExistent Shelter"), "Removing a non-existing shelter should return false.");
    }

    @Test
    public void testAddShelterAlreadyExists() {
        assertThrows(ShelterAlreadyExists.class, () -> manager.addShelter("Safe Haven", 50), "Adding a shelter that already exists should throw an exception.");
    }

    @Test
    public void testRemoveExistingShelter() {
        assertDoesNotThrow(()-> manager.removeShelter("Safe Haven"), "Removing an existing shelter should return true.");
        assertThrows(ShelterNotFound.class, () -> manager.getShelter("Safe Haven"), "Getting a shelter that does not exist should throw an exception.");
    }

}
