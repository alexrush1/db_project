package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ConnectMenu {
    private JFrame frame;
    private ConnectionDriver conDriver;

    private JTextField ipTextField;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;

    public ConnectMenu(ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        frame = new JFrame("Connection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 190);

        ipTextField = new JTextField(15);
        ipTextField.setToolTipText("IP address");
        ipTextField.setBounds(125, 10, 150, 25);
        JLabel ipLabel = new JLabel("DB IP address");
        ipLabel.setBounds(10, 10, 150, 25);
        loginTextField = new JTextField(15);
        loginTextField.setBounds(125, 40, 150, 25);
        JLabel loginLabel = new JLabel("DB Login");
        loginLabel.setBounds(10, 40, 150, 25);
        passwordTextField = new JPasswordField(15);
        passwordTextField.setBounds(125, 70, 150, 25);
        JLabel passwordLabel = new JLabel("DB Password ");
        passwordLabel.setBounds(10, 70, 150, 25);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(ipLabel);
        panel.add(ipTextField);
        panel.add(loginLabel);
        panel.add(loginTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);

        JButton connect = new JButton("Connect");
        connect.setBounds(35, 105, 100, 35);
        panel.add(connect);

        JButton connectStandart = new JButton("Auto login");
        connectStandart.setBounds(165, 105, 100, 35);
        panel.add(connectStandart);
        connectStandart.addActionListener(this::connectStandart);

        connect.addActionListener(this::connect);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void connect(ActionEvent event) {
        try {
            boolean status = conDriver.connect(ipTextField.getText(), loginTextField.getText(), passwordTextField.getText());
            if (status) {
                frame.setVisible(false);
                WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText());
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    public void connectStandart(ActionEvent event) {
        try {
            boolean status = conDriver.connect("84.237.50.81", "18204_Timofeev", "mama02091969");
            if (status) {
                frame.setVisible(false);
                WorkingMenu workingMenu = new WorkingMenu(conDriver, "18204_Timofeev");
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }


}
