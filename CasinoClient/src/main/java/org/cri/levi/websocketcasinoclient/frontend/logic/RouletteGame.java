package org.cri.levi.websocketcasinoclient.frontend.logic;

import javafx.scene.control.Label;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;

import java.util.List;
import java.util.UUID;

public class RouletteGame {

    private String gameName;
    private int maxAmountOfPlayers;
    private UUID id;
    private List<PlayerModel> players;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



    public String getGameName() {
        return gameName;
    }

    public int getMaxAmountOfPlayers() {
        return maxAmountOfPlayers;
    }


    public List<PlayerModel> getPlayers() {
        return players;
    }

    public RouletteGame(UUID id, String gameName, int maxAmountOfPlayers, List<PlayerModel> players) {
        this.id = id;
        this.gameName = gameName;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
        this.players = players;
    }


    public void changeRouletteNumber(int number, Label lNumberOutcome) {
        if (number == 0) {
            lNumberOutcome.setStyle("-fx-background-color: green; -fx-border-color:white;");
        }
        if (number > 0 && number <= 9) {
            if (number % 2 == 0) {
                lNumberOutcome.setStyle("-fx-background-color: black; -fx-border-color:white;");
            } else {
                lNumberOutcome.setStyle("-fx-background-color: red; -fx-border-color:white;");
            }
        } else if (number > 9 && number <= 18) {
            if (number % 2 != 0 || number == 10) {
                lNumberOutcome.setStyle("-fx-background-color: black; -fx-border-color:white;");
            } else {
                lNumberOutcome.setStyle("-fx-background-color: red; -fx-border-color:white;");
            }
        } else if (number > 18 && number <= 27) {
            if (number % 2 == 0) {
                lNumberOutcome.setStyle("-fx-background-color: black; -fx-border-color:white;");
            } else {
                lNumberOutcome.setStyle("-fx-background-color: red; -fx-border-color:white;");
            }
        } else if (number > 27) {
            if (number % 2 != 0 || number == 28) {
                lNumberOutcome.setStyle("-fx-background-color: black; -fx-border-color:white;");
            } else {
                lNumberOutcome.setStyle("-fx-background-color: red; -fx-border-color:white;");
            }
        }
    }
}
