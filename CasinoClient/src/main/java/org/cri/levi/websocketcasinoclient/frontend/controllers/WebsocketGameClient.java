package org.cri.levi.websocketcasinoclient.frontend.controllers;

import org.cri.levi.websocketcasinoclient.endpoint.websocket.CasinoEndpoint;
import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteGame;
import org.cri.levi.websocketcasinoshared.models.gamemodels.*;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;
import java.util.Observer;

public class WebsocketGameClient implements Observer {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private static final Logger log = LoggerFactory.getLogger(WebsocketGameClient.class);

    private CasinoEndpoint casinoEndpoint;
    private Player player;
    private RouletteGame game;

    public WebsocketGameClient(Player player, RouletteGame game) {
        this.player = player;
        this.game = game;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }


    void start() {
        try {
            casinoEndpoint = CasinoEndpoint.getInstance();
            casinoEndpoint.addObserver(this);
            casinoEndpoint.setPlayer(player);
            casinoEndpoint.setGameable(game);
            casinoEndpoint.start();
            log.info("Websocket client started");
        } catch (Exception ex) {
            log.error("Client couldn't start.");
        }
    }

    void stop() {
        casinoEndpoint.stop();
    }

    @Override
    public void update(Observable o, Object arg) {
        log.info("Update called. {}", arg);
        if (arg instanceof PlaceBetModel) {
            PlaceBetModel placeBetModel = (PlaceBetModel) arg;
            support.firePropertyChange("placeBet", null, placeBetModel);
        } else if (arg instanceof UpdatePlayersModel) {
            UpdatePlayersModel updatePlayersModel = (UpdatePlayersModel) arg;
            support.firePropertyChange("updatePlayers", null, updatePlayersModel);
        } else if (arg instanceof ProfitModel) {
            ProfitModel profitModel = (ProfitModel) arg;
            support.firePropertyChange("sendProfit", null, profitModel);
        }
    }
}
