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
import java.util.Vector;

public class WorkingMenu {
    private ConnectionDriver conDriver;
    JFrame frame;
    JFrame passengersMenu;
    JPanel panel;

    public WorkingMenu(ConnectionDriver conDriver, String username, int role) throws SQLException {
        this.conDriver = conDriver;
        frame = new JFrame("Главное меню");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel field = new JLabel("Пользователь: " + username);
        //field.setFont(Font.getFont(Font.SANS_SERIF));
        field.setBounds(50, 5, 250, 10);
        panel.add(field);

        if (role == 0 || role == 3) {
            JButton list_tables = new JButton("Расписание");
            list_tables.setBounds(48, 30, 204, 35);
            panel.add(list_tables);
            list_tables.addActionListener(this::getSchedule);
        }

        if (role == 0 || role == 3) {
            JButton passengersMenu = new JButton("Меню паспортного контроля");
            passengersMenu.setBounds(48, 75, 204, 35);
            panel.add(passengersMenu);
            passengersMenu.addActionListener(this::openPassengersMenu);
        }

        if (role == 0 || role == 1) {
            JButton employeesMenu = new JButton("Меню отдела кадров");
            employeesMenu.setBounds(48, 120, 204, 35);
            panel.add(employeesMenu);
            employeesMenu.addActionListener(this::openEmployeesMenu);
        }

        if (role == 0 || role == 2) {
            JButton medInspectionMenu = new JButton("Меню мед. работников");
            medInspectionMenu.setBounds(48, 165, 204, 35);
            panel.add(medInspectionMenu);
            medInspectionMenu.addActionListener(this::medInspectionMenu);
        }

        if (role == 0 || role == 4) {
            JButton techMenu = new JButton("Меню тех. персонала");
            techMenu.setBounds(48, 210, 204, 35);
            panel.add(techMenu);
            techMenu.addActionListener(this::openTechMenu);
        }

        if (role == 0) {
            JButton developerMenu = new JButton("Меню разработчика");
            developerMenu.addActionListener(this::openDeveloperMenu);
            developerMenu.setBounds(48, 300, 204, 35);
            panel.add(developerMenu);
        }

        JButton logout = new JButton("Отключиться");
        logout.setBounds(200, 350, 100, 20);
        logout.addActionListener(this::logout);
        panel.add(logout);

        frame.getContentPane().add(panel);
    }

    public void logout(ActionEvent event) {
        frame.setVisible(false);
        try {
            ConnectionDriver conDriver = new ConnectionDriver();
            ConnectMenu logMenu = new ConnectMenu(conDriver);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Проблемы с соединением;");
        }
    }

