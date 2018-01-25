package server.database;

import server.model.Stop;
import server.model.User;
import server.model.UserTransactionHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseWrapper {
    //private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private String DB_URL;

    private String USER;
    private String PASSWORD;

    // Used when a Controller is constructed
    public DatabaseWrapper() {
        this.DB_URL   = "jdbc:postgresql://localhost:5432/validaatordb";
        this.USER     = "testuser";
        this.PASSWORD = "test";
    }

    // Used for testing
    public DatabaseWrapper(String databaseUrl, String user, String password) {
        this.DB_URL   = "jdbc:postgresql://".concat(databaseUrl);
        this.USER     = user;
        this.PASSWORD = password;
    }

      ////////////////////////
     /* PREDEFINED QUERIES */
    ////////////////////////

    public List<UserTransactionHistory> selectUserTransactionHistory(long userId) {
        List<UserTransactionHistory> transactions = new ArrayList<>();
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn
                    .prepareStatement
                            ("SELECT transactions.ticket_nr, stops.name, transactions.date_added FROM transactions " +
                             "INNER JOIN stops ON transactions.stop_id = stops.id " +
                             "INNER JOIN users ON transactions.user_id = users.id " +
                             "WHERE users.id = (?) " +
                             "ORDER BY transactions.date_added DESC");
            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                transactions.add(new UserTransactionHistory(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDate(3)
                ));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // *** PS: storing every User in a List will incur scaling issues
    public List<Stop> selectAllStops() {
        List<Stop> stops = new ArrayList<>();
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM stops;");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stops.add(new Stop(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDate(3)
                ));
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stops;
    }

    // *** PS: storing every User in a List will incur scaling issues
    public List<User> selectAllUsers() {
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
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public long insertStop(String stopName) {
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

    public long insertTransaction(long userId, int stopId) {
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

    public long insertUser(String name, String personalId, Date dob) {
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

    // On success:
    //  Returns the id (first column value) of an Insert Query
    private long getInsertId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        return -1;
    }

    // Return an open connection to the DB
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
