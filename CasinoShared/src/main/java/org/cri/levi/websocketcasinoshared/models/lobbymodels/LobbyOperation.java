package org.cri.levi.websocketcasinoshared.models.lobbymodels;

public class LobbyOperation {
    private LobbyActionEnum action;
    private LobbyOperationOption option;

    public LobbyActionEnum getAction() {
        return action;
    }

    public LobbyOperationOption getOption() {
        return option;
    }

    public LobbyOperation(LobbyActionEnum action, LobbyOperationOption option) {
        this.action = action;
        this.option = option;
    }
}
