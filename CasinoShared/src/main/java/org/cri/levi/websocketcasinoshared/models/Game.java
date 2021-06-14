package org.cri.levi.websocketcasinoshared.models;

import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    public UUID getId() {
        return id;
    }

    private UUID id;

    public String getGameName() {
        return gameName;
    }

    public int getMaxAmountOfPlayers() {
        return maxAmountOfPlayers;
    }

    private String gameName;
    private int maxAmountOfPlayers;

    public List<PlayerModel> getPlayers() {
        return players;
    }

    private List<PlayerModel> players;

    public Game() {
        this.maxAmountOfPlayers = 20;
        players = new ArrayList<>();
    }

    public Game(String gameName) {
        this.gameName = gameName;
        this.maxAmountOfPlayers = 20;
        players = new ArrayList<>();
        id = UUID.randomUUID();
    }

    public Game(UUID id, String gameName, int maxAmountOfPlayers, List<PlayerModel> players) {
        this.id = id;
        this.gameName = gameName;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
        this.players = players;
    }

    public void addPlayer(PlayerModel player) {
        players.add(player);
    }


}
