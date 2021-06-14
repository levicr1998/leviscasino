package org.cri.levi.websocketcasinoshared.models.gamemodels;

import org.cri.levi.websocketcasinoshared.models.Player;


public class LeaveGameModel implements GameOperationOption {

    public LeaveGameModel(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public LeaveGameModel(){
        if(player == null){
            player = new Player();
        }
    }
    Player player;
}
