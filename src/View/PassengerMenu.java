package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class PassengerMenu {

    ConnectionDriver conDriver;

    public PassengerMenu(ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        JFrame passengersMenu = new JFrame("New Passenger");
        passengersMenu.setSize(300, 300);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(30, 10, 100, 30);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(110, 10, 160, 30);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(30, 50, 100, 30);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(110, 50, 160, 30);

        JLabel documentIdLabel = new JLabel("Document ID");
        documentIdLabel.setBounds(30, 90, 100, 30);

        JTextField documentIdField = new JTextField(20);
        documentIdField.setBounds(110, 90, 160, 30);

        JLabel flightNumberLabel = new JLabel("Flight number");
        flightNumberLabel.setBounds(30, 130, 100, 30);

        JTextField flightNumberField = new JTextField(20);
        flightNumberField.setBounds(130, 130, 140, 30);

        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(30, 170, 100, 30);

        Vector<String> data = new Vector<>();
        data.add("Departured");
        data.add("Detained");

        JComboBox comboBox = new JComboBox(data);
        comboBox.setBounds(110, 170, 160, 30);

        JButton commit = new JButton("Save");
        commit.setBounds(120, 210, 60, 30);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox.getSelectedIndex());
                try {
                    if (comboBox.getSelectedIndex() == 0) {
                        conDriver.commitPassenger(firstNameField.getText(), lastNameField.getText(), documentIdField.getText(), flightNumberField.getText(), 1);
                    } else {
                        conDriver.commitPassenger(firstNameField.getText(), lastNameField.getText(), documentIdField.getText(), flightNumberField.getText(), 0);
                    }
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(passengersMenu, "SQLException [error -1]");
                }
            }
        });

        passengersMenu.add(firstNameLabel);
        passengersMenu.add(firstNameField);
        passengersMenu.add(lastNameLabel);
        passengersMenu.add(lastNameField);
        passengersMenu.add(documentIdLabel);
        passengersMenu.add(documentIdField);
        passengersMenu.add(flightNumberField);
        passengersMenu.add(flightNumberLabel);
        passengersMenu.add(statusLabel);
        passengersMenu.add(comboBox);
        passengersMenu.add(commit);

        passengersMenu.setVisible(true);
    }

    public PassengerMenu(int id, String firstName, String lastName, String documentId, String flightNumber, int status, ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        JFrame passengersMenu = new JFrame("Edit Passenger");
        passengersMenu.setSize(300, 300);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(30, 10, 100, 30);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(110, 10, 160, 30);
        firstNameField.setText(firstName);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(30, 50, 100, 30);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(110, 50, 160, 30);
        lastNameField.setText(lastName);

        JLabel documentIdLabel = new JLabel("Document ID");
        documentIdLabel.setBounds(30, 90, 100, 30);

        JTextField documentIdField = new JTextField(20);
        documentIdField.setBounds(110, 90, 160, 30);
        documentIdField.setText(documentId);

        JLabel flightNumberLabel = new JLabel("Flight number");
        flightNumberLabel.setBounds(30, 130, 100, 30);

        JTextField flightNumberField = new JTextField(20);
        flightNumberField.setBounds(130, 130, 140, 30);
        flightNumberField.setText(flightNumber);

        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(30, 170, 100, 30);

        Vector<String> data = new Vector<>();
        data.add("Departured");
        data.add("Detained");

        JComboBox comboBox = new JComboBox(data);
        comboBox.setBounds(110, 170, 160, 30);
        comboBox.setSelectedIndex(status);

        JButton commit = new JButton("Save");
        commit.setBounds(120, 210, 60, 30);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox.getSelectedIndex());
                try {
                    if (comboBox.getSelectedIndex() == 0) {
                        conDriver.updatePassenger(firstNameField.getText(), lastNameField.getText(), documentIdField.getText(), flightNumberField.getText(), 1, id);
                    } else {
                        conDriver.updatePassenger(firstNameField.getText(), lastNameField.getText(), documentIdField.getText(), flightNumberField.getText(), 0, id);
                    }
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(passengersMenu, "SQLException [error -1]: " + ee);
                }
            }
        });

        passengersMenu.add(firstNameLabel);
        passengersMenu.add(firstNameField);
        passengersMenu.add(lastNameLabel);
        passengersMenu.add(lastNameField);
        passengersMenu.add(documentIdLabel);
        passengersMenu.add(documentIdField);
        passengersMenu.add(flightNumberField);
        passengersMenu.add(flightNumberLabel);
        passengersMenu.add(statusLabel);
        passengersMenu.add(comboBox);
        passengersMenu.add(commit);

        passengersMenu.setVisible(true);
    }

}
