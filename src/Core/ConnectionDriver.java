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
        connection = DriverManager.getConnection(url, login, password);
        if (connection != null) {
            return true;
        } else {
            return false;
        }
    }

    public ResultSet getListTables() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT * FROM FLIGHT_SCHEDULE";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

}
