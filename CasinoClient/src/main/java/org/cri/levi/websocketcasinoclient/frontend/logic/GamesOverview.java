package org.cri.levi.websocketcasinoclient.frontend.logic;

import org.cri.levi.websocketcasinoshared.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GamesOverview {

    public GamesOverview(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }


    private List<Game> games = new ArrayList<>();

    public GamesOverview() {

    }

    public void addGame(Game game) {
        games.add(game);
    }

}
