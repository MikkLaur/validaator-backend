package server.controller;

import server.database.DatabaseWrapper;

public abstract class Controller  {
    static public DatabaseWrapper database = new DatabaseWrapper();
}
