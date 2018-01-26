package server.controller;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

public class UserControllerTest {
    private UserController uc = new UserController();

    @Test
    public void registerUserTest() {
        String name = "im a name";
        String personalId = "im a personal id";
        String date = "1990-09-23";
        Assert.assertNotEquals(-1, uc.registerUser(name, personalId, date));
        Assert.assertEquals(-1, uc.registerUser(name, personalId, date));
    }

    @Test
    public void getAllJSONTest() {
        JSONArray jsonArray = new JSONArray(uc.getAllJSON());
        /* Reminder to myself. This is a bad way of testing.
         * Basically, i know there will be 11 users on the db during test task.
         * Each test should be cleaning up after itself
         * TODO: Implement something better. */
        Assert.assertEquals(11, jsonArray.length());

        Assert.assertNotEquals(0, jsonArray.length());

        // Hopefully this helps me get through the refactor
    }
}