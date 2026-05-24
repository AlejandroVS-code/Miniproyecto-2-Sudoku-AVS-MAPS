module com.example.sudoku_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.sudoku_game to javafx.fxml;
    exports com.example.sudoku_game;
    opens com.example.sudoku_game.controller to javafx.fxml;
    opens com.example.sudoku_game.view to javafx.fxml;
}