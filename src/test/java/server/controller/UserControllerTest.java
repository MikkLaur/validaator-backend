package server.controller;

import org.junit.Test;
import server.database.DatabaseWrapper;

import static org.junit.Assert.*;

public class UserControllerTest {

    @Test
    public void registerUser() {
        UserController uc = new UserController();

        String name = "Mikk Laur";
        String personalId  = "39595959595";
        String dob = "1995-05-31";

        DatabaseWrapper.resetDatabase();
        assertEquals(1, uc.registerUser(name, personalId, dob));
    }


}