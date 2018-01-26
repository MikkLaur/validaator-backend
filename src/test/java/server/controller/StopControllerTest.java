package server.controller;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

public class StopControllerTest {
    private StopController sc = new StopController();

    @Test
    public void getAllJSONTest() {
        JSONArray jsonArray = new JSONArray(sc.getAllJSON());
        /* Reminder to myself. This is a bad way of testing.
         * Basically, i know there will be 7 stops on the db during test task.
         * Each test should be cleaning up after itself
         * TODO: Implement something better. */
        Assert.assertEquals(7, jsonArray.length());

        Assert.assertNotEquals(0, jsonArray.length());

        // Hopefully this helps me get through the refactor
    }
}
