package com.example.minesweepertsifx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    boolean mainMenu = true;

    @Override
    public void start(Stage stage) throws Exception {

        if (mainMenu){
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Image icon = new Image("MSicon.png");
            stage.getIcons().add(icon);
            stage.setTitle("MinesweeperTSIFX");
            stage.setResizable(false);

            stage.setScene(new Scene(root));
        }
        /*
        if (mainMenu == false) {

            GridPane grid = new GridPane();

            int RRR = 40;
            int CCC = 40;

            for (int curRow = 0; curRow < RRR; ++curRow) {
                for (int curCol = 0; curCol < CCC; ++curCol) {
                    int btnNumber = RRR * curRow + curCol;
                    Button button = new Button(".");
                    grid.add(button, curRow, curCol);

                }
            }
            stage.setScene(new Scene(grid));
        }
        */

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

        /*
        Scanner input = new Scanner(System.in) ;

        MinesweeperGrid minesweeperGrid = new MinesweeperGrid(0, 0, 0) ;

        boolean stillPlaying = true ;
        boolean setupComplete = false;

        while (stillPlaying) {

            while (setupComplete == false) {

                int rowsSetup = 1000000 ;
                int colsSetup = 1000000 ;
                int minesSetup = 1000000 ;
                try {
                    System.out.println("How many rows, columns and mines?\n(Input three whole numbers, rows and columns 40 or lower, mines must all fit)");
                    rowsSetup = input.nextInt();
                    colsSetup = input.nextInt();
                    minesSetup = input.nextInt();
                } catch (Exception e) {
                    System.out.println("I can do this all day. Please enter a valid setup.");
                    input.nextLine() ;
                }
                if (rowsSetup < 1 || colsSetup < 1 || rowsSetup > 40 || colsSetup > 40 || minesSetup >= rowsSetup * colsSetup) {
                    System.out.println("Your input wasn't quite correct. Please try again.");
                }
                else {
                    setupComplete = true ;
                    minesweeperGrid = new MinesweeperGrid(rowsSetup, colsSetup, minesSetup) ;
                }
            }

            minesweeperGrid.printGrid();

            boolean noValidCommand = true ;
            while (noValidCommand) {
                int clickRow = 1000000 ;
                int clickCol = 1000000 ;
                char plantFlag = 0 ;

                try {
                    clickRow = input.nextInt();
                    clickCol = input.nextInt();
                    plantFlag = input.next().charAt(0) ;
                }
                catch (Exception e) {
                    String line = input.nextLine();
                }

                if (clickRow != 1000000 && clickCol != 1000000
                        && clickRow < minesweeperGrid.rows && clickCol < minesweeperGrid.cols
                        && (plantFlag == 'r' || plantFlag == 'f')) {
                    noValidCommand = false;
                }
                else {
                    System.out.println("Please enter a valid choice. Use \"row col r\" to reveal, use \"row col f\" to plant a flag.");
                }

                // If the user has given a valid command execute it
                if (noValidCommand == false) {
                    boolean pf = false ;
                    if (plantFlag == 'f') { pf = true; }
                    minesweeperGrid.click(clickRow, clickCol, pf);
                }
                if (minesweeperGrid.gameLost == true) {
                    System.out.println("Congratulations! You Lose!");
                    minesweeperGrid.printSolution();
                    stillPlaying = false ;
                }
            }

            // Check win condition
            if (minesweeperGrid.checkWinCondition()) {
                minesweeperGrid.printGrid();
                System.out.println("Congratulations! you win!");
                stillPlaying = false ;
            }
        }
        */
    }

}