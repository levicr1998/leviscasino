package org.cri.levi.websocketcasinoshared.models.lobbymodels;

import org.cri.levi.websocketcasinoshared.models.Game;

import java.util.List;

public class GetGamesModel implements LobbyOperationOption {

    public List<Game> getGames() {
        return games;
    }

    private List<Game> games = null;


    public GetGamesModel(List<Game> games) {
        this.games = games;
    }
}
