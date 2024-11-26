package dev.twardosz.ui;

import dev.twardosz.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AnimalTableModel extends AbstractTableModel {
    private final List<Animal> shelter;
    private final String[] columnNames = {"Name", "Species", "Condition", "Age", "Price"};

    public AnimalTableModel(List<Animal> shelter) {
        this.shelter = shelter;
    }

    public AnimalTableModel(AnimalShelter shelter) {
        this.shelter = shelter.getAnimals();
    }

    @Override
    public int getRowCount() {
        return shelter.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = shelter.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> animal.getName();
            case 1 -> animal.getSpecies();
            case 2 -> animal.getCondition();
            case 3 -> animal.getAge();
            case 4 -> animal.getPrice();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
