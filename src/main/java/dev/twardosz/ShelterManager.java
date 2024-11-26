package dev.twardosz;

import java.util.HashMap;
import java.util.Map;

public class ShelterManager {
    private final Map<String, AnimalShelter> shelters;

    public ShelterManager() {
        shelters = new HashMap<>();
    }

    public AnimalShelter addShelter(String shelterName, int capacity) {
        if (shelters.containsKey(shelterName)) {
            System.out.println("Shelter " + shelterName + " already exists.");
            return shelters.get(shelterName);
        }

        AnimalShelter shelter = new AnimalShelter(shelterName, capacity);
        shelters.put(shelterName, shelter);
        return shelter;
    }

    public void removeShelter(String shelterName) {
        if (!shelters.containsKey(shelterName))
            System.out.println("Shelter " + shelterName + " does not exist.");
        else
            shelters.remove(shelterName);
    }

    public AnimalShelter findEmpty() {
        for (AnimalShelter shelter : shelters.values()) {
            if (shelter.isEmpty())
                return shelter;
        }
        return null;
    }

    public void summary() {
        for (AnimalShelter shelter : shelters.values()) {
            System.out.println(shelter.getShelterName() + ": " + shelter.size() + "/" + shelter.getCapacity() + " (" + (shelter.size() * 100.0 / shelter.getCapacity()) + "%)");
        }
    }
}
