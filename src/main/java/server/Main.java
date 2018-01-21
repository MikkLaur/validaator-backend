package server;

import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.StopController;
import server.controller.TransactionController;
import server.controller.UserController;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        StopController stopController = new StopController();
        TransactionController transactionController = new TransactionController();

        post( "/api/users", "application/json", (request, response) -> {
            JSONObject json = new JSONObject(request.body());
            String name         = json.getString("name");
            String personalID   = json.getString("personal_id");
            String dateOfBirth  = json.getString("date_of_birth");

            return userController.registerUser(name, personalID, dateOfBirth);
        });

        post( "/api/stops", "application/json",(request, response) -> {
            JSONObject json = new JSONObject(request.body());
            String name = json.getString("name");

            return stopController.registerStop(name);
        });

        post( "/api/tickets", "application/json",(request, response) -> {
            JSONObject json = new JSONObject(request.body());
            String userId = json.getString("user_id");
            String stopId = json.getString("stop_id");

            return "ticket_nr";
        });
    }
}