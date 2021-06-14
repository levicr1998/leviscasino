package org.cri.levi.websocketcasinoshared.models.gamemodels;

public class GameOperation {

    public GameOperation(CasinoActionEnum action, GameOperationOption option) {
        this.action = action;
        this.option = option;
    }

    private CasinoActionEnum action;
    private GameOperationOption option;

    public CasinoActionEnum getAction() {
        return action;
    }


    public GameOperationOption getOption() {
        return option;
    }


}
