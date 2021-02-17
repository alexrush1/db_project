package View;

import Core.ConnectionDriver;

import javax.swing.*;

public class WorkingMenu {
    private ConnectionDriver conDriver;
    JFrame frame;

    public WorkingMenu(ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}
