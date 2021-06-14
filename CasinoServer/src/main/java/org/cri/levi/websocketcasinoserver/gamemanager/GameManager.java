package org.cri.levi.websocketcasinoserver.gamemanager;

import org.cri.levi.websocketcasinoshared.models.Game;
import org.cri.levi.websocketcasinoshared.models.gamemodels.RouletteBetOption;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.CreateGameModel;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    public List<Game> getGames() {
        return games;
    }

    private static GameManager instance;
    private static List<Game> games = null;

    public List<GameRoom> getGameRooms() {
        return gameRooms;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private static List<GameRoom> gameRooms = null;

    public GameManager() {
        if (games == null) {
            games = new ArrayList<>();
            gameRooms = new ArrayList<>();
        }
    }

    public List<PlayerModel> updatePlayerListRoom(UUID gameID) {
        for (GameRoom r : gameRooms) {
            if (r.getGameID().equals(gameID)) {
                return r.updatePlayerListRoom();
            }
        }
        return new ArrayList<PlayerModel>();
    }

    public UUID createGame(CreateGameModel createGameModel) {
        Game game = new Game(createGameModel.getGameName());
        ResultRouletteGames.getInstance().addnewGameRouletteGame(game.getId());
        GameRoom gameRoom = new GameRoom(game.getId());
        games.add(game);
        gameRooms.add(gameRoom);
        return game.getId();
    }

    public void joinGame(Player player, UUID gameID, String SessionID) {
        for (GameRoom g : gameRooms) {
            if (gameID.equals(g.getGameID())) {
                g.addnewPlayer(player, gameID, SessionID);
            }

        }
        for (Game g : games) {
            if (gameID.equals(g.getId())) {
                g.addPlayer(new PlayerModel(player.getUsername(), player.getId()));
            }

        }
    }

    public void placeBet(BetModel betModel) {
        for (GameRoom r : gameRooms) {
            if (r.getGameID().equals(betModel.getGameID())) {
                r.getBetsInGameRoom().add(betModel);
            }
        }
    }


    public void checkResultRouletteGame(UUID gameID) {
        for (ResultRouletteGame g : ResultRouletteGames.getInstance().getResultGames()) {
            for (GameRoom r : gameRooms) {
             r.checkResultRouletteGame(g.getGameID(), g);
            }
        }
    }


    public void setupUpdateNumber(UUID gameID) {
        for (GameRoom g : gameRooms) {
            if (g.getGameID().equals(gameID)) {
                g.updateNumber();
            }
        }
    }

    public void removeBets(UUID gameID) {
        for (GameRoom r : gameRooms) {
            if (r.getGameID().equals(gameID)) {
                r.getBetsInGameRoom().clear();
            }
        }
    }


    public void leaveGame(Player player) {
        for (GameRoom r : gameRooms) {
            if (r.getGameID().equals(player.getGameID())) {
                for (Player p : r.getPlayerList()) {
                    if (p.getId() == player.getId()) {
                        r.getPlayerList().remove(p);
                        break;
                    }
                }
            }
        }

        for (Game g : games) {
            if (g.getId().equals(player.getGameID())) {
                for (PlayerModel s : g.getPlayers()) {
                    if (s.getPlayerID() == player.getId()) {
                        g.getPlayers().remove(s);
                        break;
                    }
                }
            }
        }

    }
}
