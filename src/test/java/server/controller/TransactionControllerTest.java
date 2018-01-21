package server.controller;

import org.junit.Test;
import server.database.DatabaseWrapper;

import java.sql.Date;

import static org.junit.Assert.*;

public class TransactionControllerTest {

    @Test
    public void buyTicket() {
        DatabaseWrapper.resetDatabase();
        long userid = DatabaseWrapper.insertUser("Mikk Laur", "39505312733", Date.valueOf("1995-05-31"));
        long stopId = DatabaseWrapper.insertStop("Jaama");

        TransactionController tc = new TransactionController();

        assertEquals(1, tc.buyTicket(String.valueOf(userid), String.valueOf(stopId)));
    }
}