package dev.twardosz;

import dev.twardosz.exception.ShelterAlreadyExists;
import dev.twardosz.exception.ShelterNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

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
        assertNull(manager.getShelter("Safe Haven"), "Shelter should be removed.");
    }

    @Test
    public void testSortByName() {
        manager.sortShelters(Comparator.comparing(AnimalShelter::getShelterName));
        assertTrue(manager.getShelters().get(0).getShelterName().compareTo(manager.getShelters().get(1).getShelterName()) < 0, "Shelters should be sorted by name.");
    }

    @Test
    public void testSortByCapacity() {
        manager.sortShelters(Comparator.comparing(AnimalShelter::getCapacity));
        assertTrue(manager.getShelters().get(0).getCapacity() < manager.getShelters().get(1).getCapacity(), "Shelters should be sorted by capacity.");
    }

    @Test
    public void testSortByCondition() {
        manager.sortShelters(Comparator.comparing(AnimalShelter::getCapacity));
        assertTrue(manager.getShelters().get(0).getCapacity() < manager.getShelters().get(1).getCapacity(), "Shelters should be sorted by condition.");
    }

    @Test
    public void testSortByConditionReverse() {
        manager.sortShelters(Comparator.comparing(AnimalShelter::getCapacity).reversed());
        assertTrue(manager.getShelters().get(0).getCapacity() > manager.getShelters().get(1).getCapacity(), "Shelters should be sorted by condition.");
    }

    @Test
    public void testSortByNameReverse() {
        manager.sortShelters(Comparator.comparing(AnimalShelter::getShelterName).reversed());
        assertTrue(manager.getShelters().get(0).getShelterName().compareTo(manager.getShelters().get(1).getShelterName()) > 0, "Shelters should be sorted by name.");
    }

    @Test
    public void testSortByCapacityReverse() {
        manager.sortShelters(Comparator.comparing(AnimalShelter::getCapacity).reversed());
        assertTrue(manager.getShelters().get(0).getCapacity() > manager.getShelters().get(1).getCapacity(), "Shelters should be sorted by capacity.");
    }
}
