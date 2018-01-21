package server.database;

import server.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseWrapper {
    //private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb";

    private static final String USER = "tester";
    private static final String PASSWORD = "javapsql";


    private DatabaseWrapper() {

    }

    public static void main(String[] args) {
        //DatabaseWrapper db = new DatabaseWrapper();
        selectAllUsers();
    }

    public static void rawQuery(String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null)     rs.close(); } catch (Exception e) { e.printStackTrace();}
            try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace();}
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace();}
        }
    }

      ////////////////////////
     /* PREDEFINED QUERIES */
    ////////////////////////

    public static List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getDate(5)
                ));
                System.out.println(users.get(users.size()-1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static long insertStop(String stopName) {
        long stopId = -1;
        try {
            Connection conn = connect();
            // Statement.RETURN_GENERATED_KEYS makes the newly created row accessible from pstmt.getGeneratedKeys()
            PreparedStatement pstmt = conn.
                    prepareStatement("INSERT INTO stops (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, stopName);
            pstmt.executeUpdate();

            /* Get the stopId for return */
            stopId = getInsertId(pstmt.getGeneratedKeys());
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopId;
    }

    public static long insertTransaction(long userId, int stopId) {
        long ticketNr = -1;
        try {
            Connection conn = connect();
            // Statement.RETURN_GENERATED_KEYS makes the newly created row accessible from pstmt.getGeneratedKeys()
            PreparedStatement pstmt = conn.
                    prepareStatement("INSERT INTO transactions (user_id, stop_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, userId);
            pstmt.setInt(2, stopId);
            pstmt.executeUpdate();

            /* Get the ticketNr for return */
            ticketNr = getInsertId(pstmt.getGeneratedKeys());
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketNr;
    }

    public static long insertUser(String name, String personalId, Date dob) {
        long userId = -1;
        try {
            Connection conn = connect();
            // Statement.RETURN_GENERATED_KEYS makes the newly created row accessible from pstmt.getGeneratedKeys()
            PreparedStatement pstmt = conn.
                    prepareStatement("INSERT INTO users (name, personal_id, date_of_birth) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, personalId);
            pstmt.setDate(3, dob);
            pstmt.executeUpdate();

            /* Get the user_id for return */
            userId = getInsertId(pstmt.getGeneratedKeys());
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    // Returns the id (first column value) of an Insert Query
    private static long getInsertId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        return -1;
    }

      /////////////////////
     /* PRIVATE METHODS */
    /////////////////////

    // Create the tables necessary
    public static void createTables() {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String query = "CREATE TABLE users (" +
                    "id            SERIAL    PRIMARY KEY," +
                    "name          TEXT      NOT NULL," +
                    "personal_id   TEXT      NOT NULL UNIQUE," +
                    "date_of_birth DATE      NOT NULL," +
                    "date_added    TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

            query += "CREATE TABLE stops (" +
                    "id     SERIAL  PRIMARY KEY," +
                    "name   TEXT    NOT NULL" +
                    ");";

            query += "CREATE TABLE transactions (" +
                    "ticket_nr      SERIAL PRIMARY KEY," +
                    "user_id        SERIAL REFERENCES users (id)," +
                    "stop_id        SERIAL REFERENCES stops (id)," +
                    "date_added     TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";


            stmt.executeUpdate(query);
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTables() {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String query = "TRUNCATE TABLE transactions CASCADE;" +
                    "TRUNCATE TABLE stops CASCADE;" +
                    "TRUNCATE TABLE users CASCADE;";

            stmt.executeUpdate(query);
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTables() {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String query = "DROP TABLE transactions CASCADE;" +
                    "DROP TABLE stops CASCADE;" +
                    "DROP TABLE users CASCADE;";

            stmt.executeUpdate(query);
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetDatabase() {
        dropTables();
        createTables();
    }

    // Return an open connection to the DB
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Close the connection to the DB
    private static void disconnect(Connection conn) throws SQLException {
        conn.close();
    }
}
