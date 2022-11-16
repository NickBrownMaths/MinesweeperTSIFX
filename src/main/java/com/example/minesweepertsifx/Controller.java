package com.example.minesweepertsifx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    boolean mainMenu = true ;
    int rows = 10000;
    int cols = 10000;
    int mines = 10000;
    MinesweeperGrid minesweeperGrid;

    @FXML
    private TextField rowsField ;
    @FXML
    private TextField colsField ;
    @FXML
    private TextField minesField ;
    @FXML
    private Label mainMenuErrorMessage = new Label("");

    public void attemptStartGame(ActionEvent e) {
        try {
            rows = Integer.parseInt(rowsField.getText());
            cols = Integer.parseInt(colsField.getText());
            mines = Integer.parseInt(minesField.getText());
        }
        catch (Exception exception) {
            rows = 10000 ;
            cols = rows ;
            mines = rows ;
        }

        if (rows < 1 || cols < 1 || rows > 40 || cols > 40 || mines >= rows * cols) {
            mainMenuErrorMessage.setText("Please enter valid options");
        } else {

            MinesweeperGrid minesweeperGrid = new MinesweeperGrid(rows, cols, mines) ;
            GridPane buttonGrid = new GridPane();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(buttonGrid) ;

            for (int curRow = 0; curRow < this.rows; ++curRow) {
                for (int curCol = 0; curCol < this.cols; ++curCol) {
                    int btnNumber = this.rows * curRow + curCol;
                    String btnString = "" + minesweeperGrid.getGrid()[curRow][curCol].getFlag() ;
                    Button button = new Button(btnString);
                    button.setId("" + btnNumber);
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent btnEvent) {
                            System.out.println(button.getId()) ;
                        }
                    });

                    buttonGrid.add(button, curRow, curCol);
                }
            }
            stage.setScene(scene);
            stage.show() ;
        }


    }
    public void gridButtonClick(ActionEvent e) {

    }

}
