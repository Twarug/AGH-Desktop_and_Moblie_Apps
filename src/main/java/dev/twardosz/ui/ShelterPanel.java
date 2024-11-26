package dev.twardosz.ui;

import dev.twardosz.Animal;
import dev.twardosz.AnimalCondition;
import dev.twardosz.AnimalShelter;
import dev.twardosz.ShelterManager;
import dev.twardosz.exception.ShelterAlreadyExists;
import dev.twardosz.exception.ShelterNotFound;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ShelterPanel extends JPanel {
    private final ShelterManager manager;
    private final JFrame frame;

    private final JTable shelterTable;
    private final JTable animalTable;

    private ShelterTableModel shelterTableModel;
    private AnimalTableModel animalTableModel;

    private final JTextField filterTextField;
    private final JComboBox<String> stateComboBox;

    public ShelterPanel(ShelterManager manager, JFrame frame, boolean admin) {
        this.frame = frame;
        this.manager = manager;

        this.shelterTableModel = new ShelterTableModel(manager.getShelters());
        this.animalTableModel = new AnimalTableModel(new ArrayList<>());

        setLayout(new BorderLayout());

        // Panel górny z polem filtrowania
        {
            JPanel northPanel = new JPanel();
            add(northPanel, BorderLayout.NORTH);

            JPanel filterPanel = new JPanel();
            filterTextField = new JTextField(15);
            filterTextField.addActionListener(e -> filterList());
            filterPanel.add(new JLabel("Filter:"));
            filterPanel.add(filterTextField);
            northPanel.add(filterPanel, BorderLayout.WEST);

            String[] states = Stream.concat(Stream.of("All"), Arrays.stream(AnimalCondition.values()).map(Enum::toString)).toArray(String[]::new);
            stateComboBox = new JComboBox<>(states);
            stateComboBox.addActionListener(e -> filterByState());
            northPanel.add(stateComboBox, BorderLayout.EAST);
        }

        // Shelter List Table
        shelterTable = new JTable(shelterTableModel);
        add(new JScrollPane(shelterTable), BorderLayout.WEST);

        // Animal List Table
        animalTable = new JTable(animalTableModel);
        add(new JScrollPane(animalTable), BorderLayout.CENTER);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        if (admin) {
            JButton addShelterBtn = new JButton("Add Shelter");
            JButton addAnimalBtn = new JButton("Add Animal");
            JButton removeShelterBtn = new JButton("Remove Shelter");
            JButton removeAnimalBtn = new JButton("Remove Animal");

            addShelterBtn.addActionListener(e -> addShelter());
            addAnimalBtn.addActionListener(e -> addAnimal());
            removeShelterBtn.addActionListener(e -> removeShelter());
            removeAnimalBtn.addActionListener(e -> removeAnimal());

            buttonPanel.add(addShelterBtn);
            buttonPanel.add(addAnimalBtn);
            buttonPanel.add(removeShelterBtn);
            buttonPanel.add(removeAnimalBtn);
        } else {
            JButton adoptBtn = new JButton("Adopt");

            adoptBtn.addActionListener(e -> adoptAnimal());

            buttonPanel.add(adoptBtn);
        }

        JButton sortSheltersBtn = new JButton("Sort Shelters");

        sortSheltersBtn.addActionListener(e -> sortShelters());

        buttonPanel.add(sortSheltersBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Handle shelter selection to update animal list
        shelterTable.getSelectionModel().addListSelectionListener(e -> updateAnimalList());
    }

    private void addShelter() {
        String name = JOptionPane.showInputDialog("Enter Shelter Name:");
        int capacity = Integer.parseInt(JOptionPane.showInputDialog("Enter Capacity:"));
        try {
            manager.addShelter(name, capacity);
        } catch (ShelterAlreadyExists e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        shelterTableModel.update(manager.getShelters());
    }

    private void addAnimal() {
        if (shelterTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Select a shelter first.");
            return;
        }

        String name = JOptionPane.showInputDialog("Enter Animal Name:");
        String species = JOptionPane.showInputDialog("Enter Species:");
        AnimalCondition condition;
        while (true) {
            String input = JOptionPane.showInputDialog("Enter Condition:");
            try {
                condition = AnimalCondition.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, "Invalid condition.");
            }
        }
        int age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age:"));
        double price = Double.parseDouble(JOptionPane.showInputDialog("Enter Price:"));

        try {
            AnimalShelter selectedShelter = manager.getShelter(shelterTableModel.get(shelterTable.getSelectedRow()).getId());
            selectedShelter.addAnimal(name, species, condition, age, price);

            animalTableModel.update(selectedShelter.getAnimals());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeShelter() {
        if (shelterTable.getSelectedRow() != -1) {
            try {
                manager.removeShelter(shelterTableModel.get(shelterTable.getSelectedRow()).getId());
                shelterTableModel.update(manager.getShelters());
            } catch (ShelterNotFound e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeAnimal() {
        if (animalTable.getSelectedRow() != -1) {
            try {
                AnimalShelter selectedShelter = manager.getShelter(shelterTableModel.get(shelterTable.getSelectedRow()).getId());
                Animal animal = selectedShelter.getAnimal(animalTable.getSelectedRow());
                selectedShelter.removeAnimal(animal);
                animalTableModel.update(selectedShelter.getAnimals());
            } catch (ShelterNotFound e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sortShelters() {
        var shalters = manager.getShelters();
        shalters.sort((shelter1, shelter2) -> shelter2.getShelterName().compareTo(shelter1.getShelterName()));
        shelterTableModel.update(shalters);
    }

    private void updateAnimalList() {
        if (shelterTable.getSelectedRow() != -1) {
            try {
                AnimalShelter selectedShelter = manager.getShelter(shelterTableModel.get(shelterTable.getSelectedRow()).getId());
                animalTableModel.update(selectedShelter.getAnimals());
            } catch (ShelterNotFound e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filterList() {
        String filterText = filterTextField.getText().toLowerCase();

        Long selectedShelterId;

        if (shelterTable.getSelectedRow() != -1)
            selectedShelterId = shelterTableModel.get(shelterTable.getSelectedRow()).getId();
        else {
            selectedShelterId = -1L;
        }

        java.util.List<AnimalShelter> filteredShelters = new ArrayList<>();
        for (AnimalShelter shelter : manager.getShelters()) {
            if (shelter.getShelterName().toLowerCase().contains(filterText)) {
                filteredShelters.add(shelter);
            }
        }


        shelterTableModel.update(filteredShelters);

        // Filtrowanie zwierząt
        if (selectedShelterId == -1L) return;

        AnimalShelter selectedShelter = filteredShelters.stream().filter(shelter -> selectedShelterId.equals(shelter.getId())).findFirst().orElse(null);
        if (selectedShelter == null) return;
        int index = filteredShelters.indexOf(selectedShelter);

        shelterTable.setRowSelectionInterval(index, index);

        java.util.List<Animal> filteredAnimals = new ArrayList<>();
        for (Animal animal : selectedShelter.getAnimals()) {
            if (animal.getName().toLowerCase().contains(filterText)) {
                filteredAnimals.add(animal);
            }
        }
        animalTableModel.update(filteredAnimals);
    }

    private void filterByState() {
        String selectedState = (String) stateComboBox.getSelectedItem();
        if (selectedState == null)
            return;

        if (shelterTable.getSelectedRow() != -1) {
            try {
                AnimalShelter selectedShelter = manager.getShelter(shelterTableModel.get(shelterTable.getSelectedRow()).getId());
                List<Animal> filteredAnimals = new ArrayList<>();
                if (selectedState.equalsIgnoreCase("All"))
                    filteredAnimals = selectedShelter.getAnimals();
                else
                    for (Animal animal : selectedShelter.getAnimals())
                        if (animal.getCondition().toString().equalsIgnoreCase(selectedState))
                            filteredAnimals.add(animal);

                animalTableModel.update(filteredAnimals);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void adoptAnimal() {
        if (shelterTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Select a shelter first.");
            return;
        }

        if (animalTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Select a animal first.");
            return;
        }


        try {
            AnimalShelter shelter = manager.getShelter(shelterTableModel.get(shelterTable.getSelectedRow()).getId());
            shelter.getAnimal(animalTable.getSelectedRow()).adopt();

            animalTableModel.update(shelter.getAnimals());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
