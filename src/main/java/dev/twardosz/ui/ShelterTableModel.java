package dev.twardosz.ui;

import dev.twardosz.AnimalShelter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ShelterTableModel extends AbstractTableModel {
    private List<AnimalShelter> shelters;
    private final String[] columnNames = {"Name", "Capacity", "Rating", "Rating Count"};

    public ShelterTableModel(List<AnimalShelter> shelters) {
        this.shelters = shelters;
    }

    public AnimalShelter get(int rowIndex) {
        return shelters.get(rowIndex);
    }

    public void update(List<AnimalShelter> shelters) {
        this.shelters = shelters;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return shelters.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnimalShelter shelter = shelters.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> shelter.getShelterName();
            case 1 -> shelter.getCapacity();
            case 2 -> shelter.getAvgRating();
            case 3 -> shelter.getRatingCount();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
