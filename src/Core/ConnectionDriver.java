package Core;

import java.sql.*;
import java.util.Locale;

public class ConnectionDriver {
    Connection connection;
    Statement statement;

    public ConnectionDriver() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }

    public boolean connect(String ip, String login, String password) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        String url = "jdbc:oracle:thin:@"+ip+":1521:XE";
        System.out.printf("url: " + url + "\n" + "login: " + login + "\npassword: " + password + "\nconnecting... ");
        connection = DriverManager.getConnection(url, login, password);
        if (connection != null) {
            System.out.println("done!");
            return true;
        } else {
            System.out.println("failed!");
            return false;
        }
        //statement = connection.createStatement();
        //String sql;
        //sql = "SELECT * FROM developers";
        //ResultSet resultSet = statement.executeQuery(sql);
    }
}
