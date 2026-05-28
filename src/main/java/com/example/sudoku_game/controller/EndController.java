package com.example.sudoku_game.controller;

import com.example.sudoku_game.model.Music;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the end screen of the Sudoku application.
 *
 * This class handles the final screen displayed after a game session ends.
 * It shows the player's results including time, mistakes, and hints used,
 * and provides options to play again or quit the application.
 *
 * This controller is linked to the EndView.fxml layout file and receives
 * game result data from {@link GameController} via the {@link #setData} method.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see GameController
 * @see Music
 */
public class EndController {

    /** Label that displays whether the player won or lost. */
    @FXML private Label winText;

    /** Label that displays the total elapsed game time in MM:SS format. */
    @FXML private Label timeText;

    /** Label that displays the total number of mistakes made by the player. */
    @FXML private Label mistakeText;

    /** Label that displays the total number of hints used by the player. */
    @FXML private Label helpText;

    /** Button that allows the player to start a new game session. */
    @FXML private Button againBtn;

    /**
     * Populates the end screen with the game result data and starts the finish music.
     *
     * Sets the time, mistake count, and hint count labels with the provided values.
     * If the player won, the win label is updated with a success message and styled
     * in green. The finish music track is started after the data is applied.
     *
     * @param time     the elapsed game time in MM:SS format.
     * @param mistakes the total number of mistakes made during the game.
     * @param helps    the total number of hints used during the game.
     * @param won      whether the player completed the board successfully.
     */

    public void setData(String time, int mistakes, int helps, boolean won) {
        timeText.setText(time);
        mistakeText.setText(String.valueOf(mistakes));
        helpText.setText(String.valueOf(helps));

        if (won) {
            winText.setText("YOU WIN");
            winText.setStyle("-fx-text-fill: #3dbc39;");
        }
        Music.getInstance().playLoop("Finish.mp3");
    }

    /**
     * Handles the play again button click to start a new game session.
     *
     * Stops the current music, loads the GameView.fxml layout, sets up
     * keyboard events on the new scene, and replaces the current stage scene
     * with the game screen. Logs an error message if the FXML cannot be loaded.
     *
     * @param event the {@link ActionEvent} triggered by clicking the play again button.
     */

    @FXML
    void onAgainClick(ActionEvent event) {
        try {

            Music.getInstance().stopAndDispose();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudoku_game/view/GameView.fxml"));
            Parent root = loader.load();


            GameController gameController = loader.getController();

            Scene gameScene = new Scene(root);

            gameController.setupKeyEvents(gameScene);


            Stage stage = (Stage) againBtn.getScene().getWindow();
            stage.setScene(gameScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al intentar reiniciar el juego.");
        }
    }

    /**
     * Handles the quit button click to exit the application.
     *
     * Stops any active background music and terminates the application process.
     *
     * @param event the {@link ActionEvent} triggered by clicking the quit button.
     */
    @FXML
    void onQuitClick(ActionEvent event) {
        Music.getInstance().stopAndDispose();
        System.exit(0);
    }
}