package server.controller;

import server.database.DatabaseWrapper;
import server.model.User;

import java.sql.Date;
import java.util.List;

public class UserController {
    public UserController() {

    }

    public long registerUser(String name, String personalID, String dateOfBirth) {
        Date dob = Date.valueOf(dateOfBirth);
        return DatabaseWrapper.insertUser(name, personalID, dob);
    }

    public String getAllUsers() {
        return User.getAllUsersJSON();
    }
}
