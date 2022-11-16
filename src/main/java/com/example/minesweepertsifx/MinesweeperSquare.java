package com.example.minesweepertsifx;

import javafx.scene.control.Button;

public class MinesweeperSquare {

    char flag = '.';
    boolean isMine = false;

    // [SPACE] empty with no adjacent mines
    // Q mine
    // P flag
    // 12345678 neighbour mines
    // . unknown

    public void MinesweeperSquare () {
    }
    public char getFlag() {
        return flag;
    }

    public boolean isMine() {
        return isMine;
    }
}
