package server.controller;

import server.database.DatabaseWrapper;
import server.model.UserTransactionHistory;

public class TransactionController {
    public TransactionController() {

    }

    public long buyTicket(String userId, String stopId) {
        return DatabaseWrapper.insertTransaction(Long.parseLong(userId), Integer.parseInt(stopId));
    }


    /* Gets fully detailed user transaction history in JSON */
    public String getTransactionHistory(String userId) {
        return UserTransactionHistory.getAllJSON(Long.parseLong(userId));
    }
}
