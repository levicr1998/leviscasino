package org.cri.levi.websocketcasinoshared.models;

import java.util.UUID;

public class Player {
    public int getId() {
        return id;
    }

    private int id;

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionID;
    }

    public UUID getGameID() {
        return gameID;
    }

    public void setGameID(UUID gameID) {
        this.gameID = gameID;
    }

    private UUID gameID;

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    private String sessionID;

    private String username;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private int balance = 0;

    public Player() {

    }

    public Player(int id, String username, int balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Player(int id, String username, int balance, String sessionID) {
        this.id = id;
        this.username = username;
        this.balance = balance;
        this.sessionID = sessionID;
    }

}
