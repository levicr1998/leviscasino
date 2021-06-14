package org.cri.levi.websocketcasinoclient.endpoint.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteGame;
import org.cri.levi.websocketcasinoshared.models.gamemodels.*;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;

@ClientEndpoint
public class CasinoEndpoint extends Observable {

    private static final Logger log = LoggerFactory.getLogger(CasinoEndpoint.class);
    private static CasinoEndpoint instance = null;
    private static final String URI = "ws://localhost:8095/casino/";
    private Session session;
    private Gson gson;
    private boolean isRunning = false;
    private Player player;

    public void setGameable(RouletteGame gameable) {
        this.gameable = gameable;
    }

    public RouletteGame getGameable() {
        return gameable;
    }

    private RouletteGame gameable;

    private CasinoEndpoint() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GameOperation.class, new GameOperationDeserializer());
        this.gson = builder.create();
    }

    public static CasinoEndpoint getInstance() {
        if (instance == null) {
            instance = new CasinoEndpoint();
            log.info("CasinoEndpoint singleton instantiated");
        }
        return instance;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        GameOperation gameOperation = gson.fromJson(message, GameOperation.class);
        log.info("Client message received {}", message);

        switch (gameOperation.getAction()) {
            case PLACEBET:
                placeBet(gameOperation);
                break;
            case UPDATEPLAYERS:
                updatePlayers(gameOperation);
                break;
            case SENDPROFIT:
                sendProfit(gameOperation);
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

    public void send(GameOperation gameOperation) {
        String jsonMessage = gson.toJson(gameOperation);
        log.info("jsonmessage:  {}", jsonMessage);
        session.getAsyncRemote().sendText(jsonMessage);
    }

    private void startClient() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(URI + player.getId() + "/" + gameable.getId()));
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

    private void sendProfit(GameOperation gameOperation) {
        ProfitModel profitModel = (ProfitModel) gameOperation.getOption();
        this.setChanged();
        this.notifyObservers(profitModel);
    }

    private void updatePlayers(GameOperation gameOperation) {
        UpdatePlayersModel updatePlayersModel = (UpdatePlayersModel) gameOperation.getOption();
        this.setChanged();
        this.notifyObservers(updatePlayersModel);
    }

    private void placeBet(GameOperation gameOperation) {
        PlaceBetModel placeBetModel = (PlaceBetModel) gameOperation.getOption();
        player.setBalance(placeBetModel.getPlayer().getBalance());

        this.setChanged();
        this.notifyObservers(placeBetModel);

    }


}