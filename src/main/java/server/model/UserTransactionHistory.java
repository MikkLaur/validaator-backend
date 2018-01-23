package server.model;

import org.json.JSONObject;
import java.sql.Date;

public class UserTransactionHistory {
    private long ticketNr;
    private String stopName;
    private Date transactionDate;

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
