package org.cri.levi.websocketcasinoclient.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import org.cri.levi.websocketcasinoclient.frontend.tools.WindowOpener;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.io.IOException;

public class HomeController {
    WindowOpener windowOpener = new WindowOpener();

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
        tfUsername.setText("Welcome " + player.getUsername());
        tfBalance.setText("Balance: € " + player.getBalance());
    }

    @FXML
    private Label tfUsername;
    @FXML
    private Label tfBalance;


    public void btnCasinoLobbyOnAction(ActionEvent event) throws IOException {


        FXMLLoader lobbyLoader = new FXMLLoader(getClass().getResource("/views/Lobby.fxml"));
        Parent lobbyLoaderParent = lobbyLoader.load();
        LobbyController lobbyController = lobbyLoader.getController();
        lobbyController.setPlayer(player);
        Scene lobbyViewScene = new Scene(lobbyLoaderParent, 600, 400);
        windowOpener.showWindow(event, lobbyViewScene);
    }

    public void btnWithdrawOnAction(ActionEvent event) throws IOException {
        FXMLLoader withdrawLoader = new FXMLLoader(getClass().getResource("/views/Withdraw.fxml"));
        Parent withdrawLoaderParent = withdrawLoader.load();
        WithdrawController withdrawController = withdrawLoader.getController();
        withdrawController.setPlayer(player);
        Scene withdrawViewScene = new Scene(withdrawLoaderParent, 600, 400);
        windowOpener.showWindow(event, withdrawViewScene);
    }

    public void btnDepositOnAction(ActionEvent event) throws IOException {
        FXMLLoader depositLoader = new FXMLLoader(getClass().getResource("/views/Deposit.fxml"));
        Parent depositLoaderParent = depositLoader.load();
        DepositController depositController = depositLoader.getController();
        depositController.setPlayer(player);
        Scene depositViewScene = new Scene(depositLoaderParent, 600, 400);
        windowOpener.showWindow(event, depositViewScene);
    }

    public void btnLoginOnAction(ActionEvent event) throws IOException {
        Parent loginLoader = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene loginViewScene = new Scene(loginLoader, 600, 400);
        windowOpener.showWindow(event, loginViewScene);
    }
}
