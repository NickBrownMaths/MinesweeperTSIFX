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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Controller {
    private Stage stage ;
    private Scene scene ;
    int rows = 10000;
    int cols = 10000;
    int mines = 10000;
    MinesweeperGrid minesweeperGrid;

    @FXML
    private TextField rowsField = new TextField();
    @FXML
    private TextField colsField = new TextField();
    @FXML
    private TextField minesField = new TextField();
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
        }
        else {
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
                    button.setMinHeight(30);
                    button.setMinWidth(30);
                    button.setFont(Font.font("Consolas", FontWeight.BOLD, 18));

                    button.setOnMouseClicked(event -> {
                        int id = Integer.parseInt(button.getId());
                        int thisCol = id % cols;
                        int thisRow = id / cols;
                        if (event.getButton() == MouseButton.SECONDARY) {
                            minesweeperGrid.click(thisCol, thisRow, true);
                            minesweeperGrid.printGrid();
                            button.setText("" + minesweeperGrid.getGrid()[thisCol][thisRow].getFlag());
                            char flg = minesweeperGrid.getGrid()[thisCol][thisRow].getFlag() ;
                            if      (flg == 'P') {button.setTextFill(Color.ORANGE);}
                            else if (flg == '.') {button.setTextFill(Color.BLACK);}
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

                                    char flg = minesweeperGrid.getGrid()[curCol2][curRow2].getFlag() ;
                                    if      (flg == '1') {replaceButton.setTextFill(Color.BLUE);}
                                    else if (flg == '2') {replaceButton.setTextFill(Color.GREEN);}
                                    else if (flg == '3') {replaceButton.setTextFill(Color.RED);}
                                    else if (flg == '4') {replaceButton.setTextFill(Color.NAVY);}
                                    else if (flg == '5') {replaceButton.setTextFill(Color.DARKRED);}
                                    else if (flg == '6') {replaceButton.setTextFill(Color.TEAL);}
                                    else if (flg == '7') {replaceButton.setTextFill(Color.BLACK);}
                                    else if (flg == '8') {replaceButton.setTextFill(Color.DARKGRAY);}
                                    else if (flg == 'P') {replaceButton.setTextFill(Color.ORANGE);}
                                    else if (flg == '.') {replaceButton.setTextFill(Color.BLACK);}
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
    public void playAgain(ActionEvent e) {
        try {
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Image icon = new Image("MSicon.png");
            stage.getIcons().add(icon);
            stage.setTitle("MinesweeperTSIFX");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            //autofillTextfields(cols, rows, mines);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void autofillTextfields(int c, int r, int m) {
        try {
            rowsField.setText("" + r);
            colsField.setText("" + c);
            minesField.setText("" + m);
            System.out.println("autofill no throw");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
