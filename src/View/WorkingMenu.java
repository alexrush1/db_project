package View;

import Core.ConnectionDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkingMenu {
    private ConnectionDriver conDriver;
    JFrame frame;
    JPanel panel;

    public WorkingMenu(ConnectionDriver conDriver) throws SQLException {
        this.conDriver = conDriver;
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);
        JButton list_tables = new JButton("Schedule");
        list_tables.setBounds(68, 30, 150, 35);
        panel.add(list_tables);
        list_tables.addActionListener(this::getSchedule);
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
                row[0] = set.getString(1);
                row[1] = set.getString(2);
                row[2] = set.getString(3);
                row[3] = set.getString(4);
                row[4] = set.getString(5);
                row[5] = set.getString(6);
                row[6] = set.getString(7);
                row[7] = set.getString(8);
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
}
