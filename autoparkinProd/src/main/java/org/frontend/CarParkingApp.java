package org.frontend;

import static org.main.Constants.TOTALSPOTS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import javax.swing.*;

import javax.swing.event.*;
import javax.swing.table.*;

import org.backend.Car;
import org.backend.ParkingSystem;

import javafx.scene.input.KeyEvent;

public class CarParkingApp {
    private JFrame mainFrame;
    private JPanel infoPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel lastNameLabel, phoneNumberLabel, carBrandLabel, carModelLabel, carNumberLabel, warn1, warn2, warn3,
            warn4, warn5, searchLabel, takeOutLabel;
    private JTextField lastNameField, phoneNumberField, carBrandField, carModelField, carNumberField, searchByField;
    private JComboBox<String> carDropBox, takeCarBox1, takeCarBox2;
    private JButton submitCarButton, carOutButton;

    private final String[] carDropString = { "", "Last name", "Car Brand", "Car Model", "Car Number" };
    private final String[] takeCarString = { "", "Spot ID", "Car Number" };
    private final String[] carBrandsStrings = { "Acura", "Alfa Romeo", "Aston Martin", "Audi", "BMW", "Bentley",
            "Bugatti", "Buick", "Cadillac", "Chevrolet", "Chrysler", "Ferrari", "Fiat", "Ford", "GMC", "Genesis",
            "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "Lamborghini", "Land Rover", "Lexus", "Maserati",
            "Mazda", "Mercedes-Benz", "Mini", "Mitsubishi", "Nissan", "Porsche", "Ram", "Rolls-Royce", "Smart",
            "Subaru", "Tesla", "Toyota", "Volkswagen", "Volvo" };

