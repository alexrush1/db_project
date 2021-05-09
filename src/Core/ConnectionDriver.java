package Core;

import Lib.ScriptRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class ConnectionDriver {
    Connection connection;
    Statement statement;

    ClassLoader cl;

    public ConnectionDriver() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        cl = this.getClass().getClassLoader();
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

    public ResultSet getEmployeesList() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT * FROM EMPLOYEES";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void createTables() throws IOException, SQLException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        System.out.println(System.getProperty("user.dir"));
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream("Scripts/Create_tables")));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void createRoles() throws IOException, SQLException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        System.out.println(System.getProperty("user.dir"));
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream("Scripts/Roles_creator")));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void dropTables() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream("Scripts/Drop_tables")));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void fillTables() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(connection, true, false);
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream("Scripts/Fill_tables")));
        try {
            runner.runScript(reader);
        } catch (Exception e) {}
    }

    public void commitPassenger(int id, String firstName, String lastName, String documentId, String flightNumber, int status) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO PASSENGERS VALUES (" + id + ", '" + firstName + "', '" + lastName + "', " + Integer.parseInt(documentId) + ", '" + flightNumber +
                "', " + status + ")";
        System.out.println(sql);
        statement.execute(sql);
    }

    public void updatePassenger(String firstName, String lastName, String documentId, String flightNumber, int status, int id) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "UPDATE PASSENGERS SET FIRST_NAME='" + firstName + "', LAST_NAME='" + lastName + "', DOCUMENT_ID=" + Integer.parseInt(documentId) + ", FLIGHT_NUMBER='" + flightNumber +
                "', STATUS=" + status + " WHERE ID = " + id;
        //sql = "UPDATE PASSENGERS SET FIRST_NAME='" + firstName + "' WHERE ID = " + id + ";";
        System.out.println(sql);
        statement.executeQuery(sql);
    }

    public void commitEmployee(int id, String firstName, String lastName, String age, String children, String departmentId, int sex) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO PASSENGERS VALUES (" + id + ", '" + firstName + "', '" + lastName + "', " + Integer.parseInt(age) + ", " + age +
                ", " + children + ", " + Integer.parseInt(departmentId) + ")";
        System.out.println(sql);
        statement.execute(sql);
    }

    public int countPassengers() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT COUNT(*) FROM PASSENGERS";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getInt(1);
    }

    public int countEmployees() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT COUNT(*) FROM EMPLOYEES";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getInt(1);
    }

    public void updateEmployee(int id, String firstName, String lastName, String age, String children, String departmentId, int sex) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "UPDATE EMPLOYEES SET FIRST_NAME='" + firstName + "', LAST_NAME='" + lastName + "', DEPARTMENT_ID=" + Integer.parseInt(departmentId) + ", AGE='" + age +
                "', SEX=" + sex + ", CHILDREN=" + children + " WHERE EMPLOYEE_ID = " + id;
        //sql = "UPDATE PASSENGERS SET FIRST_NAME='" + firstName + "' WHERE ID = " + id + ";";
        System.out.println(sql);
        statement.executeQuery(sql);
    }

    public void commitInspection(int id, String emloyeeId, int status) throws SQLException {
        statement = connection.createStatement();
        String sql;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println(LocalDate.now().format(formatter));
        String date = LocalDate.now().format(formatter);
        sql = "INSERT INTO MED_INSPECTION VALUES (" + id + ", " + emloyeeId + ", to_date('" + date + "' , 'yyyyMMdd'), " + status + ")";
        System.out.println(sql);
        statement.executeQuery(sql);
    }

    public int countInspections() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT COUNT(*) FROM MED_INSPECTION";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getInt(1);
    }

    public ResultSet getPlanesList() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT * FROM PLANE";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void commitPlane(String planeId, String aircraftType, String numRepairs, String lastRepair, String age, String flightHours) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO PLANE VALUES ('" + planeId + "', '" + aircraftType + "', '" + numRepairs + "', to_date('" + lastRepair + "' , 'ddmmyyyy'), '" + age + "', '" + flightHours +
                "')";
        System.out.println(sql);
        statement.execute(sql);
    }

    public void createUser(String username, String password, int role) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "CREATE USER " + username +
                " IDENTIFIED BY " + password +
                " DEFAULT TABLESPACE USERS" +
                " TEMPORARY TABLESPACE TEMP";
        System.out.println(sql);
        statement.execute(sql);

        sql = "GRANT connect to " + username;
        System.out.println(sql);
        statement.execute(sql);

        if (role == 0) {
            sql = "GRANT hr_t4 to " + username;
        } else if (role == 1) {
            sql = "GRANT medic_t4 to " + username;
        } else if (role == 2) {
            sql = "GRANT tech_t4 to " + username;
        } else if (role == 3) {
            sql = "GRANT control_t4 to " + username;
        }
        System.out.println(sql);
        statement.execute(sql);
    }

    public ResultSet getPlanePositions() throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "SELECT EMPLOYEE_ID, PLANE_ID, FIRST_NAME, LAST_NAME, POSITION FROM EMPLOYEES " +
                "INNER JOIN PLANE_EMPLOYEES " +
                "USING (EMPLOYEE_ID)";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void deletePassenger(int id) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "DELETE FROM PASSENGERS WHERE ID = " + id;
        System.out.println(sql);
        statement.executeQuery(sql);
    }

    public void deleteEmployee(int id) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = " + id;
        System.out.println(sql);
        statement.executeQuery(sql);
    }

}
