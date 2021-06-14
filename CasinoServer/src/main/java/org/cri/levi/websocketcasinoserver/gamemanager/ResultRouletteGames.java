package org.cri.levi.websocketcasinoserver.gamemanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResultRouletteGames {
    private static ResultRouletteGames instance;

    public List<ResultRouletteGame> getResultGames() {
        return resultGames;
    }

    private static List<ResultRouletteGame> resultGames = new ArrayList<>();

    public static ResultRouletteGames getInstance() {
        if (instance == null) {
            instance = new ResultRouletteGames();
        }
        if (resultGames == null) {
            resultGames = new ArrayList<>();
        }
        return instance;
    }

    public void addnewGameRouletteGame(UUID gameID) {
        resultGames.add(new ResultRouletteGame(gameID));
    }

    public void addnewRouletteNumberToGame(UUID gameID, int rouletteNumber) {
        for (ResultRouletteGame r : resultGames) {
            if (r.getGameID() == gameID) {
                r.addRouletteNumber(rouletteNumber);
            }
        }
    }
}
