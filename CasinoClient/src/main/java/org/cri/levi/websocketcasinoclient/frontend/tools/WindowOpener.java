package org.cri.levi.websocketcasinoclient.frontend.tools;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cri.levi.websocketcasinoclient.frontend.controllers.HomeController;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.io.IOException;

public class WindowOpener {
    public void showWindow(ActionEvent event, Scene scene){
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void openHomeScreen(WindowOpener windowOpener, Player player, ActionEvent event) throws IOException {
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
        Parent homeLoaderParent = homeLoader.load();
        HomeController homeController = homeLoader.getController();
        homeController.setPlayer(player);
        Scene homeViewScene = new Scene(homeLoaderParent, 600, 400);
        windowOpener.showWindow(event, homeViewScene);
    }
}
