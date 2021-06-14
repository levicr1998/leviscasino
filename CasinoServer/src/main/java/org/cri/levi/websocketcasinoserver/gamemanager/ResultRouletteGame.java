package org.cri.levi.websocketcasinoserver.gamemanager;

import java.util.UUID;

public class ResultRouletteGame {

    public UUID getGameID() {
        return gameID;
    }

    private UUID gameID;

    public int getRouletteNumber() {
        return rouletteNumber;
    }

    private int rouletteNumber;

    public ResultRouletteGame(UUID gameID) {
        this.gameID = gameID;
    }

    public void addRouletteNumber(int number) {
        this.rouletteNumber = number;
    }

}
