package server;

import org.json.JSONObject;
import server.controller.StopController;
import server.controller.TransactionController;
import server.controller.UserController;

import static spark.Spark.*;

// TODO: Authentication

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        StopController stopController = new StopController();
        TransactionController transactionController = new TransactionController();

        get("/api/users",  (request, response) -> {
            /*
             *  Returns all users in a JSONArray format.
             *    [ "#getId()": {
             *          "name":          "#getName()".
             *          "personal_id":   "#getPersonalId()",
             *          "date_of_birth": "#getDateOfBirth()",
             *          "date_added":    "#getDateAdded()"
             *          },
             *      ...
             *    ]
             */
            return userController.getAllUsers();
        });

        post( "/api/users", "application/json", (request, response) -> {
            /*
             *  Adds a user on the database.
             *    Accepts a json in format:
             *    {
             *        "name":          "$name",
             *        "personal_id":   "$personalId",
             *        "date_of_birth": "$dateOfBirth"
             *    }
             *
             *    Upon success, returns the $id (database id) of the new user.
             *    Upon failure, returns -1.
             *      Fails when:
             *        - A user with a same personal_id already exists on the DB
             *        - TODO: validate personal_id with a regex pattern (ex. https://ipsec.pl/data-protection/2012/european-personal-data-regexp-patterns.html)
             */
            JSONObject json = new JSONObject(request.body());
            String name         = json.getString("name");
            String personalID   = json.getString("personal_id");
            String dateOfBirth  = json.getString("date_of_birth");

            return userController.registerUser(name, personalID, dateOfBirth);
        });

        get("/api/stops",  (request, response) -> {
            /*
             *  Returns all stops in a JSONArray format.
             *    [ "#getId()": {
             *          "name":          "$name".
             *          "date_added":    "$dateAdded"
             *          },
             *      ...
             *    ]
             */
            return stopController.getAllJSON();
        });

        post( "/api/stops", "application/json",(request, response) -> {
            /*
             *  Adds a stop on the database.
             *    Accepts a json in format:
             *    {
             *        "name":          "$name",
             *        "personal_id":   "$personalId",
             *        "date_of_birth": "$dateOfBirth",
             *    }
             *
             *    Upon success, returns the $id of the new stop.
             *    Upon failure, returns -1.
             *      Fails when:
             *        - No constraints! There may be multiple stops with the same name.
             */
            JSONObject json = new JSONObject(request.body());
            String name = json.getString("name");

            return stopController.registerStop(name);
        });

        post( "/api/tickets", "application/json",(request, response) -> {
            /*
             *  Registers a ticket validation on the database
             *    Accepts a json in format:
             *    {
             *        "user_id":  "$user_id",
             *        "stop_id":  "$stop_id",
             *    }
             *
             *    Upon success, returns the $ticketNr of the new ticket.
             *    Upon failure, returns -1
             *      Fails when:
             *        -
             */
            JSONObject json = new JSONObject(request.body());
            String userId = json.getString("user_id");
            String stopId = json.getString("stop_id");

            return transactionController.buyTicket(userId, stopId);
        });

        get( "/api/user/:id/tickethistory", (request, response) -> {
            /*
             *  Returns user's ticket validation history in detail, in json array.
             *    [ "$ticketNr": {
             *          "stop_name":     "$stopName".
             *          "date_added":    "$dateAdded"
             *          },
             *      ...
             *    ]
             *
             *    Upon failure, returns ""
             *      Fails when:
             *        - user with :id does not exist
             */
            String userId = request.params(":id");
            return transactionController.getAllUserJSON(userId);
        });

    }
}