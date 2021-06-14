package org.cri.levi.websocketcasinoshared.models.gamemodels;

import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdatePlayersModel implements GameOperationOption {
    private UUID gameID;

    public List<PlayerModel> getPlayers() {
        return players;
    }

    private List<PlayerModel> players = new ArrayList<>();


    public UpdatePlayersModel(UUID gameID, List<PlayerModel> players) {
        this.gameID = gameID;
        this.players = players;
    }

    public UUID getGameID() {
        return gameID;
    }
}
