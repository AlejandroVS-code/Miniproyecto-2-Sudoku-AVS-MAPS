package com.example.sudoku_game.controller;

import com.example.sudoku_game.model.Music;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EndController {
    @FXML private Label winText;
    @FXML private Label timeText;
    @FXML private Label mistakeText;
    @FXML private Label helpText;
    @FXML private Button againBtn;





    public void setData(String time, int mistakes, int helps, boolean won) {
        timeText.setText(time);
        mistakeText.setText(String.valueOf(mistakes));
        helpText.setText(String.valueOf(helps));

        if (won) {
            winText.setText("YOU WIN");
            winText.setStyle("-fx-text-fill: #3dbc39;");
        } else {
            winText.setText("GAME OVER");
            winText.setStyle("-fx-text-fill: #ff004c;");
        }
        Music.getInstance().playLoop("Finish.mp3");
    }





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

    @FXML
    void onQuitClick(ActionEvent event) {
        Music.getInstance().stopAndDispose();
        System.exit(0);
    }
}