package org.cri.levi.websocketcasinoserver.gamemanager;

import org.cri.levi.websocketcasinoserver.endpoint.LobbyWebsocketService;
import org.cri.levi.websocketcasinoshared.models.gamemodels.RouletteBetOption;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.LobbyActionEnum;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.NumberGeneratorModel;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.LobbyOperation;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class GameRoom {
    public UUID getGameID() {
        return gameID;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<BetModel> getBetsInGameRoom() {
        return betsInGameRoom;
    }

    private List<BetModel> betsInGameRoom = new ArrayList<>();

    private UUID gameID;
    private List<Player> playerList = new ArrayList<>();

    public GameRoom(UUID gameID) {
        this.gameID = gameID;
    }

    public GameRoom() {
    }

    public void updateNumber() {

        Thread r = new Thread(() -> {

            NumberGeneratorModel numberGeneratorModel = new NumberGeneratorModel(gameID);
            LobbyOperation lobbyOperation = new LobbyOperation(LobbyActionEnum.GENERATENUMBER, numberGeneratorModel);
            LobbyWebsocketService.getInstance().sendNumber(lobbyOperation);
            ResultRouletteGames.getInstance().addnewRouletteNumberToGame(gameID, numberGeneratorModel.getRouletteNumber());
            GameManager.getInstance().checkResultRouletteGame(gameID);
            GameManager.getInstance().removeBets(gameID);
        });
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        threadPoolExecutor.scheduleAtFixedRate(r, 0, 20000, TimeUnit.MILLISECONDS);
    }

    public void addnewPlayer(Player player, UUID gameID, String sessionID) {
        player.setGameID(gameID);
        player.setSessionID(sessionID);
        playerList.add(player);

    }

    public List<PlayerModel> updatePlayerListRoom() {
        List<PlayerModel> players = new ArrayList<>();
        for (Player p : getPlayerList()) {
            players.add(new PlayerModel(p.getUsername(), p.getId(), p.getSessionId()));
        }
        return players;
    }

    public void checkResultRouletteGame(UUID ResultRouletteGameID, ResultRouletteGame resultRouletteGame) {
        for (BetModel b : getBetsInGameRoom()) {
            if (ResultRouletteGameID.equals(resultRouletteGame.getGameID()) && getGameID().equals(resultRouletteGame.getGameID())) {
                RouletteResultChecker rouletteResultChecker = new RouletteResultChecker();
                if (b.getRouletteBetOption().equals(RouletteBetOption.NUMBER)) {
                    rouletteResultChecker.checkNumber(resultRouletteGame.getRouletteNumber(), b.getRouletteNumber(), b.getBet(), b.getPlayerID());
                } else {
                    rouletteResultChecker.checkOtherOption(resultRouletteGame.getRouletteNumber(), b.getRouletteBetOption(), b.getBet(), b.getPlayerID());
                }
            }
        }
    }

}
