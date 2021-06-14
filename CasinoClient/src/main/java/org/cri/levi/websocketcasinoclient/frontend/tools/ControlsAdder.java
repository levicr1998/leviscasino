package org.cri.levi.websocketcasinoclient.frontend.tools;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static javafx.scene.paint.Color.WHITE;

public class ControlsAdder {

    public Label newLabel(double layoutX, double layoutY, String text, boolean bold) {
        Label l = new Label();
        if (bold) {
            l.setStyle("-fx-font-weight: bold");
        }
        l.setLayoutX(layoutX);
        l.setLayoutY(layoutY);
        l.setText(text);
        return l;


    }

    public Button getLobbyButton() {
        Button b = new Button();
        b.getStyleClass().add("SignUpButton");
        b.setStyle("-fx-font-weight: bold");
        b.setLayoutX(245);
        b.setLayoutY(16);
        b.setMnemonicParsing(false);
        b.setPrefHeight(29);
        b.setPrefWidth(48);
        b.setText("Join");
        b.setTextFill(WHITE);
        return b;
    }
}
