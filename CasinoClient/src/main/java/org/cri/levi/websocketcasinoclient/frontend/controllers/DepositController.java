package org.cri.levi.websocketcasinoclient.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.cri.levi.websocketcasinoclient.endpoint.rest.BankRestClient;
import org.cri.levi.websocketcasinoclient.frontend.tools.WindowOpener;
import org.cri.levi.websocketcasinoshared.models.bankmodels.TransferModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.io.IOException;

public class DepositController {
    private BankRestClient client;
    private Player player;
    @FXML
    private Label lBalance;
    @FXML
    private TextField tfAmount;

    @FXML
    public void initialize() {
        client = new BankRestClient();
    }

    public void setPlayer(Player player) {
        this.player = player;
        lBalance.setText("€ " + player.getBalance());
    }

    WindowOpener windowOpener = new WindowOpener();

    public void btnHomeOnAction(ActionEvent event) throws IOException {
        windowOpener.openHomeScreen(windowOpener, player, event);
    }

    public void btnDepositOnAction() {
        int depositAmount = Integer.parseInt(tfAmount.getText());
        TransferModel transferModel = new TransferModel(player, depositAmount);
        setPlayer(client.deposit(transferModel));
    }
}
