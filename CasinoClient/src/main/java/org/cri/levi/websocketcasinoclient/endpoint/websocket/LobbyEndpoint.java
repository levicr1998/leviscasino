package org.cri.levi.websocketcasinoclient.endpoint.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Observable;

import com.google.gson.JsonSyntaxException;
import org.cri.levi.websocketcasinoclient.frontend.logic.GamesOverview;
import org.cri.levi.websocketcasinoshared.models.Game;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.*;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class LobbyEndpoint extends Observable {

    private static final Logger log = LoggerFactory.getLogger(LobbyEndpoint.class);
    private static LobbyEndpoint instance = null;
    private static final String URI = "ws://localhost:8096/lobby/";
    private Session session;
    private Gson gson;
    private boolean isRunning = false;
    private Player player;
    private GamesOverview games;


    public void setPlayer(Player player) {
        this.player = player;
    }

    public LobbyEndpoint() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LobbyOperation.class, new LobbyOperationDeserializer());
        this.gson = builder.create();
        this.games = new GamesOverview();
    }

    public static LobbyEndpoint getInstance() {
        if (instance == null) {
            instance = new LobbyEndpoint();
            log.info("LobbyEndpoint singleton instantiated");
        }
        return instance;
    }

    public void start() {
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        log.info("Client open session {}", session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session) {
        LobbyOperation lobbyOperation = gson.fromJson(message, LobbyOperation.class);
        log.info("Client message received {}", message);

        switch (lobbyOperation.getAction()) {
            case NEWLOBBY:
                NewGameModel newGameModel = (NewGameModel) lobbyOperation.getOption();
                createNewLobby(newGameModel);
                break;
            case LOADGAMESCLIENT:
                GetGamesModel getGamesModel = (GetGamesModel) lobbyOperation.getOption();
                loadGames(getGamesModel);
                break;
            case GENERATENUMBER:
                processNumber(lobbyOperation);
                break;
            default:
                break;
        }
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        log.info("Client connection error {}", cause);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        log.info("Client close session {} for reason {} ", session.getRequestURI(), reason);
        session = null;
    }

    public void send(LobbyOperation lobbyOperation) {
        String jsonMessage = gson.toJson(lobbyOperation);
        session.getAsyncRemote().sendText(jsonMessage);
    }

    private void startClient() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(URI + player.getId()));
            log.info("Connected to server at {}", URI);

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            log.error("Error in startClient: {}", ex.getMessage());
        }
    }

    private void stopClient() {
        try {
            session.close();
            log.info("Session closed");

        } catch (IOException ex) {
            log.error("Error in stopClient {}", ex.getMessage());
        }
    }

    private void createNewLobby(NewGameModel newGameModel) {
        games.addGame(new Game(newGameModel.getId(), newGameModel.getGameName(), newGameModel.getMaxAmountOfPlayers(), newGameModel.getJoinedPlayers()));
        this.setChanged();
        this.notifyObservers(games);
    }

    private void loadGames(GetGamesModel getGamesModel) {
        this.setChanged();
        games = new GamesOverview(getGamesModel.getGames());
        this.notifyObservers(games);

    }

    private void processNumber(LobbyOperation lobbyOperation) {
        NumberGeneratorModel numberGeneratorModel = (NumberGeneratorModel) lobbyOperation.getOption();
        if (numberGeneratorModel.getGameID().equals(CasinoEndpoint.getInstance().getGameable().getId())) {
            int rouletteNumber = numberGeneratorModel.getRouletteNumber();
            try {
                log.info("Message processed: {}", rouletteNumber);
            } catch (JsonSyntaxException ex) {
                log.error("Can't process message: {}", ex.getMessage());
                return;
            }


            this.setChanged();
            this.notifyObservers(numberGeneratorModel);
        }
    }
}
