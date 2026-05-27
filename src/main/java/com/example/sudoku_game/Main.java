package com.example.sudoku_game;

import com.example.sudoku_game.view.HomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The main entry point of the Sudoku game application.
 * This class extends the JavaFX {@link Application} class and is responsible
 * for bootstrapping the runtime environment and launching the initial user interface.
 * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public class Main extends Application {
    /**
     * The main method of the program.
     * Serves as the standard execution entry point fallback. It delegates control
     * to the native JavaFX framework toolkit to initialize the application lifecycle.
     * * @param args Command-line arguments passed to the application at launch.
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Initializes and displays the primary window container of the application.
     * This lifecycle method is triggered automatically by JavaFX after the environment is ready.
     * It instantiates the opening menu display scene stage.
     * * @param primaryStage The default root window container provided by the JavaFX platform (unused).
     * @throws IOException If an error occurs while loading the FXML view layout configurations for the scene.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        new HomeStage();
    }
}
