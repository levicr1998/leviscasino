package org.cri.levi.websocketcasinoshared.models.lobbymodels;


import java.util.Random;
import java.util.UUID;

public class NumberGeneratorModel implements LobbyOperationOption {
    public NumberGeneratorModel(UUID gameID) {
        this.gameID = gameID;
        random = new Random();
        this.rouletteNumber = random.nextInt(37);
    }

    public int getRouletteNumber() {
        return rouletteNumber;
    }

    public UUID getGameID() {
        return gameID;
    }

    private UUID gameID;
    private int rouletteNumber;
    private static Random random;

    public NumberGeneratorModel() {
        random = new Random();
        this.rouletteNumber = random.nextInt(37);
    }
}
