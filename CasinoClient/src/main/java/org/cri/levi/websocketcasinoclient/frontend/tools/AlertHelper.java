package org.cri.levi.websocketcasinoclient.frontend.tools;

import javafx.scene.control.Alert;

public class AlertHelper {

    private AlertHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
