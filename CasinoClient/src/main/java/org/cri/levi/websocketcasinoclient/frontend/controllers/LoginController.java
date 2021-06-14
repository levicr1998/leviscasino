package org.cri.levi.websocketcasinoclient.frontend.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.cri.levi.websocketcasinoclient.endpoint.rest.LoginRestClient;
import org.cri.levi.websocketcasinoclient.frontend.tools.AlertHelper;
import org.cri.levi.websocketcasinoclient.frontend.tools.WindowOpener;
import org.cri.levi.websocketcasinoshared.models.loginmodels.LoginModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField tvUsername;
    @FXML
    private PasswordField tvPassword;

    WindowOpener windowOpener = new WindowOpener();

    private LoginRestClient client;

    @FXML
    public void initialize() {
        client = new LoginRestClient();
    }

    public void btnHomeOnAction(ActionEvent event) throws IOException {
        
        LoginModel loginModel = new LoginModel(tvUsername.getText(), tvPassword.getText());
        Player loginResponse = client.login(loginModel);
        if (loginResponse != null) {
            windowOpener.openHomeScreen(windowOpener, loginResponse, event);
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Wrong username or password", "Please fill in the right username or password");
        }

    }

    public void btnSignUpOnAction(ActionEvent event) throws IOException {

        Parent registerLoader = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
        Scene registerViewScene = new Scene(registerLoader, 600, 400);
        windowOpener.showWindow(event, registerViewScene);
    }
}
