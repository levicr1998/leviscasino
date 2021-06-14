package org.cri.levi.websocketcasinoserver.endpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import javassist.NotFoundException;
import org.cri.levi.websocketcasinoserver.database.playerfinder.Finder;
import org.cri.levi.websocketcasinoserver.gamemanager.GameManager;
import org.cri.levi.websocketcasinoserver.gamemanager.GameRoom;
import org.cri.levi.websocketcasinoshared.models.Game;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.*;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ServerEndpoint(value = "/lobby/{id}")
public class LobbyWebsocketService {
    private static final Logger log = LoggerFactory.getLogger(LobbyWebsocketService.class);
    private static final List<Session> sessions = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();
    private static GameManager gameManager = GameManager.getInstance();
    private static Finder finder = Finder.getInstance();
    private static LobbyWebsocketService instance;

    public static LobbyWebsocketService getInstance() {
        if (instance == null) {
            instance = new LobbyWebsocketService();
        }
        return instance;
    }

    public LobbyWebsocketService() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }
    }

    @OnOpen
    public void onConnect(Session session, @PathParam("id") String idString) {
        int id = Integer.parseInt(idString);
        Player player = null;
        log.info("Connected SessionID: {}", session.getId());
        log.info("Received id: {}", id);
        try {
            player = finder.getPlayer(id, session.getId());
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        players.add(player);
        sessions.add(session);
        log.info("Session added. Session count is {}", sessions.size());

    }

    @OnMessage
    public void onText(String message, Session session) {
        log.info("Session ID: {} Received: {}", session.getId(), message);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LobbyOperation.class, new LobbyOperationDeserializer());
        Gson gson = builder.create();

        LobbyOperation lobbyOperation = gson.fromJson(message, LobbyOperation.class);
        switch (lobbyOperation.getAction()) {
            case CREATEGAME:
                createGame(lobbyOperation);
                break;
            case LOADGAMES:
                loadGames(session);
                break;
            default:
                break;
        }
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        log.info("Session ID: {} Closed. Reason: {}", session.getId(), reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        log.error("Session ID: {} Error: {}", session.getId(), cause.getMessage());
    }

    public void send(LobbyOperation lobbyOperation, Session session) {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(lobbyOperation);
        session.getAsyncRemote().sendText(jsonMessage);
    }

    private void createGame(LobbyOperation operation) {
        CreateGameModel createGameModel = (CreateGameModel) operation.getOption();
        UUID id = gameManager.createGame(createGameModel);

        List<PlayerModel> joinedPlayers = new ArrayList<>();

        NewGameModel newGameModel = new NewGameModel(id, createGameModel.getGameName(), createGameModel.getMaxAmountOfPlayers(), createGameModel.getGameType(), joinedPlayers);
        LobbyOperation lobbyOperation = new LobbyOperation(LobbyActionEnum.NEWLOBBY, newGameModel);
        for (Session s : sessions) {
            send(lobbyOperation, s);
            log.error("Session {}", lobbyOperation);
        }
        updateNumber(id);
    }

    private void loadGames(Session session) {
        List<Game> games = gameManager.getGames();
        GetGamesModel getGamesModel = new GetGamesModel(games);
        LobbyOperation lobbyOperation = new LobbyOperation(LobbyActionEnum.LOADGAMESCLIENT, getGamesModel);
        send(lobbyOperation, session);

    }


    private Player findPlayer(int playerID, UUID gameID) throws NotFoundException {
        for (GameRoom room : GameManager.getInstance().getGameRooms()) {
            if (room.getGameID() == gameID) {
                for (Player p : room.getPlayerList()) {
                    if (p.getId() == playerID) {
                        return p;
                    }
                }
            }
        }
        throw new NotFoundException("Session not found connected to this player...");
    }

    private boolean checkPlayersInGame(UUID gameID) {
        for (GameRoom room : GameManager.getInstance().getGameRooms()) {
            if (room.getGameID() == gameID && room.getPlayerList().isEmpty()) {
                    return false;
            }
        }
        return true;
    }

    private int findPlayerID(String sessionId) {
        for (Player player : players) {
            if (player.getSessionId().equals(sessionId))
                return player.getId();
        }
        return 0;
    }

    private void updateNumber(UUID gameID) {
        try {
            gameManager.setupUpdateNumber(gameID);


        } catch (JsonSyntaxException ex) {
            log.error("Can't process message: {0}", ex);
        }
    }

    public void sendNumber(LobbyOperation lobbyOperation) {

        NumberGeneratorModel numberGeneratorModel = (NumberGeneratorModel) lobbyOperation.getOption();
        if (checkPlayersInGame(numberGeneratorModel.getGameID())) {
            log.info("Roulette nummer: {} GameID: {}", numberGeneratorModel.getRouletteNumber(), numberGeneratorModel.getGameID());
            for (Session s : sessions) {
                log.info("Session ID: {} Handling message", lobbyOperation);
                try {
                    log.info("Session gameID {}", findPlayer(findPlayerID(s.getId()), numberGeneratorModel.getGameID()).getGameID());
                } catch (NotFoundException e) {
                    log.info(e.getMessage());
                }

                try {
                    UUID gameID = findPlayer(findPlayerID(s.getId()), numberGeneratorModel.getGameID()).getGameID();
                    if (numberGeneratorModel.getGameID().equals(gameID)) {
                        send(lobbyOperation, s);
                        log.info("Session ID: {} Message handled", s.getId());
                    }
                } catch (NotFoundException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }
}
