package org.cri.levi.websocketcasinoclient.frontend.controllers;

import org.cri.levi.websocketcasinoclient.endpoint.websocket.LobbyEndpoint;
import org.cri.levi.websocketcasinoclient.frontend.logic.GamesOverview;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.NumberGeneratorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;
import java.util.Observer;

public class WebsocketLobbyClient implements Observer {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private static final Logger log = LoggerFactory.getLogger(WebsocketLobbyClient.class);

    public LobbyEndpoint getLobbyEndpoint() {
        return lobbyEndpoint;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    int magicNumber;
    private LobbyEndpoint lobbyEndpoint;

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    void start() {
        try {
            lobbyEndpoint = LobbyEndpoint.getInstance();
            lobbyEndpoint.addObserver(this);
            lobbyEndpoint.start();
            log.info("Websocket client started");
        } catch (Exception ex) {
            log.error("Client couldn't start.");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        log.info("Update called. {}", arg);
        if (arg instanceof GamesOverview) {
            GamesOverview gamesOverview = (GamesOverview) arg;
            support.firePropertyChange("updateGames", null, gamesOverview);
        } else if (arg instanceof NumberGeneratorModel) {
            NumberGeneratorModel rouletteNumberModel = (NumberGeneratorModel) arg;
            this.magicNumber = rouletteNumberModel.getRouletteNumber();
            support.firePropertyChange("newNumber", null, magicNumber);
        }
    }
}
