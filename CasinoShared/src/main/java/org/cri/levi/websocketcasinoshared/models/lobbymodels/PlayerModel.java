package org.cri.levi.websocketcasinoshared.models.lobbymodels;


public class PlayerModel {

    public String getUsername() {
        return username;
    }

    public int getPlayerID() {
        return playerID;
    }

    private int playerID;
    private String username;

    public String getSessionID() {
        return sessionID;
    }

    private String sessionID;

    public PlayerModel(String username) {
        this.username = username;
    }

    public PlayerModel(String username, int playerID) {
        this.username = username;
        this.playerID = playerID;
    }

    public PlayerModel(String username, int playerID, String sessionID) {
        this.username = username;
        this.playerID = playerID;
        this.sessionID = sessionID;
    }
}
