package server.controller;

import org.json.JSONArray;
import server.database.DatabaseWrapper;
import server.model.UserTransactionHistory;

import java.util.List;

public class TransactionController extends Controller {
    public TransactionController() {

    }

    public long buyTicket(String userId, String stopId) {
        return database.insertTransaction(Long.parseLong(userId), Integer.parseInt(stopId));
    }


    /* Gets fully detailed user transaction history in JSON */
    public String getAllUserJSON(String userId) {
        JSONArray jsonArray = new JSONArray();
        List<UserTransactionHistory> historyList =
                database.selectUserTransactionHistory(Long.parseLong(userId));
        for (UserTransactionHistory uth : historyList) {
            jsonArray.put(uth.toJSONObject());
        }
        return jsonArray.toString();
    }
}
