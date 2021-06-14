package org.cri.levi.websocketcasinoclient.frontend.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteBet;
import org.cri.levi.websocketcasinoclient.frontend.tools.ControlsAdder;
import org.cri.levi.websocketcasinoclient.frontend.tools.WindowOpener;
import org.cri.levi.websocketcasinoshared.models.gamemodels.*;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.cri.levi.websocketcasinoclient.endpoint.websocket.CasinoEndpoint;
import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteBoard;
import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteGame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;


public class GameController implements PropertyChangeListener {


    public void setPlayer(Player player) {
        this.player = player;
        lBalance.setText(Integer.toString(player.getBalance()));
        lUsername.setText(player.getUsername());
    }

    public void setGame(RouletteGame game) {
        this.game = game;
        websocketGameClient = new WebsocketGameClient(player, game);
        websocketGameClient.addPropertyChangeListener(this);
        websocketLobbyClient.addPropertyChangeListener(this);
        websocketGameClient.start();
    }

    private Player player;
    private RouletteGame game;
    private PropertyChangeListener pcl;

    public void setWebsocketLobbyClient(WebsocketLobbyClient websocketLobbyClient) {
        this.websocketLobbyClient = websocketLobbyClient;
    }
RouletteBet rouletteBet = new RouletteBet();
    WebsocketLobbyClient websocketLobbyClient;
    WebsocketGameClient websocketGameClient;
    ControlsAdder controlsAdder = new ControlsAdder();
    WindowOpener windowOpener = new WindowOpener();
    final ToggleGroup choiceBetGroup = new ToggleGroup();

    @FXML
    private RadioButton rbOther;
    @FXML
    private ChoiceBox<RouletteBetOption> cbRouletteOptions;
    @FXML
    private RadioButton rbNumber;
    @FXML
    private Label lNumberOutcome;
    @FXML
    private TextField tfBet;
    @FXML
    private TextField tfNumber;
    @FXML
    private GridPane rouletteBoard;
    @FXML
    private Label lBalance;
    @FXML
    private Label lNumberComment;
    @FXML
    private Label lOptionComment;
    @FXML
    private Label lUsername;
    @FXML
    private Label lBet;
    @FXML
    private Label lNotifcationWinner;
    @FXML
    private VBox vbUpdatePlayers;

    @FXML
    public void initialize() {
        rbOther.setToggleGroup(choiceBetGroup);
        rbNumber.setToggleGroup(choiceBetGroup);
        RouletteBoard board = new RouletteBoard(rouletteBoard);
        board.createRouletteBoard();
        cbRouletteOptions.getItems().setAll(RouletteBetOption.values());
        CasinoEndpoint.getInstance().setGameable(game);

        choiceBetGroup.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            if(choiceBetGroup.getSelectedToggle() != null){
                if(rbNumber.isSelected()){
                    tfNumber.setDisable(false);
                    cbRouletteOptions.setDisable(true);
                    lNumberComment.setText("Type your number here");
                    lOptionComment.setText("");
                } else if (rbOther.isSelected()){
                    tfNumber.setDisable(true);
                    cbRouletteOptions.setDisable(false);
                    lOptionComment.setText("Choose your bet option here");
                    lNumberComment.setText("");
                }
            }
        });

    }


    public void btnSubmitBetOnAction() {
        if(rbNumber.isSelected()){
            PlaceBetModel placeBetModel = new PlaceBetModel(Integer.parseInt(tfBet.getText()), player, Integer.parseInt(tfNumber.getText()));
            GameOperation gameOperation = new GameOperation(CasinoActionEnum.PLACEBET, placeBetModel);
            tfNumber.clear();
            tfBet.clear();
            CasinoEndpoint.getInstance().send(gameOperation);

        } else if(rbOther.isSelected()){
            PlaceBetModel placeBetModel = new PlaceBetModel(Integer.parseInt(tfBet.getText()), player, cbRouletteOptions.getValue());
            GameOperation gameOperation = new GameOperation(CasinoActionEnum.PLACEBET, placeBetModel);
            CasinoEndpoint.getInstance().send(gameOperation);
            tfNumber.clear();
            tfBet.clear();
        }



    }

    public void btnHomeOnAction(ActionEvent event) throws IOException {

        windowOpener.openHomeScreen(windowOpener, player, event);
        websocketGameClient.stop();
    }


    private void placeBet(PlaceBetModel placeBetModel) {
        Platform.runLater(() -> {
            int rouletteBetCurrent = rouletteBet.getAmountRouletteBet() + placeBetModel.getBet();
            rouletteBet.setAmountRouletteBet(rouletteBetCurrent);
            lBet.setText(Integer.toString(rouletteBet.getAmountRouletteBet()));
            lBalance.setText(Integer.toString(placeBetModel.getPlayer().getBalance()));

        });
    }

    private void generateNewNumber(int rouletteNumber) {
        Platform.runLater(() -> {
            lNotifcationWinner.setText("");
            lNumberOutcome.setText(Integer.toString(rouletteNumber));
            rouletteBet.setAmountRouletteBet(0);
            lBet.setText(Integer.toString(rouletteBet.getAmountRouletteBet()));
        });

        game.changeRouletteNumber(websocketLobbyClient.getMagicNumber(), lNumberOutcome);
    }

    private void updatePlayers(UpdatePlayersModel updatePlayersModel) {
        Platform.runLater(() -> {
            vbUpdatePlayers.getChildren().clear();
            for (PlayerModel p : updatePlayersModel.getPlayers()) {
                Pane pane = new Pane(
                        controlsAdder.newLabel(80, 10, p.getUsername(), false)
                );
                pane.setPrefHeight(40);
                pane.setPrefWidth(200);
                pane.getStyleClass().add("wBackground");
                Platform.runLater(() -> {
                    vbUpdatePlayers.getChildren().add(pane);
                });

            }
        });
    }

    private void addProfit(ProfitModel profitModel) {
        Platform.runLater(() -> {
            lBalance.setText(Integer.toString(profitModel.getBalance()));
            lNotifcationWinner.setText("You won €" +  profitModel.getProfit() +"! Congratulations!");
            lNotifcationWinner.setTextFill(Color.GREEN);
        });
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("newNumber")) {
            int rouletteNumber = (int) evt.getNewValue();
            generateNewNumber(rouletteNumber);

        } else if (evt.getPropertyName().equals("placeBet")) {
            PlaceBetModel placeBetModel = (PlaceBetModel) evt.getNewValue();

            placeBet(placeBetModel);
        } else if (evt.getPropertyName().equals("updatePlayers")) {
            UpdatePlayersModel updatePlayersModel = (UpdatePlayersModel) evt.getNewValue();
            game.setId(updatePlayersModel.getGameID());
            player.setGameID(updatePlayersModel.getGameID());
            updatePlayers(updatePlayersModel);
        } else if (evt.getPropertyName().equals("sendProfit")) {
            ProfitModel profitModel = (ProfitModel) evt.getNewValue();
            addProfit(profitModel);
        }
    }


}
