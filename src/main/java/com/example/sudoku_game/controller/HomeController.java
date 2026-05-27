package com.example.sudoku_game.controller;


import com.example.sudoku_game.model.Music;
import com.example.sudoku_game.view.GameStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;



public class HomeController {
    @FXML
    private Button btnPlay;
    private MediaPlayer musicPlayer;

    @FXML
    private Button btnQuit;

    @FXML
    public void initialize() {
        Music.getInstance().playLoop("Starts.mp3");
    }

    @FXML
    void handlePlay(ActionEvent event) throws IOException {

        Music.getInstance().stopAndDispose();

        new GameStage();
        Stage currentStage = (Stage) btnPlay.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void handleQuit(ActionEvent event) {
        Music.getInstance().stopAndDispose();
        System.exit(0);
    }
}
