package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConnectMenu {
    private JFrame frame;
    private ConnectionDriver conDriver;

    private JTextField ipTextField;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;

    public ConnectMenu(ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        frame = new JFrame("Подключение");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(290, 270);

        ipTextField = new JTextField(15);
        ipTextField.setToolTipText("IP адрес");
        ipTextField.setBounds(125, 10, 150, 25);
        JLabel ipLabel = new JLabel("DB IP адрес");
        ipLabel.setBounds(10, 10, 150, 25);
        loginTextField = new JTextField(15);
        loginTextField.setBounds(125, 40, 150, 25);
        JLabel loginLabel = new JLabel("DB пользователь");
        loginLabel.setBounds(10, 40, 150, 25);
        passwordTextField = new JPasswordField(15);
        passwordTextField.setBounds(125, 70, 150, 25);
        JLabel passwordLabel = new JLabel("DB пароль");
        passwordLabel.setBounds(10, 70, 150, 25);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(ipLabel);
        panel.add(ipTextField);
        panel.add(loginLabel);
        panel.add(loginTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);

        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButton adminButton = new JRadioButton("Администратор");
        adminButton.setBounds(10, 105, 150, 20);
        buttonGroup.add(adminButton);
        panel.add(adminButton);
        adminButton.setSelected(true);

        JRadioButton HRButton = new JRadioButton("Кадры");
        HRButton.setBounds(165, 105, 100, 20);
        buttonGroup.add(HRButton);
        panel.add(HRButton);

        JRadioButton medicButton = new JRadioButton("Мед. персонал");
        medicButton.setBounds(10, 135, 150, 20);
        buttonGroup.add(medicButton);
        panel.add(medicButton);

        JRadioButton passControlButton = new JRadioButton("Паспортный контроль");
        passControlButton.setBounds(55, 165, 190, 20);
        buttonGroup.add(passControlButton);
        panel.add(passControlButton);

        JRadioButton techControlButton = new JRadioButton("Тех. персонал");
        techControlButton.setBounds(165, 135, 150, 20);
        buttonGroup.add(techControlButton);
        panel.add(techControlButton);

        JButton connect = new JButton("Подклчение");
        connect.setBounds(25, 195, 100, 35);
        panel.add(connect);

        JButton connectStandart = new JButton("Подкл. к НГУ");
        connectStandart.setBounds(155, 195, 100, 35);
        panel.add(connectStandart);
        connectStandart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectStandart(adminButton.isSelected(), HRButton.isSelected(), medicButton.isSelected(), passControlButton.isSelected(), techControlButton.isSelected());
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect(adminButton.isSelected(), HRButton.isSelected(), medicButton.isSelected(), passControlButton.isSelected(), techControlButton.isSelected());
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void connect(boolean admin, boolean hr, boolean medic, boolean control, boolean technic) {
        try {
            boolean status = conDriver.connect(ipTextField.getText(), loginTextField.getText(), passwordTextField.getText());
            if (status) {
                frame.setVisible(false);
                if (admin) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 0);
                } else if (hr) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 1);
                } else if (medic) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 2);
                } else if (control) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 3);
                } else if (technic) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 4);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Bad connection");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Bad connection");
        }
    }

    public void connectStandart(boolean admin, boolean hr, boolean medic, boolean control, boolean technic) {
        try {
            boolean status = conDriver.connect("84.237.50.81", "18204_Timofeev", "mama02091969");
            if (status) {
                frame.setVisible(false);
                if (admin) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "18204_Timofeev", 0);
                } else if (hr) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "18204_Timofeev", 1);
                } else if (medic) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "18204_Timofeev", 2);
                } else if (control) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "18204_Timofeev", 3);
                } else if (technic) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "18204_Timofeev", 4);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Bad connection");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Bad connection");
        }
    }


}
