package Core;

import Lib.ScriptRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public ResultSet getPassengersList() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT * FROM PASSENGERS";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void createTables() throws IOException, SQLException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        System.out.println(System.getProperty("user.dir"));
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/Scripts/Create_tables"));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void dropTables() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/Scripts/Drop_tables"));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void fillTables() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/Scripts/Fill_tables"));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void commitPassenger(String firstName, String lastName, String documentId, String flightNumber, int status) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO PASSENGERS VALUES ('" + firstName + "', '" + lastName + "', " + Integer.parseInt(documentId) + ", '" + flightNumber +
                "', " + status + ")";
        System.out.println(sql);
        statement.execute(sql);
    }

    public void updatePassenger(String firstName, String lastName, String documentId, String flightNumber, int status, int id) throws SQLException {
        statement = connection.createStatement();
        String sql;
        //sql = "UPDATE PASSENGERS SET FIRST_NAME='" + firstName + "', LAST_NAME='" + lastName + "', DOCUMENT_ID=" + Integer.parseInt(documentId) + ", FLIGHT_NUMBER='" + flightNumber +
        //        "', STATUS=" + status + " WHERE ID = " + id + ";";
        sql = "UPDATE PASSENGERS SET FIRST_NAME='" + firstName + "' WHERE ID = " + id + ";";
        System.out.println(sql);
        statement.executeQuery(sql);
    }

}
