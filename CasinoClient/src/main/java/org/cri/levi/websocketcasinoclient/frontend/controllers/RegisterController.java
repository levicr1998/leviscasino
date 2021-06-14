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

public class RegisterController {
    private LoginRestClient client;

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfRpassword;

    public void initialize() {
        client = new LoginRestClient();
    }

    WindowOpener windowOpener = new WindowOpener();

    public void btnLoginOnAction(ActionEvent event) throws IOException {
        if ((tfUsername.getText().equals("") || tfPassword.getText().equals("")) && !(tfPassword.getText().equals(tfRpassword.getText()))) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Wrong input", "Make sure the password fields are equal and the textfields aren't empty!");
        } else {
            LoginModel loginModel = new LoginModel(tfUsername.getText(), tfPassword.getText());
            Player registerResponse = client.register(loginModel);
            if (registerResponse != null) {
                windowOpener.openHomeScreen(windowOpener, registerResponse, event);
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong. Please try again!");
            }
        }
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        Parent loginLoader = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene loginViewScene = new Scene(loginLoader, 600, 400);
        windowOpener.showWindow(event, loginViewScene);
    }
}
