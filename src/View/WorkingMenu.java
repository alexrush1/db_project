package View;

import Core.ConnectionDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkingMenu {
    private ConnectionDriver conDriver;
    JFrame frame;
    JFrame passengersMenu;
    JPanel panel;

    public WorkingMenu(ConnectionDriver conDriver, String username) throws SQLException {
        this.conDriver = conDriver;
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel field = new JLabel("User: " + username);
        //field.setFont(Font.getFont(Font.SANS_SERIF));
        field.setBounds(150, 5, 150, 10);
        panel.add(field);

        JButton list_tables = new JButton("Schedule");
        list_tables.setBounds(68, 30, 160, 35);
        panel.add(list_tables);
        list_tables.addActionListener(this::getSchedule);

        JButton passengersMenu = new JButton("Passengers Menu");
        passengersMenu.setBounds(68, 75, 160, 35);
        panel.add(passengersMenu);
        passengersMenu.addActionListener(this::openPassengersMenu);

        JButton developerMenu = new JButton("Developer Menu");
        developerMenu.addActionListener(this::openDeveloperMenu);
        developerMenu.setBounds(68, 300, 160, 35);
        panel.add(developerMenu);

        frame.getContentPane().add(panel);
    }

    public void getSchedule (ActionEvent event) {
        frame = new JFrame("List tables");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getListTables();

            JTable table = new JTable();
            Object[] columnNames = {"Aircraft type", "Flight number", "Departure days", "Departure time", "Arrival time", "Departure", "Arrival", "Ticket price"};
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);
            table.setModel(model);
            table.setEnabled(false);
            table.setBackground(Color.gray);
            table.setForeground(Color.white);
            table.setRowHeight(20);
            JScrollPane pane = new JScrollPane(table);

            int i = 0;
            while (set.next()) {
                i++;
                Object[] row = new Object[8];
                row[0] = set.getString(3);
                row[1] = set.getString(4);
                row[2] = set.getString(5);
                row[3] = set.getString(6);
                row[4] = set.getString(7);
                row[5] = set.getString(8);
                row[6] = set.getString(9);
                row[7] = set.getString(10);
                model.addRow(row);
            }
            set.close();
            JLabel countLabel = new JLabel("Records in table: " + i);
            countLabel.setBounds(600, 300, 200, 20);
            frame.add(countLabel);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    public void getPassengersList (ActionEvent event) {
        frame = new JFrame("Passengers List");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getPassengersList();

            JTable table = new JTable();
            Object[] columnNames = {"First name", "Last name", "Document ID", "Flight number", "Status", "ID"};
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);

            table.setModel(model);
            table.setEnabled(true);
            table.setBackground(Color.gray);
            table.setForeground(Color.white);
            table.setRowHeight(20);

            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(0,0,800,300);

            int i = 0;
            while (set.next()) {
                i++;
                Object[] row = new Object[6];
                row[0] = set.getString(2);
                row[1] = set.getString(3);
                row[2] = set.getString(4);
                row[3] = set.getString(5);
                row[5] = set.getString(1);
                if (set.getString(6).equals("1")) {
                    row[4] = "Departured";
                } else { row[4] = "Detained"; }
                model.addRow(row);
            }
            set.close();

            JButton edit = new JButton("Edit info");
            edit.setBounds(700, 320, 100, 35);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int current = table.getSelectedRow();
                    int status = 0;
                    if (((String) model.getValueAt(current, 4)).equals("Departured")) {status = 1;} else {status = 0;}
                    PassengerMenu menu = new PassengerMenu(
                    Integer.parseInt((String) model.getValueAt(current, 5)),
                            (String) model.getValueAt(current, 0),
                            (String) model.getValueAt(current, 1),
                            (String) model.getValueAt(current, 2),
                            (String) model.getValueAt(current, 3),
                            status,
                            conDriver);
                    //System.out.println(Integer.parseInt((String) model.getValueAt(current, 5)));
                }
            });
            frame.add(edit);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    private void openDeveloperMenu(ActionEvent e) {
        JFrame developerMenu = new JFrame("Developer menu");
        developerMenu.setSize(300, 175);
        developerMenu.setLocationRelativeTo(null);
        developerMenu.setFocusable(true);
        developerMenu.setLayout(null);

        JButton dropTables = new JButton("Drop tables");
        dropTables.setBounds(68, 15, 160, 35);
        developerMenu.add(dropTables);
        dropTables.addActionListener(this::dropTables);

        JButton createTables = new JButton("Create tables");
        createTables.setBounds(68, 55, 160, 35);
        developerMenu.add(createTables);
        createTables.addActionListener(this::createTables);

        JButton fillTables = new JButton("Fill tables");
        fillTables.setBounds(68, 95, 160, 35);
        developerMenu.add(fillTables);
        fillTables.addActionListener(this::fillTables);

        developerMenu.setVisible(true);
    }

    private void openPassengersMenu(ActionEvent e) {
        passengersMenu = new JFrame("Passengers menu");
        passengersMenu.setSize(300, 150);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        JButton addPassenger = new JButton("Add passenger");
        addPassenger.setBounds(68, 15, 160, 35);
        passengersMenu.add(addPassenger);
        addPassenger.addActionListener(this::addPassenger);

        JButton passengerList = new JButton("Passenger list");
        passengerList.setBounds(68, 55, 160, 35);
        passengersMenu.add(passengerList);
        passengerList.addActionListener(this::getPassengersList);

        passengersMenu.setVisible(true);
    }

    private void addPassenger(ActionEvent e) {
        PassengerMenu menu = new PassengerMenu(conDriver);
    }

    private void createTables(ActionEvent e) {
        passengersMenu.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            conDriver.createTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(frame, "IOException [error -2]");
        }
        passengersMenu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    private void dropTables(ActionEvent e) {
        try {
            conDriver.dropTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        }
    }

    private void fillTables(ActionEvent e) {
        try {
            conDriver.fillTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        }
    }
}
