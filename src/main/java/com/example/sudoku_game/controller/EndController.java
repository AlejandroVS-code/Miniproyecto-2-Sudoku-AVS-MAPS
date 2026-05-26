package com.example.sudoku_game.controller;

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

    private MediaPlayer endMusicPlayer;



    public void setData(String time, int mistakes, int helps, boolean won) {
        timeText.setText(time);
        mistakeText.setText(String.valueOf(mistakes));
        helpText.setText(String.valueOf(helps));

        if (won) {
            winText.setText("YOU WIN");
            winText.setStyle("-fx-text-fill: #3dbc39;"); // Verde neón [cite: 2]
        } else {
            winText.setText("GAME OVER");
            winText.setStyle("-fx-text-fill: #ff004c;"); // Rojo neón
        }
        playEndMusic("Finish.mp3");
    }

    private void playEndMusic(String fileName) {
        if (endMusicPlayer != null) {
            endMusicPlayer.stop();
            endMusicPlayer.dispose();
        }

        try {
            URL resource = getClass().getResource("/com/example/Sudoku_game/Sounds/" + fileName);
            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                endMusicPlayer = new MediaPlayer(media);
                endMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                endMusicPlayer.setVolume(0.3);
                endMusicPlayer.play();
            }
        } catch (Exception e) {
            System.out.println("Error al reproducir audio final: " + e.getMessage());
        }
    }



    @FXML
    void onAgainClick(ActionEvent event) {
        try {
            if (endMusicPlayer != null) {
                endMusicPlayer.stop();
                endMusicPlayer.dispose(); // Libera el reproductor para evitar que quede en memoria
                endMusicPlayer = null;    // Limpiamos la referencia
            }
            // 1. Cargamos el FXML de la vista del juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Sudoku_game/view/GameView.fxml"));
            Parent root = loader.load();

            // 2. OBTENEMOS EL NUEVO CONTROLADOR
            GameController gameController = loader.getController();

            // 3. CREAMOS LA NUEVA ESCENA
            Scene gameScene = new Scene(root);

            // 4. LE PASAMOS LA ESCENA AL CONTROLADOR (Paso CRÍTICO para el teclado)
            gameController.setupKeyEvents(gameScene);

            // 5. Cambiamos la ventana
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
        if (endMusicPlayer != null) {
            endMusicPlayer.stop();
            endMusicPlayer.dispose();
        }
        System.exit(0);
    }
}