    // Constructor to initialize the app
    public CarParkingApp(ParkingSystem parkingSystem) {
        mainFrame = new JFrame("Car Parking KI-22-1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 410);

        infoPanel = setPanel();

        // Table
        String[] columnNames = { "Spot ID", "Last Name", "Car Brand", "Car Model", "Car Number", "Parking Date" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(600, 237);
        scrollPane.setLocation(380, 14);
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        // Add sorter to table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Disable sort 2-5 columns
        for (int i = 1; i <= 4; i++) {
            sorter.setSortable(i, false);
        }

        int[] columnWidths = { 60, 88, 97, 104, 118, 118 }; // Set desired widths for each column

        // Get the column model from the table
        TableColumnModel columnModel = table.getColumnModel();

        // Loop through each column and set its preferred width
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Set car Information into table
        for (int i = 0; i < TOTALSPOTS; i++) {
            if (parkingSystem.getSpot(i) != null) {
                String id = Integer.toString(parkingSystem.getSpot(i).getId());
                String lastName = parkingSystem.getSpot(i).getCar().getPersonSurname();
                String carNumber = parkingSystem.getSpot(i).getCar().getCarNumberFromClassCar().toUpperCase();
                String carBrand = parkingSystem.getSpot(i).getCar().getCarMark();
                String carModel = parkingSystem.getSpot(i).getCar().getCarModel();
                Instant instant = parkingSystem.getSpot(i).getParkingData().toInstant(ZoneOffset.ofTotalSeconds(7200));
                Date date = Date.from(instant);
                String parkingTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
                String[] DataToAdd = { id, lastName, carBrand, carModel, carNumber.toUpperCase(), parkingTime };
                tableModel.addRow(DataToAdd);
            }
        }
        takeCarBox2.setEditable(false);
        addListeners();
        submitCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCar(parkingSystem);
            }
        });
        carOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeCarOut(parkingSystem);
            }
        });

        mainFrame.add(infoPanel);
        mainFrame.setVisible(true);
    }

    private JPanel setPanel() {
        infoPanel = new JPanel(null);

        // Labels
        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(14, 14, 100, 20);
        infoPanel.add(lastNameLabel);

        phoneNumberLabel = new JLabel("Phone Number");
        phoneNumberLabel.setBounds(182, 14, 100, 20);
        infoPanel.add(phoneNumberLabel);

        carBrandLabel = new JLabel("Car Brand");
        carBrandLabel.setBounds(14, 90, 100, 20);
        infoPanel.add(carBrandLabel);

        carModelLabel = new JLabel("Car Model");
        carModelLabel.setBounds(182, 90, 100, 20);
        infoPanel.add(carModelLabel);

        carNumberLabel = new JLabel("Car Number");
        carNumberLabel.setBounds(14, 172, 100, 20);
        infoPanel.add(carNumberLabel);

        searchLabel = new JLabel("Search by");
        searchLabel.setBounds(381, 276, 100, 20);
        infoPanel.add(searchLabel);

        takeOutLabel = new JLabel("Take out");
        takeOutLabel.setBounds(577, 276, 100, 20);
        infoPanel.add(takeOutLabel);

        // Warn Labels
        warn1 = new JLabel("Invalid Last Name");
        warn1.setBounds(14, 64, 200, 20);
        warn1.setForeground(Color.RED);
        warn1.setVisible(false);
        infoPanel.add(warn1);

        warn2 = new JLabel("Invalid phone Number");
        warn2.setBounds(182, 64, 200, 20);
        warn2.setForeground(Color.RED);
        warn2.setVisible(false);
        infoPanel.add(warn2);

        warn3 = new JLabel("No Car Brand found");
        warn3.setBounds(14, 145, 200, 20);
        warn3.setForeground(Color.RED);
        warn3.setVisible(false);
        infoPanel.add(warn3);

        warn4 = new JLabel("Invalid Info");
        warn4.setBounds(182, 145, 100, 20);
        warn4.setForeground(Color.RED);
        warn4.setVisible(false);
        infoPanel.add(warn4);

        warn5 = new JLabel("Non Ukraine plate");
        warn5.setBounds(14, 225, 200, 20);
        warn5.setForeground(Color.RED);
        warn5.setVisible(false);
        infoPanel.add(warn5);

        // Fields
        lastNameField = new JTextField();
        lastNameField.setBounds(14, 31, 147, 21);
        infoPanel.add(lastNameField);

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(182, 31, 147, 21);
        infoPanel.add(phoneNumberField);

        carBrandField = new JTextField();
        carBrandField.setBounds(14, 107, 147, 21);
        infoPanel.add(carBrandField);

        carModelField = new JTextField();
        carModelField.setBounds(182, 107, 147, 21);
        infoPanel.add(carModelField);

        carNumberField = new JTextField();
        carNumberField.setBounds(14, 189, 147, 21);
        infoPanel.add(carNumberField);

        searchByField = new JTextField();
        searchByField.setBounds(380, 335, 149, 25);
        infoPanel.add(searchByField);

        // Buttons
        submitCarButton = new JButton("Submit");
        submitCarButton.setBounds(14, 258, 95, 36);
        submitCarButton.setBackground(new Color(0x1f8fe8));
        infoPanel.add(submitCarButton);

        carOutButton = new JButton("Take out Car");
        carOutButton.setBounds(576, 335, 106, 25);
        carOutButton.setBackground(new Color(0x888888));
        carOutButton.setEnabled(false);
        infoPanel.add(carOutButton);

        // ChoiceBoxes
        carDropBox = new JComboBox<>(carDropString);
        carDropBox.setBounds(380, 299, 95, 26);
        infoPanel.add(carDropBox);

        takeCarBox1 = new JComboBox<>(takeCarString);
        takeCarBox1.setBounds(576, 299, 106, 26);
        infoPanel.add(takeCarBox1);

        takeCarBox2 = new JComboBox<>();
        takeCarBox2.setBounds(686, 299, 150, 26);
        infoPanel.add(takeCarBox2);

        return infoPanel;
    }

    private void addListeners() {
        // Adds listeners to handle user input and events
        lastNameField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            public void update(DocumentEvent e) {
                String lastNameString = lastNameField.getText();
                if (lastNameString.contains(" ")) {
                    errorCheck(true);
                    warn1.setVisible(true);
                } else {
                    errorCheck(false);
                    warn1.setVisible(false);
                }
            }
        });

        phoneNumberField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            public void update(DocumentEvent e) {
                String phoneString = phoneNumberField.getText();
                if (phoneString.matches("(\\d{3}-\\d{3}-\\d{2}-\\d{2}|\\d{10})")) {
                    errorCheck(false);
                    warn2.setVisible(false);
                } else {
                    errorCheck(true);
                    warn2.setVisible(true);
                }
            }
        });

        carBrandField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            public void update(DocumentEvent e) {
                String brandString = carBrandField.getText();
                for (String brand : carBrandsStrings) {
                    if (brandString.equalsIgnoreCase(brand)) {
                        errorCheck(false);
                        warn3.setVisible(false);
                        break;
                    } else {
                        errorCheck(true);
                        warn3.setVisible(true);
                    }
                }
            }
        });

        carModelField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            public void update(DocumentEvent e) {
                String modelString = carModelField.getText();
                if (!modelString.matches("[a-zA-Z0-9 .\\-_]*")) {
                    warn4.setVisible(true);
                    errorCheck(true);
                } else {
                    warn4.setVisible(false);
                    errorCheck(false);
                }
            }
        });

        carNumberField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            public void update(DocumentEvent e) {
                String numberString = carNumberField.getText().toUpperCase();

                String plateNumberPattern = "^[A-Z]{2}\\d{4}[A-Z]{2}$";
                if (numberString.matches(plateNumberPattern)) {
                    warn5.setVisible(false);
                    errorCheck(false);
                } else {
                    warn5.setVisible(true);
                    errorCheck(true);
                }

            }
        });

        takeCarBox1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedIndex = takeCarBox1.getSelectedIndex();
                // Delete 0 index choice !Only for esthetic!
                if (selectedIndex != 0) {
                    takeCarBox1.removeItemAt(0);
                    takeCarBox2.setEditable(true);
                    takeCarBox1.removeActionListener(this); // Remove the action listener after the first selection
                }
            }
        });

        takeCarBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get all Spot Id or Parking Time
                String selectedString = (String) takeCarBox1.getSelectedItem();
                if (selectedString.equals("Spot ID")) {
                    takeCarBox2.removeAllItems();
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        takeCarBox2.addItem((String) tableModel.getValueAt(i, 0)); // Assuming 0 is the index of the "№
                                                                                   // Spot" column
                    }
                } else {
                    takeCarBox2.removeAllItems();
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        takeCarBox2.addItem((String) tableModel.getValueAt(i, 4)); // Assuming 4 is the index of the
                                                                                   // "Car Number" column
                    }
                }
            }
        });

        takeCarBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carOutButton.setEnabled(true);
                carOutButton.setBackground(new Color(0x1f8fe8));
                takeCarBox2.removeActionListener(this);
            }
        });

        carDropBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = carDropBox.getSelectedIndex();

                // Delete 0 index choice !Only for esthetic!
                if (selectedIndex != 0) {
                    carDropBox.removeItemAt(0);
                    carDropBox.removeActionListener(this); // Remove the action listener after the first selection
                }
            }
        });

        searchByField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }

            private void filterTable() {
                int index = carDropBox.getSelectedIndex();
                index++;
                String data = searchByField.getText().toLowerCase();

                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + data, index));
            }
        });
    }

    abstract class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        // Abstract method to be implemented by subclasses
        public abstract void update(DocumentEvent e);

        // Default implementations for insert, remove, and change events
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        public void changedUpdate(DocumentEvent e) {
            // Plain text components don't fire these events
        }
    }

    public void addCar(ParkingSystem parkingSystem) {
        String lastName = lastNameField.getText();
        String phone = phoneNumberField.getText();
        String carNumber = carNumberField.getText();
        String carBrand = carBrandField.getText();
        String carModel = carModelField.getText();
        String parkingTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        String id = Integer.toString(parkingSystem.parkCar(new Car(lastName, phone, carBrand, carModel, carNumber)));
        if (Integer.parseInt(id) == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Sorry all parking spots already occupied", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[] rowData = { id, lastName, phone, carNumber, carBrand, carModel, parkingTime };
            tableModel.addRow(rowData);

        }

        // Clear form fields after adding a car
        resetAll();
    }

    public void takeCarOut(ParkingSystem parkingSystem) {
        String choiceCarString = (String) takeCarBox1.getSelectedItem();

        if (choiceCarString.equals("Spot ID")) {
            String selectedIdString = (String) takeCarBox2.getSelectedItem();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String valueInTable = (String) tableModel.getValueAt(i, 0);
                if (selectedIdString.equals(valueInTable)) {
                    tableModel.removeRow(i);
                    int spotId = Integer.parseInt(selectedIdString); // Parse the selected ID
                    double payment = parkingSystem.findBySpotIdAndPay(spotId);
                    JOptionPane.showMessageDialog(mainFrame, "Payment: " + Double.toString(payment) +
                            " grn", "To pay", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } else {
            String selectedCarNumber = (String) takeCarBox2.getSelectedItem();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String valueInTable = (String) tableModel.getValueAt(i, 4); // Assuming car number is at index 4
                if (selectedCarNumber.equals(valueInTable)) {
                    tableModel.removeRow(i);
                    double payment = parkingSystem.findByCarNumberAndPay(selectedCarNumber); // Adjust this method
                                                                                             // accordingly
                    JOptionPane.showMessageDialog(mainFrame, "Payment: " + Double.toString(payment) +
                            " grn", "To pay", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }

    }

    public void resetAll() {
        lastNameField.setText("");
        phoneNumberField.setText("");
        carNumberField.setText("");
        carBrandField.setText("");
        carModelField.setText("");
        warn1.setVisible(false);
        warn2.setVisible(false);
        warn3.setVisible(false);
        warn4.setVisible(false);
        warn5.setVisible(false);
    }

    public void errorCheck(boolean has_error) {
        if (has_error) {
            submitCarButton.setOpaque(true);
            submitCarButton.setBackground(new Color(0, 0, 0, 0.7f));
            submitCarButton.setEnabled(false);
        } else {
            submitCarButton.setOpaque(false);
            submitCarButton.setBackground(new Color(0, 0, 0, 1f));
            submitCarButton.setEnabled(true);
        }
    }
}
