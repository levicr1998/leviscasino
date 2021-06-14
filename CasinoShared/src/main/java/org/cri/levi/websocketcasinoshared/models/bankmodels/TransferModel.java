package org.cri.levi.websocketcasinoshared.models.bankmodels;

import org.cri.levi.websocketcasinoshared.models.Player;

public class TransferModel {

    public Player getPlayer() {
        return player;
    }

    private Player player;

    public int getTransferMoney() {
        return transferMoney;
    }

    private int transferMoney;

    public TransferModel(Player player, int transferMoney) {
        this.player = player;
        this.transferMoney = transferMoney;
    }

    public TransferModel() {

    }
}
