package server.controller;

import server.database.DatabaseWrapper;

public class TransactionController {
    public TransactionController() {

    }

    public long buyTicket(String userId, String stopId) {
        long userId_L = Long.parseLong(userId);
        int stopId_Int = Integer.parseInt(stopId);
        return DatabaseWrapper.insertTransaction(userId_L, stopId_Int);
    }
}
