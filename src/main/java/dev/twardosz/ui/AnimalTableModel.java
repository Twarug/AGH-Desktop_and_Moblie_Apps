package dev.twardosz.ui;

import dev.twardosz.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AnimalTableModel extends AbstractTableModel {
    private List<Animal> animals;
    private final String[] columnNames = {"Name", "Species", "Condition", "Age", "Price"};

    public AnimalTableModel(List<Animal> animals) {
        this.animals = animals;
    }

    public Animal get(int rowIndex) {
        return animals.get(rowIndex);
    }

    public void update(List<Animal> animals) {
        this.animals = animals;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return animals.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = animals.get(rowIndex);
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
