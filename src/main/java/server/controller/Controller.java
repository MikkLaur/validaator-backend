package server.controller;

import server.database.DatabaseWrapper;

public interface Controller {
    DatabaseWrapper database = new DatabaseWrapper();
}
