package dev.twardosz;

import dev.twardosz.exception.ShelterAlreadyExists;
import dev.twardosz.exception.ShelterNotFound;
import dev.twardosz.utils.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShelterManager implements Serializable {

    public ShelterManager() {}

    public AnimalShelter addShelter(String shelterName, int capacity) throws ShelterAlreadyExists {
        try {
            AnimalShelter shelter = new AnimalShelter(shelterName, capacity);
            HibernateUtils.getSession().persist(shelter);
            HibernateUtils.commit();

            return shelter;
        } catch (Exception e) {
            throw new ShelterAlreadyExists(shelterName, e);
        }
    }

    public void removeShelter(String shelterName) throws ShelterNotFound {
        try {
            AnimalShelter shelter = getShelter(shelterName);
            HibernateUtils.getSession().remove(shelter);
        } catch (Exception e) {
            throw new ShelterNotFound(shelterName, e);
        }
    }

    public List<AnimalShelter> findEmpty() {
        Query<AnimalShelter> query = HibernateUtils.getSession().createQuery("FROM AnimalShelter WHERE animals.size = :size", AnimalShelter.class);
        return query.setParameter("size", 0).getResultList();
    }

    public void summary() {
        for (AnimalShelter shelter : getShelters()) {
            System.out.println(shelter.getShelterName() + ": " + shelter.size() + "/" + shelter.getCapacity() + " (" + (shelter.size() * 100.0 / shelter.getCapacity()) + "%)");
        }
    }

    public long size() {
        CriteriaBuilder cb = HibernateUtils.getSession().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<AnimalShelter> root = cq.from(AnimalShelter.class);
        cq.select(cb.count(root));
        Query<Long> query = HibernateUtils.getSession().createQuery(cq);
        return query.getSingleResult();
    }

    public void removeShelter(Long id) throws ShelterNotFound {
        AnimalShelter shelter = getShelter(id);
        HibernateUtils.getSession().remove(shelter);
    }

    public AnimalShelter getShelter(String shelterName) throws ShelterNotFound {
        try {
            Query<AnimalShelter> query = HibernateUtils.getSession().createQuery("FROM AnimalShelter WHERE shelterName = :name", AnimalShelter.class);
            return query.setParameter("name", shelterName).getSingleResult();
        } catch (Exception e) {
            throw new ShelterNotFound(shelterName, e);
        }
    }

    public AnimalShelter getShelter(Long id) throws ShelterNotFound {
        try {
            Query<AnimalShelter> query = HibernateUtils.getSession().createQuery("FROM AnimalShelter WHERE id = :id", AnimalShelter.class);
            return query.setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new ShelterNotFound(String.valueOf(id), e);
        }
    }

    public List<AnimalShelter> getShelters() {
        Query<AnimalShelter> query = HibernateUtils.getSession().createQuery("FROM AnimalShelter", AnimalShelter.class);
        return query.getResultList();
    }

    public void saveToCsv(String directory) {
        try {
            Path dir = Paths.get(directory);
            dir.toFile().mkdirs();

            File file = dir.resolve("_shelters.csv").toFile();
            if (!file.exists())
                if (!file.createNewFile())
                    throw new IOException("Cannot create file " + file.getName());

            try (FileWriter fileWriter = new FileWriter(file)) {
                for (AnimalShelter shelter : getShelters()) {
                    fileWriter.write(shelter.getId() + "," + shelter.getShelterName() + "," + shelter.getCapacity() + "\n");

                    shelter.saveToCsv(directory);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists())
                if (!file.createNewFile())
                    throw new IOException("Cannot create file " + fileName);

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ShelterManager loadFromFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists())
                throw new IOException("File " + fileName + " does not exist");

            try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                return (ShelterManager) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
