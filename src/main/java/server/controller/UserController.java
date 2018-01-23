package server.controller;

import org.json.JSONArray;
import server.model.User;

import java.sql.Date;
import java.util.List;

public class UserController extends Controller{
    public UserController() {

    }

    public long registerUser(String name, String personalID, String dateOfBirth) {
        Date dob = Date.valueOf(dateOfBirth);
        return database.insertUser(name, personalID, dob);
    }

    public String getAllUsers() {
        JSONArray jsonArray = new JSONArray();
        List<User> users = database.selectAllUsers();
        for (User user : users) {
            jsonArray.put(user.toJSONObject());
        }
        return jsonArray.toString();
    }
}
