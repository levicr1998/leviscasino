package org.cri.levi.websocketcasinoclient.frontend.logic;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class RouletteBoard {
    GridPane rouletteBoardGridPane;

    public RouletteBoard(GridPane board) {
        this.rouletteBoardGridPane = board;
    }

    public void createRouletteBoard() {
        int number = 1;
        for (int h = 0; h < 3; h++) {
if (h == 1){
    createNewSquare(0,0, h, true);
} else {
   createNewSquare(0, 0, h, false);
}
        }
        for (int i = 1; i <= 12; i++) {
            for (int j = 2; j >= 0; j--) {
            createNewSquare(number, i, j, true);
                number++;
            }
        }
    }

    public void createNewSquare(int number, int horizontal, int vertical, boolean isNumbered) {
       Label label = new Label();
        if (isNumbered) {
           label.setText(Integer.toString(number));
       }
        label.setMinWidth(50);
        label.setMinHeight(50);
        label.setAlignment(Pos.CENTER);
        determineColorSquare(label, number);
        label.setTextFill(Color.WHITE);
        rouletteBoardGridPane.add(label, horizontal, vertical);
    }


    public void determineColorSquare(Label label, int number) {
        if (number == 0){
            setStyleSquareGreen(label);
        }
        if (number > 0 && number <= 9) {
            if (number % 2 == 0) {
                setStyleSquareBlack(label);
            } else {
                setStyleSquareRed(label);
            }
        } else if (number > 9 && number <= 18) {
            if (number % 2 != 0 || number == 10) {
                setStyleSquareBlack(label);
            } else {
                setStyleSquareRed(label);
            }
        } else if (number > 18 && number <= 27) {
            if (number % 2 == 0) {
                setStyleSquareBlack(label);
            } else {
                setStyleSquareRed(label);
            }
        } else if (number > 27) {
            if (number % 2 != 0 || number == 28) {
                setStyleSquareBlack(label);
            } else {
                setStyleSquareRed(label);
            }
        }
    }

    private void setStyleSquareBlack(Label label){
        label.setStyle("-fx-background-color: black; -fx-border-color:white;");
    }

    private void setStyleSquareRed(Label label){
        label.setStyle("-fx-background-color: red; -fx-border-color:white;");
    }

    private void setStyleSquareGreen(Label label){
        label.setStyle("-fx-background-color: green; -fx-border-color:white;");
    }
}
