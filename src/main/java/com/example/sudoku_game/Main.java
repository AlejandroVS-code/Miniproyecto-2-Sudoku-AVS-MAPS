package com.example.sudoku_game;

import com.example.sudoku_game.view.HomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Main class of the Sudoku Game application.
 *
 * This class is responsible for launching the JavaFX application
 * and initializing the main window of the game.
 *
 * @author Maria Alejandra Pizarro Sarria
 * @author Alejandro Valencia Sandoval
 * @version 1.0
 */
public class Main extends Application {
    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command-line arguments
     */

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application and loads the home stage.
     *
     * @param primaryStage the primary stage provided by JavaFX
     * @throws IOException if an error occurs while loading resources
     *                     or initializing the interface
     */

    @Override
    public void start(Stage primaryStage) throws IOException {

        new HomeStage();
    }
}
