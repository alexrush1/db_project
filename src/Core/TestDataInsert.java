package Core;

import java.sql.*;

public class TestDataInsert {

    public static void createTable(Statement statement) throws SQLException {
        String sql = "CREATE TABLE FLIGHT_SCHEDULE (" +
                "AIRCRAFT_ID NUMBER(5) NOT NULL," +
                "AIRCRAFT_TYPE VARCHAR(15) NOT NULL," +
                "FLIGHT_NUMBER VARCHAR(15) NOT NULL," +
                "DEPARTURE_DAYS VARCHAR(15) NOT NULL," +
                "DEPARTURE_TIME VARCHAR(10) NOT NULL," +
                "ARRIVAL_TIME VARCHAR(10) NOT NULL," +
                "DEPARTURE VARCHAR(5) NOT NULL," +
                "ARRIVAL VARCHAR(5) NOT NULL," +
                "TICKET_PRICE NUMBER NOT NULL," +
                "CONSTRAINT flight_shcedule_pk PRIMARY KEY (AIRCRAFT_ID))";
        statement.execute(sql);
    }

    public static void fillTable(Statement statement) {
        //String sql = "INSERT INTO FLIGHT_SCHEDULE";
    }
}
