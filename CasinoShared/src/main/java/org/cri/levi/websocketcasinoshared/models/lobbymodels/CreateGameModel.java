package org.cri.levi.websocketcasinoshared.models.lobbymodels;

import org.cri.levi.websocketcasinoshared.models.GameType;

import java.util.UUID;

public class CreateGameModel implements LobbyOperationOption {
    public UUID getId() {
        return id;
    }

    private UUID id;
    private String gameName;
    private int maxAmountOfPlayers;
    private GameType gameType;

    public CreateGameModel() {
    }

    public CreateGameModel(String gameName, int maxAmountOfPlayers, GameType gameType) {
        this.gameName = gameName;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
        this.gameType = gameType;
    }

    public String getGameName() {
        return gameName;
    }

    public int getMaxAmountOfPlayers() {
        return maxAmountOfPlayers;
    }

    public GameType getGameType() {
        return gameType;
    }
}
