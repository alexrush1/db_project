package View;

import Core.ConnectionDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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

        JButton employeesMenu = new JButton("Employees Menu");
        employeesMenu.setBounds(68, 120, 160, 35);
        panel.add(employeesMenu);
        employeesMenu.addActionListener(this::openEmployeesMenu);

        JButton medInspectionMenu = new JButton("Medical Inspection");
        medInspectionMenu.setBounds(68, 165, 160, 35);
        panel.add(medInspectionMenu);
        medInspectionMenu.addActionListener(this::medInspectionMenu);

        JButton techMenu = new JButton("Technical menu");
        techMenu.setBounds(68, 210, 160, 35);
        panel.add(techMenu);
        techMenu.addActionListener(this::openTechMenu);

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
            pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


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
            pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


            updatePassengers(model);

            JButton edit = new JButton("Edit info");
            edit.setBounds(650, 320, 100, 35);
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

            JButton update = new JButton("Update");
            update.setBounds(500, 320, 100, 35);
            update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePassengers(model);
                }
            });

            frame.add(update);
            frame.add(edit);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    public void getEmployeesList (ActionEvent event) {
        frame = new JFrame("Employees List");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getPassengersList();

            JTable table = new JTable();
            Object[] columnNames = {"First name", "Last name", "Age", "Sex", "Children", "ID", "Department ID"};
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);

            table.setModel(model);
            table.setEnabled(true);
            table.setBackground(Color.gray);
            table.setForeground(Color.white);
            table.setRowHeight(20);

            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(0,0,800,300);
            pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            updateEmployees(model, 0);

            JButton edit = new JButton("Edit info");
            edit.setBounds(650, 320, 100, 35);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int current = table.getSelectedRow();
                    int sex = 0;
                    if (((String) model.getValueAt(current, 3)).equals("Female")) {sex = 1;} else {sex = 0;}
                    EmployeeMenu menu = new EmployeeMenu(
                            Integer.parseInt((String) model.getValueAt(current, 5)),
                            (String) model.getValueAt(current, 0),
                            (String) model.getValueAt(current, 1),
                            (String) model.getValueAt(current, 2),
                            sex,
                            (String) model.getValueAt(current, 4),
                            (String) model.getValueAt(current, 6),
                            conDriver);
                    //System.out.println(Integer.parseInt((String) model.getValueAt(current, 5)));
                }
            });

            JButton update = new JButton("Update");
            update.setBounds(500, 320, 100, 35);
            update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateEmployees(model, 0);
                }
            });

            frame.add(update);
            frame.add(edit);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    private void medInspectionMenu(ActionEvent e) {
        frame = new JFrame("Choose action");
        frame.setSize(620, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);

        JTable table = new JTable();
        Object[] columnNames = {"First name", "Last name", "Age", "Sex", "ID"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        table.setModel(model);
        table.setEnabled(true);
        table.setBackground(Color.gray);
        table.setForeground(Color.white);
        table.setRowHeight(25);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(2,0,600,300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        updateEmployees(model, 1);

            JButton edit = new JButton("New Med Info");
            edit.setBounds(450, 320, 150, 35);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int current = table.getSelectedRow();
                    try {
                        MedInspectionMenu menu = new MedInspectionMenu(conDriver,
                                (String) model.getValueAt(current, 4),
                                (String) model.getValueAt(current, 0),
                                (String) model.getValueAt(current, 1));
                    } catch (SQLException ee) {
                        JOptionPane.showMessageDialog(frame, "SQLException [Error -1]: " + ee);
                    }
                }
            });

        JButton update = new JButton("Update");
        update.setBounds(300, 320, 100, 35);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployees(model, 1);
            }
        });

        frame.add(update);
        frame.add(edit);
        frame.add(pane);
        frame.setVisible(true);
    }

    private void planeList(ActionEvent e) {
        frame = new JFrame("Planes");
        frame.setSize(620, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);

        JTable table = new JTable();
        Object[] columnNames = {"Plane ID", "Aircraft type", "Repairs", "Last repair", "Age", "Flight Hours"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        table.setModel(model);
        table.setEnabled(true);
        table.setBackground(Color.gray);
        table.setForeground(Color.white);
        table.setRowHeight(25);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(2,0,600,300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        updatePlanes(model);

        JButton edit = new JButton("New Plane");
        edit.setBounds(450, 320, 150, 35);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaneMenu menu = new PlaneMenu(conDriver);
            }
        });

        JButton update = new JButton("Update");
        update.setBounds(300, 320, 100, 35);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlanes(model);
            }
        });

        frame.add(update);
        frame.add(edit);
        frame.add(pane);
        frame.setVisible(true);
    }

    private void planeEmployeesList(ActionEvent e) {
        frame = new JFrame("Plane Employees");
        frame.setSize(620, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);

        JTable table = new JTable();
        Object[] columnNames = {"Employee ID", "Plane ID", "First Name", "Last Name", "Position"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        table.setModel(model);
        table.setEnabled(true);
        table.setBackground(Color.gray);
        table.setForeground(Color.white);
        table.setRowHeight(25);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(2,0,600,300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        updatePlanePosition(model);

        JButton edit = new JButton("Edit Position");
        edit.setBounds(450, 320, 150, 35);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PlaneMenu menu = new PlaneMenu(conDriver);
            }
        });

        JButton update = new JButton("Update");
        update.setBounds(300, 320, 100, 35);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlanePosition(model);
            }
        });

        frame.add(update);
        frame.add(edit);
        frame.add(pane);
        frame.setVisible(true);
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

    private void openTechMenu(ActionEvent e) {
        JFrame technicalMenu = new JFrame("Technical menu");
        technicalMenu.setSize(300, 175);
        technicalMenu.setLocationRelativeTo(null);
        technicalMenu.setFocusable(true);
        technicalMenu.setLayout(null);

        JButton planeList = new JButton("Planes");
        planeList.setBounds(68, 15, 160, 35);
        technicalMenu.add(planeList);
        planeList.addActionListener(this::planeList);

        JButton planeEmployees = new JButton("Plane employees");
        planeEmployees.setBounds(68, 55, 160, 35);
        technicalMenu.add(planeEmployees);
        planeEmployees.addActionListener(this::planeEmployeesList);

        JButton planeRepair = new JButton("Plane repair");
        planeRepair.setBounds(68, 95, 160, 35);
        technicalMenu.add(planeRepair);

        technicalMenu.setVisible(true);
    }

    private void openEmployeesMenu(ActionEvent e) {
        JFrame employeesMenu = new JFrame("Employees menu");
        employeesMenu.setSize(300, 150);
        employeesMenu.setLocationRelativeTo(null);
        employeesMenu.setFocusable(true);
        employeesMenu.setLayout(null);

        JButton addEmployee = new JButton("New Employee");
        addEmployee.setBounds(68, 15, 160, 35);
        employeesMenu.add(addEmployee);
        addEmployee.addActionListener(this::addEmployee);

        JButton passengerList = new JButton("Employees list");
        passengerList.setBounds(68, 55, 160, 35);
        employeesMenu.add(passengerList);
        passengerList.addActionListener(this::getEmployeesList);

        employeesMenu.setVisible(true);
    }

    private void addEmployee(ActionEvent e) {
        try {
            int count = conDriver.countEmployees();
            EmployeeMenu menu = new EmployeeMenu(conDriver, count);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]: " + throwables);
        }
    }

    private void addPassenger(ActionEvent e) {
        try {
            int count = conDriver.countPassengers();
            //System.out.println(count);
            PassengerMenu menu = new PassengerMenu(conDriver, count);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]: " + throwables);
        }
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

    private void createTables(ActionEvent e) {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            conDriver.createTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(frame, "IOException [error -2]");
        }
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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

    public void updatePassengers(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ResultSet set = conDriver.getPassengersList();
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
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        }
    }

    public void updateEmployees(DefaultTableModel model, int mode) {
        try {
            model.setRowCount(0);
            ResultSet set = conDriver.getEmployeesList();
            if (mode == 0) {
                while (set.next()) {
                    Object[] row = new Object[7];
                    row[0] = set.getString(2);
                    row[1] = set.getString(3);
                    row[2] = set.getString(4);
                    if (set.getString(5).equals("0")) {
                        row[3] = "Male";
                    } else {
                        row[3] = "Female";
                    }
                    row[4] = set.getString(6);
                    row[5] = set.getString(1);
                    row[6] = set.getString(7);
                    model.addRow(row);
                }
                set.close();
            } else {
                while (set.next()) {
                    Object[] row = new Object[7];
                    row[0] = set.getString(2);
                    row[1] = set.getString(3);
                    row[2] = set.getString(4);
                    if (set.getString(5).equals("0")) {
                        row[3] = "Male";
                    } else {
                        row[3] = "Female";
                    }
                    row[4] = set.getString(1);
                    model.addRow(row);
                }
                set.close();
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        }
    }

    public void updatePlanes(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ResultSet set = conDriver.getPlanesList();
            int i = 0;
            while (set.next()) {
                i++;
                Object[] row = new Object[6];
                row[0] = set.getString(1);
                row[1] = set.getString(2);
                row[2] = set.getString(3);
                row[3] = set.getString(4);
                row[4] = set.getString(5);
                row[5] = set.getString(6);
                model.addRow(row);
            }
            set.close();
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        }
    }

    public void updatePlanePosition(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ResultSet set = conDriver.getPlanePositions();
            int i = 0;
            while (set.next()) {
                i++;
                Object[] row = new Object[5];
                row[0] = set.getString(1);
                row[1] = set.getString(2);
                row[2] = set.getString(3);
                row[3] = set.getString(4);
                if (set.getString(5).equals("1")) {
                    row[4] = "Pilot";
                } else if (set.getString(5).equals("2")) {
                    row[4] = "Technic";
                } else {
                    row[4] = "Personal";
                }
                model.addRow(row);
            }
            set.close();
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        }
    }
}
