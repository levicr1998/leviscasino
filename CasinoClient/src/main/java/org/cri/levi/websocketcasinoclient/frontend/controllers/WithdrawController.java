package org.cri.levi.websocketcasinoclient.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.cri.levi.websocketcasinoclient.endpoint.rest.BankRestClient;
import org.cri.levi.websocketcasinoclient.frontend.tools.AlertHelper;
import org.cri.levi.websocketcasinoclient.frontend.tools.WindowOpener;
import org.cri.levi.websocketcasinoshared.models.bankmodels.TransferModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.io.IOException;

public class WithdrawController {

    private Player player;
    private BankRestClient client;
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

    public void btnWithdrawOnAction() {
        int withdrawAmount = Integer.parseInt(tfAmount.getText());
        if(withdrawAmount > player.getBalance()){
            AlertHelper.showAlert(Alert.AlertType.ERROR,"Not enough money","You can't withdraw more than you have on your account!");
        } else {
            TransferModel transferModel = new TransferModel(player, withdrawAmount);
            setPlayer(client.withdraw(transferModel));
        }
    }
}
