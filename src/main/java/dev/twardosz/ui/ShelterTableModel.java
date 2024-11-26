package dev.twardosz.ui;

import dev.twardosz.AnimalShelter;
import dev.twardosz.ShelterManager;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ShelterTableModel extends AbstractTableModel {
    private final List<AnimalShelter> manager;
    private final String[] columnNames = {"Name", "Capacity"};

    public ShelterTableModel(ShelterManager manager) {
        this.manager = manager.getShelters();
    }
    public ShelterTableModel(List<AnimalShelter> manager) {
        this.manager = manager;
    }

    @Override
    public int getRowCount() {
        return manager.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnimalShelter shelter = manager.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> shelter.getShelterName();
            case 1 -> shelter.getCapacity();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
