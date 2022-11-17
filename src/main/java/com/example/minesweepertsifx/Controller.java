package com.example.minesweepertsifx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Controller {

    private Stage stage ;
    private Scene scene ;

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
        // Read and verify user input
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
        if (rows < 1 || cols < 1 || rows > 30 || cols > 30 || mines >= rows * cols) {
            mainMenuErrorMessage.setText("Please enter valid options");
        } else {
            minesweeperGrid = new MinesweeperGrid(rows, cols, mines) ;
            GridPane buttonGrid = new GridPane();
            Button[][] referenceGrid ;
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(buttonGrid) ;
            referenceGrid = new Button[this.rows][this.cols] ;

            String btnString;
            for (int curRow = 0; curRow < this.rows; ++curRow) {
                for (int curCol = 0; curCol < this.cols; ++curCol) {
                    int btnNumber = this.rows * curRow + curCol;
                    btnString = "" + minesweeperGrid.getGrid()[curRow][curCol].getFlag() ;
                    Button button = new Button(btnString);
                    button.setId("" + btnNumber);
                    button.setMinHeight(30);
                    button.setMinWidth(30);

                    button.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            int id = Integer.parseInt(button.getId());
                            int thisCol = id % rows;
                            int thisRow = id / rows;
                            minesweeperGrid.click(thisRow, thisCol, true);
                            minesweeperGrid.printGrid();
                            button.setText("" + minesweeperGrid.getGrid()[thisRow][thisCol].getFlag());
                        }
                        else if (event.getButton() == MouseButton.PRIMARY) {
                            int id = Integer.parseInt(button.getId());
                            int thisCol = id % rows;
                            int thisRow = id / rows;
                            minesweeperGrid.click(thisRow, thisCol, false);
                            minesweeperGrid.printGrid();
                            button.setText("" + minesweeperGrid.getGrid()[thisRow][thisCol].getFlag());

                            // Update all the other buttons
                            buttonGrid.getChildren().clear();
                            for (int curRow2 = 0; curRow2 < minesweeperGrid.rows; ++curRow2) {
                                for (int curCol2 = 0; curCol2 < minesweeperGrid.cols; ++curCol2) {
                                    Button replaceButton = referenceGrid[curRow2][curCol2];
                                    replaceButton.setText("" + minesweeperGrid.getGrid()[curRow2][curCol2].getFlag());
                                    referenceGrid[curRow2][curCol2] = replaceButton;
                                    buttonGrid.add(replaceButton, curRow2, curCol2);
                                }
                            }

                            if (minesweeperGrid.checkWinCondition() == true) {
                                try {
                                    Parent root2 = FXMLLoader.load(getClass().getResource("win.fxml"));
                                    Image icon = new Image("MSicon.png");
                                    stage.getIcons().add(icon);
                                    stage.setTitle("MinesweeperTSIFX");
                                    stage.setResizable(false);

                                    stage.setScene(new Scene(root2));
                                    stage.show();
                                } catch (Exception exception) {
                                }
                            }
                            if (minesweeperGrid.checkLoseCondition() == true) {
                                try {
                                    Parent root3 = FXMLLoader.load(getClass().getResource("lose.fxml"));
                                    Image icon = new Image("MSicon.png");
                                    stage.getIcons().add(icon);
                                    stage.setTitle("MinesweeperTSIFX");
                                    stage.setResizable(false);

                                    stage.setScene(new Scene(root3));
                                    stage.show();
                                } catch (Exception exception) {
                                }
                            }
                        }
                    });

                    referenceGrid[curRow][curCol] = button;
                    buttonGrid.add(button, curRow, curCol);
                    //referenceGrid[curCol][curRow] = button;
                    //buttonGrid.add(button, curCol, curRow);
                }
            }
            stage.setScene(scene);
            stage.show() ;
        }


    }
    public void gridButtonClick(ActionEvent e) {

    }

}
