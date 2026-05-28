package com.example.sudoku_game.controller;


import com.example.sudoku_game.model.Music;
import com.example.sudoku_game.view.GameStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the home screen of the Sudoku application.
 *
 * This class handles user interactions on the main menu, including
 * starting a new game and quitting the application. It also manages
 * background music playback through the {@link Music} singleton.
 *
 * This controller is linked to the HomeView.fxml layout file
 * and is automatically instantiated by the JavaFX FXML loader.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see Music
 * @see GameStage
 */

public class HomeController {

    /**
     * Button that starts a new game session when clicked.
     * Injected automatically by the FXML loader.
     */
    @FXML
    private Button btnPlay;

    /**
     * Media player instance reserved for future audio control extensions.
     * Currently, audio is managed through the {@link Music} singleton.
     */
    private MediaPlayer musicPlayer;

    /**
     * Button that exits the application when clicked.
     * Injected automatically by the FXML loader.
     */
    @FXML
    private Button btnQuit;

    /**
     * Initializes the home screen after the FXML layout is loaded.
     *
     * This method is called automatically by the JavaFX framework.
     * It starts playing the background music track on an infinite loop.
     */
    @FXML
    public void initialize() {
        Music.getInstance().playLoop("Starts.mp3");
    }

    /**
     * Handles the play button action to start a new game.
     *
     * Stops the current background music, opens the game stage,
     * and closes the home screen window.
     *
     * @param event the {@link ActionEvent} triggered by clicking the play button.
     * @throws IOException if the GameStage FXML file cannot be loaded.
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {

        // Stop background music before transitioning to the game
        Music.getInstance().stopAndDispose();

        // Close the current home screen window
        new GameStage();
        Stage currentStage = (Stage) btnPlay.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Handles the quit button action to exit the application.
     *
     * Stops any active background music and terminates the application process.
     *
     * @param event the {@link ActionEvent} triggered by clicking the quit button.
     */
    @FXML
    void handleQuit(ActionEvent event) {
        Music.getInstance().stopAndDispose();
        System.exit(0);
    }
}
