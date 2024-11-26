package dev.twardosz;

import dev.twardosz.exception.ShelterAlreadyExists;
import dev.twardosz.exception.ShelterNotFound;

import java.util.*;

public class ShelterManager {
    private final List<String> shelterNames;
    private final Map<String, AnimalShelter> shelters;

    public ShelterManager() {
        shelters = new HashMap<>();
        shelterNames = new ArrayList<>();
    }

    public AnimalShelter addShelter(String shelterName, int capacity) throws ShelterAlreadyExists {
        if (shelters.containsKey(shelterName))
            throw new ShelterAlreadyExists(shelterName);

        AnimalShelter shelter = new AnimalShelter(shelterName, capacity);
        shelterNames.add(shelterName);
        shelters.put(shelterName, shelter);
        return shelter;
    }

    public void removeShelter(String shelterName) throws ShelterNotFound {
        if (!shelters.containsKey(shelterName))
            throw new ShelterNotFound(shelterName);
        else {
            shelterNames.remove(shelterName);
            shelters.remove(shelterName);
        }
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

    public int size() {
        return shelters.size();
    }

    public void removeShelter(int rowIndex) {
        try {
            removeShelter(shelterNames.get(rowIndex));
        } catch (ShelterNotFound e) {
            System.out.println("Shelter not found.");
        }
    }

    public AnimalShelter getShelter(String shelterName) {
        return shelters.get(shelterName);
    }

    public AnimalShelter getShelter(int rowIndex) {
        return shelters.get(shelterNames.get(rowIndex));
    }

    public void sortShelters(Comparator<AnimalShelter> comparator) {
        shelterNames.sort((s1, s2) -> comparator.compare(getShelter(s1), getShelter(s2)));
    }

    public List<AnimalShelter> getShelters() {
        return shelterNames.stream().map(shelters::get).toList();
    }
}