    public void getSchedule (ActionEvent event) {
        frame = new JFrame("Расписание");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getListTables();

            JTable table = new JTable();
            Object[] columnNames = {"Тип самолёта", "Номер рейса", "Дни вылета", "Время вылета", "Время прибытия", "Аэропорт вылета", "Аэропорт прибытия", "Цена билета"};
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
            JLabel countLabel = new JLabel("Записей в таблице: " + i);
            countLabel.setBounds(600, 300, 200, 20);
            frame.add(countLabel);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    public void getPassengersList (ActionEvent event) {
        frame = new JFrame("Список пасажиров");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getPassengersList();

            JTable table = new JTable();
            Object[] columnNames = {"Фамилия", "Имя", "Номер документа", "Номер рейса", "Статус", "ID"};
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

            JButton edit = new JButton("Правка");
            edit.setBounds(650, 320, 100, 35);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int current = table.getSelectedRow();
                    int status = 0;
                    if (((String) model.getValueAt(current, 4)).equals("Вылетел")) {status = 1;} else {status = 0;}
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

            JButton update = new JButton("Обновить");
            update.setBounds(500, 320, 100, 35);
            update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePassengers(model);
                }
            });

            JButton delete = new JButton("Удалить");
            delete.setBounds(350, 320, 100, 35);
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        conDriver.deletePassenger(Integer.parseInt((String) model.getValueAt(table.getSelectedRow(), 5)));
                        JOptionPane.showMessageDialog(passengersMenu, "После изменения данных в таблице просьба нажать кнопку \" Обновить\"");
                        JOptionPane.showMessageDialog(passengersMenu, "Готово!");
                    } catch (SQLException throwables) {
                        JOptionPane.showMessageDialog(frame, "Ошибка! " + throwables);
                    }
                }
            });

            frame.add(delete);
            frame.add(update);
            frame.add(edit);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    public void getEmployeesList (ActionEvent event) {
        frame = new JFrame("Список работников");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getPassengersList();

            JTable table = new JTable();
            Object[] columnNames = {"Фамилия", "Имя", "Возраст", "Пол", "Дети", "ID", "ID отдела"};
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);

            table.setModel(model);
            table.setEnabled(true);
            table.setBackground(Color.gray);
            table.setForeground(Color.white);
            table.setRowHeight(20);

            Vector<String> sortList = new Vector<>();
            sortList.add("Пол");
            sortList.add("Возраст");
            sortList.add("Дети");
            sortList.add("Зарплата");
            JComboBox sort = new JComboBox<>(sortList);
            sort.setBounds(300, 10, 100, 30);

            JButton sortButton = new JButton("Сортировать");
            sortButton.setBounds(400, 10, 100, 30);
            //sortButton.addActionListener(this::sort);


            JScrollPane pane = new JScrollPane(table);

            pane.setBounds(0,50,800,250);
            pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            updateEmployees(model, 0);

            JButton edit = new JButton("Правка");
            edit.setBounds(650, 320, 100, 35);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int current = table.getSelectedRow();
                    int sex = 0;
                    if (((String) model.getValueAt(current, 3)).equals("Женский")) {sex = 1;} else {sex = 0;}
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

            JButton update = new JButton("Обновить");
            update.setBounds(500, 320, 100, 35);
            update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateEmployees(model, 0);
                }
            });

            JButton delete = new JButton("Удалить");
            delete.setBounds(350, 320, 100, 35);
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        conDriver.deleteEmployee(Integer.parseInt((String) model.getValueAt(table.getSelectedRow(), 5)));
                        JOptionPane.showMessageDialog(passengersMenu, "Готово!");
                        JOptionPane.showMessageDialog(passengersMenu, "После изменения данных в таблице просьба нажать кнопку \" Обновить\"");
                    } catch (SQLException throwables) {
                        JOptionPane.showMessageDialog(frame, "Ошибка! " + throwables);
                    }
                }
            });

            frame.add(sortButton);
            frame.add(sort);
            frame.add(delete);
            frame.add(update);
            frame.add(edit);
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {}
    }

    private void medInspectionMenu(ActionEvent e) {
        frame = new JFrame("Меню мед. работников");
        frame.setSize(620, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);

        JTable table = new JTable();
        Object[] columnNames = {"Фамилия", "Имя", "Возраст", "Пол", "ID"};
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

            JButton edit = new JButton("Новая мед. запись");
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

        JButton update = new JButton("Обновить");
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
        frame = new JFrame("Самолёты");
        frame.setSize(620, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);

        JTable table = new JTable();
        Object[] columnNames = {"ID Самолёта", "Тип самолёта", "Число ремонтов", "Последний ремонт", "Возраст", "Лётных часов"};
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

        JButton edit = new JButton("Новый самолёт");
        edit.setBounds(450, 320, 150, 35);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaneMenu menu = new PlaneMenu(conDriver);
            }
        });

        JButton update = new JButton("Обновить");
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
        frame = new JFrame("Персонал самолёта");
        frame.setSize(620, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setFocusable(true);

        JTable table = new JTable();
        Object[] columnNames = {"ID Работника", "ID самолёта", "Фамилия", "Имя", "Позиция"};
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

        JButton edit = new JButton("Сменить позицию");
        edit.setBounds(450, 320, 150, 35);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PlaneMenu menu = new PlaneMenu(conDriver);
            }
        });

        JButton update = new JButton("Обновить");
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
        JFrame developerMenu = new JFrame("Меню разработчика");
        developerMenu.setSize(300, 175);
        developerMenu.setLocationRelativeTo(null);
        developerMenu.setFocusable(true);
        developerMenu.setLayout(null);

        JButton dropTables = new JButton("Сброс таблиц");
        dropTables.setBounds(68, 15, 160, 35);
        developerMenu.add(dropTables);
        dropTables.addActionListener(this::dropTables);

        JButton createTables = new JButton("Создание таблиц");
        createTables.setBounds(68, 55, 160, 35);
        developerMenu.add(createTables);
        createTables.addActionListener(this::createTables);

        JButton fillTables = new JButton("Заполнение таблиц");
        fillTables.setBounds(68, 95, 160, 35);
        developerMenu.add(fillTables);
        fillTables.addActionListener(this::fillTables);

        developerMenu.setVisible(true);
    }

    private void openTechMenu(ActionEvent e) {
        JFrame technicalMenu = new JFrame("Меню тех. персонала");
        technicalMenu.setSize(300, 175);
        technicalMenu.setLocationRelativeTo(null);
        technicalMenu.setFocusable(true);
        technicalMenu.setLayout(null);

        JButton planeList = new JButton("Самолёты");
        planeList.setBounds(68, 15, 160, 35);
        technicalMenu.add(planeList);
        planeList.addActionListener(this::planeList);

        JButton planeEmployees = new JButton("Персонал самолётов");
        planeEmployees.setBounds(68, 55, 160, 35);
        technicalMenu.add(planeEmployees);
        planeEmployees.addActionListener(this::planeEmployeesList);

        JButton planeRepair = new JButton("Ремонт самолётов");
        planeRepair.setBounds(68, 95, 160, 35);
        technicalMenu.add(planeRepair);

        technicalMenu.setVisible(true);
    }

    private void openEmployeesMenu(ActionEvent e) {
        JFrame employeesMenu = new JFrame("Меню отдела кадров");
        employeesMenu.setSize(300, 150);
        employeesMenu.setLocationRelativeTo(null);
        employeesMenu.setFocusable(true);
        employeesMenu.setLayout(null);

        JButton addEmployee = new JButton("Новый работник");
        addEmployee.setBounds(68, 15, 160, 35);
        employeesMenu.add(addEmployee);
        addEmployee.addActionListener(this::addEmployee);

        JButton passengerList = new JButton("Список работников");
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
        passengersMenu = new JFrame("Меню паспортного контроля");
        passengersMenu.setSize(300, 150);
        passengersMenu.setLocationRelativeTo(null);
        passengersMenu.setFocusable(true);
        passengersMenu.setLayout(null);

        JButton addPassenger = new JButton("Новый пассажир");
        addPassenger.setBounds(68, 15, 160, 35);
        passengersMenu.add(addPassenger);
        addPassenger.addActionListener(this::addPassenger);

        JButton passengerList = new JButton("Список пассажиров");
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
            JOptionPane.showMessageDialog(frame, "Проблемы в скрипте!");
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
            JOptionPane.showMessageDialog(frame, "Проблемы в скрипте!");
        }
    }

    private void fillTables(ActionEvent e) {
        try {
            conDriver.fillTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Проблемы в скрипте!");
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
                    row[4] = "Вылетел";
                } else { row[4] = "Задержан"; }
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
                        row[3] = "Мужской";
                    } else {
                        row[3] = "Женский";
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
                        row[3] = "Мужской";
                    } else {
                        row[3] = "Женский";
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
                    row[4] = "Пилот";
                } else if (set.getString(5).equals("2")) {
                    row[4] = "Техник";
                } else {
                    row[4] = "Стюард";
                }
                model.addRow(row);
            }
            set.close();
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        }
    }
}
