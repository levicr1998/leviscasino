package org.cri.levi.websocketcasinoclient.frontend.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.cri.levi.websocketcasinoclient.endpoint.websocket.CasinoEndpoint;
import org.cri.levi.websocketcasinoclient.endpoint.websocket.LobbyEndpoint;
import org.cri.levi.websocketcasinoclient.frontend.logic.GamesOverview;
import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteGame;
import org.cri.levi.websocketcasinoclient.frontend.tools.ControlsAdder;
import org.cri.levi.websocketcasinoclient.frontend.tools.WindowOpener;
import org.cri.levi.websocketcasinoshared.models.Game;
import org.cri.levi.websocketcasinoshared.models.GameType;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.CreateGameModel;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.LobbyActionEnum;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.LobbyOperation;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LobbyController implements PropertyChangeListener {
    private static final Logger log = LoggerFactory.getLogger(LobbyController.class);
    private Player player;
    @FXML
    private Label tfUsername;
    @FXML
    private TextField tfGamename;
    WebsocketLobbyClient websocketLobbyClient;
    private PropertyChangeListener pcl;
    private ControlsAdder controlsAdder = new ControlsAdder();

    public void setPlayer(Player player) {
        this.player = player;
        tfUsername.setText("Username: " + player.getUsername());
        LobbyEndpoint.getInstance().setPlayer(player);

        websocketLobbyClient.start();
        LobbyOperation lobbyOperation = new LobbyOperation(LobbyActionEnum.LOADGAMES, null);
        websocketLobbyClient.getLobbyEndpoint().send(lobbyOperation);
    }

    @FXML
    private VBox vbLobbies;

    @FXML
    public void initialize() {
        websocketLobbyClient = new WebsocketLobbyClient();
        websocketLobbyClient.addPropertyChangeListener(this);


    }

    WindowOpener windowOpener = new WindowOpener();

    public void btnHomeOnAction(ActionEvent event) throws IOException {
        windowOpener.openHomeScreen(windowOpener, player, event);
    }


    public void btnCreateGameOnAction() {
        CreateGameModel createGameModel = new CreateGameModel(tfGamename.getText(), 20, GameType.ROULETTE);
        LobbyOperation lobbyOperation = new LobbyOperation(LobbyActionEnum.CREATEGAME, createGameModel);
        LobbyEndpoint.getInstance().send(lobbyOperation);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("updateGames")) {
            GamesOverview gamesOverview = (GamesOverview) evt.getNewValue();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vbLobbies.getChildren().clear();
                    for (Game g : gamesOverview.getGames()) {
                        Button button = controlsAdder.getLobbyButton();
                        button.setOnAction(new EventHandler<>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/views/Game.fxml"));
                                    Parent gameLoaderParent = gameLoader.load();
                                    GameController gameController = gameLoader.getController();
                                    gameController.setPlayer(player);
                                    gameController.setWebsocketLobbyClient(websocketLobbyClient);
                                    gameController.setGame(new RouletteGame(g.getId(), g.getGameName(), g.getMaxAmountOfPlayers(), g.getPlayers()));
                                    Scene gameViewScene = new Scene(gameLoaderParent, 1000, 600);
                                    windowOpener.showWindow(event, gameViewScene);
                                } catch (IOException e) {
                                    log.info(e.getMessage());
                                }
                            }
                        });
                        Pane pane = new Pane(
                                button,
                                controlsAdder.newLabel(20, 8, "Game name: ", true),
                                controlsAdder.newLabel(100, 8, g.getGameName(), false),
                                controlsAdder.newLabel(20, 30, "Players: ", true),
                                controlsAdder.newLabel(100, 30, g.getPlayers().size() + " | " + g.getMaxAmountOfPlayers(), false)
                        );
                        pane.setPrefHeight(60);
                        pane.setPrefWidth(292);
                        pane.getStyleClass().add("wBackground");
                        Platform.runLater(() -> vbLobbies.getChildren().add(pane));
                    }
                }
            });

        }
    }


}
