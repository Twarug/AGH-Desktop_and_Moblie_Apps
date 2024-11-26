package dev.twardosz;

import dev.twardosz.utils.HibernateUtils;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Animal implements Comparable<Animal>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private final String species;
    private AnimalCondition condition;
    private int age;
    private final double price;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private AnimalShelter shelter;

    public Animal(String name, String species, AnimalCondition condition, int age, double price, AnimalShelter shelter) {
        this.name = name;
        this.species = species;
        this.condition = condition;
        this.age = age;
        this.price = price;

        this.shelter = shelter;
    }

    public Animal() {
        this("", "", AnimalCondition.Healthy, 0, 0.0, null);
    }

    public void print() {
        System.out.println("Animal " + name + ": Species: " + species + ", Age: " + age + ", Condition: " + condition + ", Price: " + price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public AnimalCondition getCondition() {
        return condition;
    }

    public void setCondition(AnimalCondition condition) {
        this.condition = condition;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void adopt() {
        setCondition(AnimalCondition.Adopted);
        shelter = null;

        HibernateUtils.getSession().persist(this);
        HibernateUtils.commit();
    }

    @Override
    public int compareTo(Animal o) {
        int result = this.name.compareTo(o.name);
        if (result != 0)
            return result;
        result = this.species.compareTo(o.species);
        if (result != 0)
            return result;
        return Integer.compare(this.age, o.age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return name.equals(animal.name) && species.equals(animal.species) && age == animal.age;
    }
}
