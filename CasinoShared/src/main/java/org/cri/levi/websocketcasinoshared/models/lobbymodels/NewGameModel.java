package org.cri.levi.websocketcasinoshared.models.lobbymodels;

import org.cri.levi.websocketcasinoshared.models.GameType;

import java.util.List;
import java.util.UUID;

public class NewGameModel implements LobbyOperationOption {

    private UUID id;
    private String gameName;
    private int maxAmountOfPlayers;
    private GameType gameType;
    private List<PlayerModel> joinedPlayers;


    public NewGameModel(UUID id, String gameName, int maxAmountOfPlayers, GameType gameType, List<PlayerModel> players) {
        this.id = id;
        this.gameName = gameName;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
        this.gameType = gameType;
        this.joinedPlayers = players;
    }

    public UUID getId() {
        return id;
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

    public List<PlayerModel> getJoinedPlayers() {
        return joinedPlayers;
    }
}
