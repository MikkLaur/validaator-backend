package server.model;

import org.json.JSONArray;
import org.json.JSONObject;
import server.database.DatabaseWrapper;

import java.sql.Date;
import java.util.List;

public class User {
    private long id;
    private String name;
    private String personalId;
    private Date dateOfBirth;
    private Date dateAdded;

    public User(long id, String name, String personalId, Date dateOfBirth, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.personalId = personalId;
        this.dateOfBirth = dateOfBirth;
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
                        .put("personal_id", this.getPersonalId())
                        .put("date_of_birth", this.getDateOfBirth())
                        .put("date_added", this.getDateAdded()));
    }

    public static String getAllUsersJSON() {
        JSONArray jsonArray = new JSONArray();
        List<User> users = DatabaseWrapper.selectAllUsers();
        for (User user : users) {
            jsonArray.put(user.toJSONObject());
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

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
