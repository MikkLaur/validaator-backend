package server.controller;

import org.json.JSONArray;
import server.model.Stop;

import java.util.List;

public class StopController implements Controller {
    public StopController() {

    }

    public long registerStop(String name) {
        return database.insertStop(name);
    }

    public String getAllJSON() {
        JSONArray jsonArray = new JSONArray();
        List<Stop> stops = database.selectAllStops();
        for (Stop stop : stops) {
            jsonArray.put(stop.toJSONObject());
        }
        return jsonArray.toString();
    }
}
