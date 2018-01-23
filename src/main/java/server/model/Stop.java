package server.model;

import org.json.JSONArray;
import org.json.JSONObject;
import server.database.DatabaseWrapper;

import java.sql.Date;
import java.util.List;

public class Stop {
    private long id;
    private String name;
    private Date dateAdded;

    public Stop(long id, String name, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        return new JSONObject()
                .put(Long.toString(this.getId()), new JSONObject()
                        .put("name", this.getName())
                        .put("date_added", this.getDateAdded()));
    }

    public static String getAllJSON() {
        JSONArray jsonArray = new JSONArray();
        List<Stop> stops = DatabaseWrapper.selectAllStops();
        for (Stop stop : stops) {
            jsonArray.put(stop.toJSONObject());
        }
        return jsonArray.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
