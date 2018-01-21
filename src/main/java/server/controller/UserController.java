package server.controller;

import org.json.JSONObject;
import server.database.DatabaseWrapper;
import server.model.User;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class UserController {
    public UserController() {

    }

    public long registerUser(String name, String personalID, String dateOfBirth) {
        Date dob = Date.valueOf(dateOfBirth);
        return DatabaseWrapper.insertUser(name, personalID, dob);
    }

    public String getAllUsers() {
        JSONObject json = new JSONObject();
        List<User> users = DatabaseWrapper.selectAllUsers();

        return "";
    }
}
