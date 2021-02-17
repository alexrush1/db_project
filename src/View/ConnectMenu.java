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
        frame.setSize(300, 180);

        ipTextField = new JTextField(15);
        ipTextField.setToolTipText("IP address");
        ipTextField.setBounds(125, 25, 150, 25);
        JLabel ipLabel = new JLabel("DB IP address");
        loginTextField = new JTextField(15);
        JLabel loginLabel = new JLabel("DB Login          ");
        passwordTextField = new JPasswordField(15);
        JLabel passwordLabel = new JLabel("DB Password ");

        JPanel panel = new JPanel(); // the panel is not visible in output
        var layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        panel.add(ipLabel);
        panel.add(ipTextField);
        panel.add(loginLabel);
        panel.add(loginTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);

        JButton connect = new JButton("Connect");
        connect.setBounds(125, 225, 150, 50);
        panel.add(connect);
        connect.addActionListener(this::connect);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void connect(ActionEvent event) {
        try {
            var status = conDriver.connect(ipTextField.getText(), loginTextField.getText(), passwordTextField.getText());
            if (status) {
                frame.setVisible(false);
                WorkingMenu workingMenu = new WorkingMenu(conDriver);
            }
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(frame, "Connection error");
            e.getErrorCode();
        }
    }


}
