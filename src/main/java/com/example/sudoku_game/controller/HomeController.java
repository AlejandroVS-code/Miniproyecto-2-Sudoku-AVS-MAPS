package com.example.sudoku_game.controller;

import com.example.sudoku_game.controller.HomeController;
import com.example.sudoku_game.view.GameStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class HomeController {
    @FXML
    private Button btnPlay;
    private MediaPlayer musicPlayer;

    @FXML
    private Button btnQuit;

    @FXML
    public void initialize() {
        playMenuMusic();
    }
    private void playMenuMusic() {
        try {
            // Asegúrate de que la ruta sea correcta según tu estructura de carpetas
            URL resource = getClass().getResource("/com/example/sudoku_game/Sounds/Starts.mp3");

            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                musicPlayer = new MediaPlayer(media);

                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Bucle infinito
                musicPlayer.setVolume(0.3); // Volumen moderado para el menú
                musicPlayer.play();
            } else {
                System.out.println("No se encontró el archivo de música de inicio.");
            }
        } catch (Exception e) {
            System.out.println("Error al reproducir música de inicio: " + e.getMessage());
        }
    }
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        // DETENER la música antes de cambiar de ventana
        if (musicPlayer != null) {
            musicPlayer.stop();
        }

        new GameStage();
        Stage currentStage = (Stage) btnPlay.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void handleQuit(ActionEvent event) {
        System.exit(0);
    }
}
