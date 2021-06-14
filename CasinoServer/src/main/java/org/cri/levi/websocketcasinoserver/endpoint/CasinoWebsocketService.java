package org.cri.levi.websocketcasinoserver.endpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cri.levi.websocketcasinoserver.database.authenticator.Authenticator;
import org.cri.levi.websocketcasinoserver.database.bank.Bank;
import org.cri.levi.websocketcasinoserver.database.playerfinder.Finder;
import org.cri.levi.websocketcasinoserver.gamemanager.BetModel;
import org.cri.levi.websocketcasinoserver.gamemanager.GameManager;
import org.cri.levi.websocketcasinoshared.models.gamemodels.*;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;
import org.cri.levi.websocketcasinoshared.models.gamemodels.UpdatePlayersModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.sql.SQLException;
import java.util.*;

@ServerEndpoint(value = "/casino/{playerID}/{gameID}")
public class CasinoWebsocketService {

    private static final Logger log = LoggerFactory.getLogger(CasinoWebsocketService.class);
    private static final List<Session> sessions = new ArrayList<>();
    private static GameManager gameManager = GameManager.getInstance();
    private static Authenticator authenticator = null;
    private static List<Player> players = new ArrayList<>();
    private static Bank bank = Bank.getInstance();
    private static CasinoWebsocketService instance;
    private static Finder finder = Finder.getInstance();

    public static CasinoWebsocketService getInstance() {
        if (instance == null) {
            instance = new CasinoWebsocketService();
        }
        return instance;
    }

    public CasinoWebsocketService() {
        if (gameManager == null) {
            gameManager = new GameManager();
            authenticator = new Authenticator();
            bank = new Bank();
        }
    }

    @OnOpen
    public void onConnect(Session session, @PathParam("playerID") String playerIDstring, @PathParam("gameID") String gameIDstring) {
        int playerID = Integer.parseInt(playerIDstring);
        UUID gameID = UUID.fromString(gameIDstring);
        log.info("Connected SessionID: {}", session.getId());
        log.info("Gameid: {}", gameID);
        sessions.add(session);

        try {
            Player player = finder.getPlayer(playerID);
            players.add(player);
            joinGame(player, gameID, session);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        log.info("Session added. Session count is {}", sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        log.info("Session ID: {} Received: {}", session.getId(), message);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GameOperation.class, new GameOperationDeserializer());
        Gson gson = builder.create();

        GameOperation gameOperation = gson.fromJson(message, GameOperation.class);
        switch (gameOperation.getAction()) {
            case PLACEBET:
                placeBet(gameOperation, session);
                break;
            default:
                break;
        }
    }


    @OnClose
    public void onClose(CloseReason reason, Session session) {
        log.info("Session ID: {} Closed. Reason: {}", session.getId(), reason);
      Player player = findPlayer(session.getId());
      if(!(player == null)) {
          leaveGame(player);
          sessions.remove(session);
      }


    }

    @OnError
    public void onError(Throwable cause, Session session) {
        log.error("Session ID: {} Error: {}", session.getId(), cause.getMessage());
    }

    private void leaveGame(Player player){
        UUID gameID = player.getGameID();
        gameManager.leaveGame(player);
        List<PlayerModel> playerModels = updatePlayersList(gameID);
        UpdatePlayersModel updatePlayersModel = new UpdatePlayersModel(gameID,playerModels);
        GameOperation gameOperationUpdate = new GameOperation(CasinoActionEnum.UPDATEPLAYERS, updatePlayersModel);
        for(Session s: sessions){
            for(PlayerModel p: playerModels){
                if(p.getSessionID().equals(s.getId())){
                    send(gameOperationUpdate, s);
                }
            }
        }

    }


    private void joinGame(Player player, UUID gameID, Session session) {
        gameManager.joinGame(player, gameID, session.getId());
        List<PlayerModel> playersList = updatePlayersList(gameID);
        UpdatePlayersModel updatePlayersModel = new UpdatePlayersModel(gameID, playersList);
        GameOperation gameOperation = new GameOperation(CasinoActionEnum.UPDATEPLAYERS, updatePlayersModel);
        for (Session s : sessions) {
            for (PlayerModel p : playersList) {
                if (p.getSessionID().equals(s.getId())) {
                    send(gameOperation, s);
                }
            }
        }
    }


    private List<PlayerModel> updatePlayersList(UUID gameID) {
        return gameManager.updatePlayerListRoom(gameID);
    }

    private void placeBet(GameOperation gameOperation, Session session) {
        PlaceBetModel placeBetModel = (PlaceBetModel) gameOperation.getOption();
        try {
            boolean checkBet = bank.checkPlaceBetPossible(placeBetModel.getPlayer().getId(), placeBetModel.getBet());
            if (checkBet) {
                PlaceBetModel placeBetModelAccepted = new PlaceBetModel(placeBetModel.getBet(), bank.removeBetFromBalance(placeBetModel.getPlayer().getId(), placeBetModel.getBet()));
                if(placeBetModel.getRouletteBetOption().equals(RouletteBetOption.NUMBER)) {
                    gameManager.placeBet(new BetModel(placeBetModel.getPlayer().getId(), placeBetModel.getPlayer().getGameID(), placeBetModel.getBet(), placeBetModel.getRouletteNumber()));
                } else {
                    gameManager.placeBet(new BetModel(placeBetModel.getPlayer().getId(), placeBetModel.getPlayer().getGameID(), placeBetModel.getBet(), placeBetModel.getRouletteBetOption()));
                }
                GameOperation resultGameOperation = new GameOperation(CasinoActionEnum.PLACEBET, placeBetModelAccepted);
                send(resultGameOperation, session);
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }


    }

    public void sendProfit(int id, int profit) {
        for (Session s : sessions) {
            if (findPlayerID(s.getId()) == id) {
                try {
                    Player player = bank.addProfitToBalance(id, profit);
                    ProfitModel profitModel = new ProfitModel(player.getId(), player.getBalance(), profit);
                    GameOperation gameOperation = new GameOperation(CasinoActionEnum.SENDPROFIT, profitModel);
                    send(gameOperation, s);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }
    }


    private int findPlayerID(String sessionId) {
        for (Player player : players) {
            if (player.getSessionId().equals(sessionId))
                return player.getId();
        }
        return 0;
    }

    private Player findPlayer(String sessionId) {
        for (Player player : players) {
            if (player.getSessionId().equals(sessionId))
                return player;
        }
        return null;
    }

    public void send(GameOperation gameOperation, Session session) {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(gameOperation);
        session.getAsyncRemote().sendText(jsonMessage);
    }
}
