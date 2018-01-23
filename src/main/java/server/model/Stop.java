package server.model;

import org.json.JSONObject;

import java.sql.Date;

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
