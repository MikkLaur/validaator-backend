package server.controller;

import server.database.DatabaseWrapper;
import server.model.Stop;

public class StopController {
    public StopController() {

    }

    public long registerStop(String name) {
        return DatabaseWrapper.insertStop(name);
    }

    public String getAllStops() {
        return Stop.getAllJSON();
    }
}
