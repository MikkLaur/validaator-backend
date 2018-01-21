package server.model;

import java.util.List;

abstract public class Model {
    //private final String DB_URL = "jdbc:postgresql://localhost:5432/testdb";

    //private static final String USER = "tester";
    //private static final String PASSWORD = "javapsql";

    abstract List<Model> selectAll();
    abstract Model selectById(long id);
    abstract Model insert();

}
