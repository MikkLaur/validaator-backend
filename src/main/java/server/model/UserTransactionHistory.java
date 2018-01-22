package server.model;

import org.json.JSONArray;
import org.json.JSONObject;
import server.database.DatabaseWrapper;

import java.sql.Date;
import java.util.List;

public class UserTransactionHistory {
    long ticketNr;
    String stopName;
    Date transactionDate;

    public UserTransactionHistory(long ticketNr, String stopName, Date transactionDate) {
        this.ticketNr = ticketNr;
        this.stopName = stopName;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        return new JSONObject()
                .put(Long.toString(this.getTicketNr()), new JSONObject()
                    .put("stop_name", this.getStopName())
                    .put("date_added", this.getTransactionDate()));
    }

    public static String getAllJSON(long userId) {
        JSONArray jsonArray = new JSONArray();
        List<UserTransactionHistory> historyList = DatabaseWrapper.selectUserTransactionHistory(userId);
        for (UserTransactionHistory uth : historyList) {
            jsonArray.put(uth.toJSONObject());
        }
        return jsonArray.toString();
    }

    public long getTicketNr() {
        return ticketNr;
    }

    public void setTicketNr(long ticketNr) {
        this.ticketNr = ticketNr;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
