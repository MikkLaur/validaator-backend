package server.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.model.Stop;

import java.lang.reflect.*;
import java.sql.Date;

import static org.junit.Assert.*;

public class DatabaseWrapperTest {
    private String DB_URL   = "localhost:5432/validaatordb";
    private String USER     = "testuser";
    private String PASSWORD = "test";

    private DatabaseWrapper database = new DatabaseWrapper(DB_URL, USER, PASSWORD);

    /////
    // Insertion tests
    //   When an insertion fails, it returns -1.
    /////
    @Test
    public void insertUser() {
        String[] user = new String[] { "Vello", "39003202222", "1990-03-20" };
        Assert.assertNotEquals(-1, database.insertUser(user[0], user[1], Date.valueOf(user[2])));
        // Insert the same user again
        Assert.assertEquals(-1, database.insertUser(user[0], user[1], Date.valueOf(user[2])));
    }

    @Test
    public void insertStopTest() {
        Assert.assertNotEquals(-1, database.insertStop("imastop"));
    }

    @Test
    public void insertTransaction() {
        Assert.assertNotEquals(-1, database.insertTransaction(1,1));
        Assert.assertEquals(-1, database.insertTransaction(0, 0));
    }
}