package org.cri.levi.websocketcasinoshared.models.gamemodels;

import org.cri.levi.websocketcasinoshared.models.Player;

public class PlaceBetModel implements GameOperationOption {
    public int getBet() {
        return bet;
    }

    private int bet;

    public int getRouletteNumber() {
        return rouletteNumber;
    }

    private int rouletteNumber;

    public RouletteBetOption getRouletteBetOption() {
        return rouletteBetOption;
    }

    private RouletteBetOption rouletteBetOption;

    public Player getPlayer() {
        return player;
    }

    private Player player;

    public PlaceBetModel(int bet, Player player, int rouletteNumber) {
        this.bet = bet;
        this.player = player;
        this.rouletteNumber = rouletteNumber;
        this.rouletteBetOption = RouletteBetOption.NUMBER;
    }

    public PlaceBetModel(int bet, Player player, RouletteBetOption rouletteBetOption) {
        this.bet = bet;
        this.player = player;
        this.rouletteBetOption = rouletteBetOption;
    }

    public PlaceBetModel(int bet, Player player) {
        this.bet = bet;
        this.player = player;
    }
}
