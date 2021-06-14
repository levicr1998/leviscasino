package org.cri.levi.websocketcasinoshared.models.gamemodels;

public class ProfitModel implements GameOperationOption {

    private int playerID;

    public int getBalance() {
        return balance;
    }

    private int balance;

    public int getProfit() {
        return profit;
    }

    private int profit;

    public ProfitModel(int playerID, int balance) {
        this.playerID = playerID;
        this.balance = balance;
    }

    public ProfitModel(int playerID, int balance, int profit) {
        this.playerID = playerID;
        this.balance = balance;
        this.profit = profit;
    }
}
