package com.example.minesweepertsifx;

import javafx.event.ActionEvent;
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
            minesweeperGrid = new MinesweeperGrid(cols, rows, mines) ;
            GridPane buttonGrid = new GridPane();
            Button[][] referenceGrid ;
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(buttonGrid) ;
            referenceGrid = new Button[this.cols][this.rows] ;

            String btnString;
            for (int curRow = 0; curRow < this.rows; ++curRow) {
                for (int curCol = 0; curCol < this.cols; ++curCol) {
                    int btnNumber = this.cols * curRow + curCol;
                    btnString = "" + minesweeperGrid.getGrid()[curCol][curRow].getFlag() ;
                    Button button = new Button(btnString);
                    button.setId("" + btnNumber);

                    button.setText("" + btnNumber);

                    button.setMinHeight(30);
                    button.setMinWidth(30);

                    button.setOnMouseClicked(event -> {
                        int id = Integer.parseInt(button.getId());
                        int thisCol = id % cols;
                        int thisRow = id / cols;
                        if (event.getButton() == MouseButton.SECONDARY) {
                            minesweeperGrid.click(thisCol, thisRow, true);
                            minesweeperGrid.printGrid();
                            button.setText("" + minesweeperGrid.getGrid()[thisCol][thisRow].getFlag());
                        }
                        else if (event.getButton() == MouseButton.PRIMARY) {
                            minesweeperGrid.click(thisCol, thisRow, false);
                            minesweeperGrid.printGrid();
                            button.setText("" + minesweeperGrid.getGrid()[thisCol][thisRow].getFlag());

                            // Update all the other buttons
                            buttonGrid.getChildren().clear();
                            for (int curRow2 = 0; curRow2 < minesweeperGrid.rows; ++curRow2) {
                                for (int curCol2 = 0; curCol2 < minesweeperGrid.cols; ++curCol2) {
                                    Button replaceButton = referenceGrid[curCol2][curRow2];
                                    replaceButton.setText("" + minesweeperGrid.getGrid()[curCol2][curRow2].getFlag());
                                    referenceGrid[curCol2][curRow2] = replaceButton;
                                    buttonGrid.add(replaceButton, curCol2, curRow2);
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

                    referenceGrid[curCol][curRow] = button;
                    buttonGrid.add(button, curCol, curRow);
                }
            }
            stage.setScene(scene);
            stage.show() ;
        }
    }
}
