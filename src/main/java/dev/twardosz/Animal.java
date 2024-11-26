package dev.twardosz;

public class Animal implements Comparable<Animal> {
    private final String name;
    private final String species;
    private AnimalCondition condition;
    private int age;
    private final double price;

    public Animal(String name, String species, AnimalCondition condition, int age, double price) {
        this.name = name;
        this.species = species;
        this.condition = condition;
        this.age = age;
        this.price = price;
    }

    public void print() {
        System.out.println("Animal " + name + ": Species: " + species + ", Age: " + age + ", Condition: " + condition + ", Price: " + price);
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
