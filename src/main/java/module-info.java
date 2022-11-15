module com.example.minesweepertsifx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minesweepertsifx to javafx.fxml;
    exports com.example.minesweepertsifx;
}