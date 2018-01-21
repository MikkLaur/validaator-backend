package server.controller;

import server.database.DatabaseWrapper;

public class StopController {
    public StopController() {

    }

    public long registerStop(String name) {
        return DatabaseWrapper.insertStop(name);
    }
}
