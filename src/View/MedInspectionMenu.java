package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class MedInspectionMenu {
    ConnectionDriver conDriver;

    public MedInspectionMenu(ConnectionDriver conDriver, String employeeId, String firstName, String lastName) throws SQLException {
        this.conDriver = conDriver;
        JFrame passengersMenu = new JFrame("New Inspection");
        passengersMenu.setSize(300, 300);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        int count = conDriver.countInspections();

        JLabel numIdLabel = new JLabel("ID");
        numIdLabel.setBounds(30, 10, 100, 30);

        JTextField numIdField = new JTextField(20);
        numIdField.setBounds(110, 10, 160, 30);
        numIdField.setEditable(false);
        numIdField.setText(String.valueOf(count + 1));

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setBounds(30, 50, 100, 30);

        JTextField idField = new JTextField(20);
        idField.setBounds(110, 50, 160, 30);
        idField.setEditable(false);
        idField.setText(String.valueOf(employeeId));

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(30, 90, 100, 30);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(110, 90, 160, 30);
        firstNameField.setText(firstName);
        firstNameField.setEditable(false);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(30, 130, 100, 30);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(110, 130, 160, 30);
        lastNameField.setText(lastName);
        lastNameField.setEditable(false);

        JLabel status = new JLabel("Insp. status");
        status.setBounds(30, 170, 100, 30);

        Vector<String> data = new Vector<>();
        data.add("Success");
        data.add("Failed");

        JComboBox comboBox = new JComboBox(data);
        comboBox.setBounds(110, 170, 160, 30);


        JButton commit = new JButton("Save");
        commit.setBounds(110, 210, 80, 30);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox.getSelectedIndex());
                try {
                    if (comboBox.getSelectedIndex() == 0) {
                        conDriver.commitInspection(count + 1, employeeId, 0);
                    } else {
                        conDriver.commitInspection(count + 1, employeeId, 1);
                    }
                    JOptionPane.showMessageDialog(passengersMenu, "Please press Update button after edit something");
                    JOptionPane.showMessageDialog(passengersMenu, "Done!");
                    passengersMenu.setVisible(false);
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(passengersMenu, "SQLException [error -1]: " + ee);
                }
            }
        });

        passengersMenu.add(numIdLabel);
        passengersMenu.add(numIdField);
        passengersMenu.add(idField);
        passengersMenu.add(idLabel);
        passengersMenu.add(firstNameLabel);
        passengersMenu.add(firstNameField);
        passengersMenu.add(lastNameLabel);
        passengersMenu.add(lastNameField);
        passengersMenu.add(status);
        passengersMenu.add(comboBox);
        passengersMenu.add(commit);

        passengersMenu.setVisible(true);
    }
}
