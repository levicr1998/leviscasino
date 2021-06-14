package org.cri.levi.websocketcasinoserver.gamemanager;

import org.cri.levi.websocketcasinoshared.models.gamemodels.RouletteBetOption;

import java.util.UUID;

public class BetModel {

    private int playerID;

    public int getPlayerID() {
        return playerID;
    }

    public UUID getGameID() {
        return gameID;
    }

    public int getBet() {
        return bet;
    }

    public int getRouletteNumber() {
        return rouletteNumber;
    }

    private UUID gameID;
    private int bet;
    private int rouletteNumber;

    public RouletteBetOption getRouletteBetOption() {
        return rouletteBetOption;
    }

    private RouletteBetOption rouletteBetOption;

    public BetModel(int playerID, UUID gameID, int bet, int rouletteNumber) {
        this.playerID = playerID;
        this.gameID = gameID;
        this.bet = bet;
        this.rouletteNumber = rouletteNumber;
        this.rouletteBetOption = RouletteBetOption.NUMBER;
    }

    public BetModel(int playerID, UUID gameID, int bet, RouletteBetOption rouletteBetOption) {
        this.playerID = playerID;
        this.gameID = gameID;
        this.bet = bet;
        this.rouletteBetOption = rouletteBetOption;
    }

}
