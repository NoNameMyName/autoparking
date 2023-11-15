package org.frontend;

import org.backend.Car;
import org.backend.ParkingSpot;
import org.backend.ParkingSystem;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

import static org.main.Constants.TOTALSPOTS;

public class CarParkingApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField lastNameField, phoneField, carNumberField, carBrandField, carModelField;


    public CarParkingApp(ParkingSystem parkingSystem) {
        frame = new JFrame("Car Parking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 400);

        // Table
        String[] columnNames = {"Id of parking spot", "Last Name", "Phone", "Car Number", "Car Brand", "Car Model", "Parking Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form for adding a car
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        lastNameField = new JTextField();
        phoneField = new JTextField();
        carNumberField = new JTextField();
        carBrandField = new JTextField();
        carModelField = new JTextField();

        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Car Number:"));
        formPanel.add(carNumberField);
        formPanel.add(new JLabel("Car Brand:"));
        formPanel.add(carBrandField);
        formPanel.add(new JLabel("Car Model:"));
        formPanel.add(carModelField);

        // Add sorter to table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JButton addButton = new JButton("Add Car");
        for (int i = 0; i < TOTALSPOTS; i++) {
            if (parkingSystem.getSpot(i) != null){
                String id = Integer.toString(parkingSystem.getSpot(i).getId());
                String lastName = parkingSystem.getSpot(i).getCar().getPersonSurname();
                String phone = parkingSystem.getSpot(i).getCar().getPersonTelephoneNumber();
                String carNumber = parkingSystem.getSpot(i).getCar().getCarNumberFromClassCar();
                String carBrand = parkingSystem.getSpot(i).getCar().getCarMark();
                String carModel = parkingSystem.getSpot(i).getCar().getCarModel();
                Instant instant = parkingSystem.getSpot(i).getParkingData().toInstant(ZoneOffset.ofTotalSeconds(7200));
                Date date = Date.from(instant);
                String parkingTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
                String[] DataToAdd = {id, lastName, phone, carNumber, carBrand, carModel, parkingTime};
                tableModel.addRow(DataToAdd);
            }
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCar(parkingSystem);
            }
        });

        formPanel.add(addButton);

        JButton removeButton = new JButton("Pay that spot");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    double payment = parkingSystem.findByCarNumberAndPay((String) table.getModel().getValueAt(selectedRow, 3));
                    JOptionPane.showMessageDialog(frame, "Payment: " + Double.toString(payment) +
                                    " grn", "To pay", JOptionPane.INFORMATION_MESSAGE);
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        formPanel.add(removeButton);

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(formPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }
    private void addCar(ParkingSystem parkingSystem) {
        String lastName = lastNameField.getText();
        String phone = phoneField.getText();
        String carNumber = carNumberField.getText();
        String carBrand = carBrandField.getText();
        String carModel = carModelField.getText();
        String parkingTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        String id = Integer.toString(parkingSystem.parkCar(new Car(lastName, phone, carBrand, carModel, carNumber)));

        String[] rowData = {id, lastName, phone, carNumber, carBrand, carModel, parkingTime};
        tableModel.addRow(rowData);

        // Clear form fields after adding a car
        lastNameField.setText("");
        phoneField.setText("");
        carNumberField.setText("");
        carBrandField.setText("");
        carModelField.setText("");
    }
}
