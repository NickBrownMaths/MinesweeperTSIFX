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
    private TextField rowsField = new TextField("" + this.rows);
    @FXML
    private TextField colsField = new TextField("" + this.cols);
    @FXML
    private TextField minesField = new TextField("" + this.mines);
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

            // Create some text to show the game status
            Label helpLabel0 = new Label(" Mines: ") ;
            Label helpLabel1 = new Label("   " + minesweeperGrid.getMines()) ;
            Label helpLabel2 = new Label(" Flags: ") ;
            Label helpLabel3 = new Label("   " + minesweeperGrid.getPlantedFlags()) ;

            // Align the labels
            if      (minesweeperGrid.getMines() > 99) {helpLabel1.setText(" "   + minesweeperGrid.getMines()) ;}
            else if (minesweeperGrid.getMines() >  9) {helpLabel1.setText("  "  + minesweeperGrid.getMines()) ;}
            else                                      {helpLabel1.setText("   " + minesweeperGrid.getMines()) ;}
            if      (minesweeperGrid.getPlantedFlags() > 99) {helpLabel3.setText(" "   + minesweeperGrid.getPlantedFlags()) ;}
            else if (minesweeperGrid.getPlantedFlags() >  9) {helpLabel3.setText("  "  + minesweeperGrid.getPlantedFlags()) ;}
            else                                             {helpLabel3.setText("   " + minesweeperGrid.getPlantedFlags()) ;}

            helpLabel0.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
            helpLabel1.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
            helpLabel2.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
            helpLabel3.setFont(Font.font("Consolas", FontWeight.BOLD, 20));

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
                            button.setText("" + minesweeperGrid.getGrid()[thisCol][thisRow].getFlag());

                            // Align the labels
                            if      (minesweeperGrid.getPlantedFlags() > 99) {helpLabel3.setText(" "   + minesweeperGrid.getPlantedFlags()) ;}
                            else if (minesweeperGrid.getPlantedFlags() >  9) {helpLabel3.setText("  "  + minesweeperGrid.getPlantedFlags()) ;}
                            else                                             {helpLabel3.setText("   " + minesweeperGrid.getPlantedFlags()) ;}

                            char flg = minesweeperGrid.getGrid()[thisCol][thisRow].getFlag() ;
                            if      (flg == '@') {button.setTextFill(Color.DARKORANGE);}
                            else if (flg == '.') {button.setTextFill(Color.BLACK);}
                        }
                        else if (event.getButton() == MouseButton.PRIMARY) {
                            minesweeperGrid.click(thisCol, thisRow, false);
                            //minesweeperGrid.printGrid();
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
                                    else if (flg == '@') {replaceButton.setTextFill(Color.DARKORANGE);}
                                    else if (flg == '.') {replaceButton.setTextFill(Color.BLACK);}
                                }
                            }

                            // Align the labels
                            if      (minesweeperGrid.getPlantedFlags() > 99) {helpLabel3.setText(" "   + minesweeperGrid.getPlantedFlags()) ;}
                            else if (minesweeperGrid.getPlantedFlags() >  9) {helpLabel3.setText("  "  + minesweeperGrid.getPlantedFlags()) ;}
                            else                                             {helpLabel3.setText("   " + minesweeperGrid.getPlantedFlags()) ;}

                            buttonGrid.add(helpLabel0, cols, 0);
                            buttonGrid.add(helpLabel1, cols, 1);
                            buttonGrid.add(helpLabel2, cols, 2);
                            buttonGrid.add(helpLabel3, cols, 3);

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

            // Align the labels
            if      (minesweeperGrid.getPlantedFlags() > 99) {helpLabel3.setText(" "   + minesweeperGrid.getPlantedFlags()) ;}
            else if (minesweeperGrid.getPlantedFlags() >  9) {helpLabel3.setText("  "  + minesweeperGrid.getPlantedFlags()) ;}
            else                                             {helpLabel3.setText("   " + minesweeperGrid.getPlantedFlags()) ;}

            buttonGrid.add(helpLabel0, cols, 0);
            buttonGrid.add(helpLabel1, cols, 1);
            buttonGrid.add(helpLabel2, cols, 2);
            buttonGrid.add(helpLabel3, cols, 3);

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

            //autofillTextfields(cols, rows, mines);
            stage.show();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void autofillTextfieldsEasy(ActionEvent e) {
        try {
            rowsField.setText("8");
            colsField.setText("8");
            minesField.setText("10");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void autofillTextfieldsMid(ActionEvent e) {
        try {
            rowsField.setText("16");
            colsField.setText("16");
            minesField.setText("40");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void autofillTextfieldsHard(ActionEvent e) {
        try {
            rowsField.setText("24");
            colsField.setText("30");
            minesField.setText("99");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
