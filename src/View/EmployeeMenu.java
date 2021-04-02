package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class EmployeeMenu {

    ConnectionDriver conDriver;

    public EmployeeMenu(ConnectionDriver conDriver, int count) {
        this.conDriver = conDriver;
        JFrame passengersMenu = new JFrame("New Employee");
        passengersMenu.setSize(300, 375);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setBounds(30, 10, 100, 30);

        JTextField idField = new JTextField(20);
        idField.setBounds(110, 10, 160, 30);
        idField.setEditable(false);
        idField.setText(String.valueOf(count + 1));

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(30, 50, 100, 30);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(110, 50, 160, 30);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(30, 90, 100, 30);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(110, 90, 160, 30);

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setBounds(30, 130, 100, 30);

        JTextField ageField = new JTextField(20);
        ageField.setBounds(110, 130, 160, 30);

        JLabel sexLabel = new JLabel("Sex");
        sexLabel.setBounds(30, 170, 100, 30);

        Vector<String> data = new Vector<>();
        data.add("Male");
        data.add("Female");

        JComboBox comboBox = new JComboBox(data);
        comboBox.setBounds(110, 170, 160, 30);

        JLabel childrenLabel = new JLabel("Children");
        childrenLabel.setBounds(30, 210, 100, 30);

        JTextField childrenField = new JTextField(20);
        childrenField.setBounds(130, 210, 140, 30);

        JLabel departmentId = new JLabel("Department ID");
        departmentId.setBounds(30, 250, 100, 30);

        JTextField departmentIdField = new JTextField(20);
        departmentIdField.setBounds(130, 250, 140, 30);


        JButton commit = new JButton("Save");
        commit.setBounds(110, 290, 80, 30);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox.getSelectedIndex());
                try {
                    if (comboBox.getSelectedIndex() == 0) {
                        conDriver.commitEmployee(count + 1, firstNameField.getText(), lastNameField.getText(), ageField.getText(), childrenField.getText(), departmentIdField.getText(),0);
                    } else {
                        conDriver.commitEmployee(count + 1, firstNameField.getText(), lastNameField.getText(), ageField.getText(), childrenField.getText(), departmentIdField.getText(),0);
                    }
                    JOptionPane.showMessageDialog(passengersMenu, "Done!");
                    passengersMenu.setVisible(false);
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(passengersMenu, "SQLException [error -1]");
                }
            }
        });


        passengersMenu.add(idField);
        passengersMenu.add(idLabel);
        passengersMenu.add(firstNameLabel);
        passengersMenu.add(firstNameField);
        passengersMenu.add(lastNameLabel);
        passengersMenu.add(lastNameField);
        passengersMenu.add(ageLabel);
        passengersMenu.add(ageField);
        passengersMenu.add(childrenField);
        passengersMenu.add(childrenLabel);
        passengersMenu.add(departmentId);
        passengersMenu.add(departmentIdField);
        passengersMenu.add(sexLabel);
        passengersMenu.add(comboBox);
        passengersMenu.add(commit);

        passengersMenu.setVisible(true);
    }

    public EmployeeMenu(int id, String firstName, String lastName, String age, int sex, String children, String departmentIdd, ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        JFrame passengersMenu = new JFrame("Edit Employee");
        passengersMenu.setSize(300, 375);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setBounds(30, 10, 100, 30);

        JTextField idField = new JTextField(20);
        idField.setBounds(110, 10, 160, 30);
        idField.setEditable(false);
        idField.setText(String.valueOf(id));

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(30, 50, 100, 30);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(110, 50, 160, 30);
        firstNameField.setText(firstName);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(30, 90, 100, 30);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(110, 90, 160, 30);
        lastNameField.setText(lastName);

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setBounds(30, 130, 100, 30);

        JTextField ageField = new JTextField(20);
        ageField.setBounds(110, 130, 160, 30);
        ageField.setText(age);

        JLabel sexLabel = new JLabel("Sex");
        sexLabel.setBounds(30, 170, 100, 30);

        Vector<String> data = new Vector<>();
        data.add("Male");
        data.add("Female");

        JComboBox comboBox = new JComboBox(data);
        comboBox.setBounds(110, 170, 160, 30);
        comboBox.setSelectedIndex(sex);

        JLabel childrenLabel = new JLabel("Children");
        childrenLabel.setBounds(30, 210, 100, 30);

        JTextField childrenField = new JTextField(20);
        childrenField.setBounds(130, 210, 140, 30);
        childrenField.setText(children);

        JLabel departmentId = new JLabel("Department ID");
        departmentId.setBounds(30, 250, 100, 30);

        JTextField departmentIdField = new JTextField(20);
        departmentIdField.setBounds(130, 250, 140, 30);
        departmentIdField.setText(departmentIdd);


        JButton commit = new JButton("Save");
        commit.setBounds(110, 290, 80, 30);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox.getSelectedIndex());
                try {
                    JOptionPane.showMessageDialog(passengersMenu, "Please press Update button after edit something");
                    if (comboBox.getSelectedIndex() == 0) {
                        conDriver.updateEmployee(id, firstNameField.getText(), lastNameField.getText(), ageField.getText(), childrenField.getText(), departmentIdField.getText(),0);
                    } else {
                        conDriver.updateEmployee(id, firstNameField.getText(), lastNameField.getText(), ageField.getText(), childrenField.getText(), departmentIdField.getText(),0);
                    }
                    JOptionPane.showMessageDialog(passengersMenu, "Done!");
                    passengersMenu.setVisible(false);
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(passengersMenu, "SQLException [error -1]");
                }
            }
        });


        passengersMenu.add(idField);
        passengersMenu.add(idLabel);
        passengersMenu.add(firstNameLabel);
        passengersMenu.add(firstNameField);
        passengersMenu.add(lastNameLabel);
        passengersMenu.add(lastNameField);
        passengersMenu.add(ageLabel);
        passengersMenu.add(ageField);
        passengersMenu.add(childrenField);
        passengersMenu.add(childrenLabel);
        passengersMenu.add(departmentId);
        passengersMenu.add(departmentIdField);
        passengersMenu.add(sexLabel);
        passengersMenu.add(comboBox);
        passengersMenu.add(commit);

        passengersMenu.setVisible(true);
    }

}
