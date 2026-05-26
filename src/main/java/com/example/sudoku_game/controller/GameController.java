package com.example.sudoku_game.controller;

import com.example.sudoku_game.model.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class GameController {
    @FXML private Label titleText;
    @FXML private Label hintsText;
    @FXML private Label timeText;
    @FXML private Label mistakeText;
    @FXML private Button cell_00; @FXML private Button cell_01; @FXML private Button cell_02; @FXML private Button cell_03; @FXML private Button cell_04; @FXML private Button cell_05;
    @FXML private Button cell_10; @FXML private Button cell_11; @FXML private Button cell_12; @FXML private Button cell_13; @FXML private Button cell_14; @FXML private Button cell_15;
    @FXML private Button cell_20; @FXML private Button cell_21; @FXML private Button cell_22; @FXML private Button cell_23; @FXML private Button cell_24; @FXML private Button cell_25;
    @FXML private Button cell_30; @FXML private Button cell_31; @FXML private Button cell_32; @FXML private Button cell_33; @FXML private Button cell_34; @FXML private Button cell_35;
    @FXML private Button cell_40; @FXML private Button cell_41; @FXML private Button cell_42; @FXML private Button cell_43; @FXML private Button cell_44; @FXML private Button cell_45;
    @FXML private Button cell_50; @FXML private Button cell_51; @FXML private Button cell_52; @FXML private Button cell_53; @FXML private Button cell_54; @FXML private Button cell_55;

    private HashMap<String, Button> boardButtons;
    private Sudoku sudokuModel;
    private String selectedKey;
    private GameStatus gameStatus;
    private Timer timer;
    private Timeline timeline;


    @FXML
    public void initialize() {

        sudokuModel = new Sudoku();
        boardButtons = new HashMap<>();
        gameStatus = new GameStatus();
        timer = new Timer();


        sudokuModel.generateBoard();


        mapButtons();


        renderBoard();
        hintsText.setText("Pistas: 0/3");
        mistakeText.setText("Errores: 0/3");
        setupTimer();
        Music.getInstance().playLoop("Background.mp3");

    }

    private void setupTimer() {

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timer.addSecond();
            timeText.setText(timer.getFormattedTime());
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setupKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            String key = event.getText().toLowerCase();

            if (key.matches("[1-6]")) {
                placeNumber(Integer.parseInt(key));
            }

            if (key.equals("ñ")) {
                revealFullSolution();
            } else if (key.equals("n")) {
                showSolutionWindow();
            }
        });
    }

    private void revealFullSolution() {
        HashMap<String, Integer> sol = sudokuModel.getFullSolution();

        boardButtons.forEach((key, btn) -> {
            Integer value = sol.get(key);
            if (value != null) {
                btn.setText(value.toString());
                btn.getStyleClass().remove("error");
                btn.setDisable(true);


                sudokuModel.validateAndPlace(key, value);
            }
        });


        if (sudokuModel.isGameFinished()) {
            handleWin();
        }
    }

    private void showSolutionWindow() {
        Stage solutionStage = new Stage();
        solutionStage.setTitle("Solución Generada (Stack)");
        solutionStage.setResizable(false);


        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setStyle("-fx-background-color: #0a0018; -fx-padding: 15; -fx-hgap: 8; -fx-vgap: 8;");

        HashMap<String, Integer> sol = sudokuModel.getFullSolution();

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                String key = r + "," + c;
                Label lbl = new Label(sol.get(key).toString());
                lbl.setStyle("-fx-text-fill: #00e0ff; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #00e0ff; " +
                        "-fx-padding: 10; " +
                        "-fx-border-radius: 5; " +
                        "-fx-alignment: center;");
                lbl.setMinWidth(40);
                grid.add(lbl, c, r);
            }
        }

        Scene scene = new Scene(grid);
        solutionStage.setScene(scene);
        solutionStage.show();
    }










    @FXML void onNumberClick1(javafx.event.ActionEvent event) {placeNumber(1);}
    @FXML void onNumberClick2(javafx.event.ActionEvent event) {placeNumber(2);}
    @FXML void onNumberClick3(javafx.event.ActionEvent event) {placeNumber(3);}
    @FXML void onNumberClick4(javafx.event.ActionEvent event) {placeNumber(4);}
    @FXML void onNumberClick5(javafx.event.ActionEvent event) {placeNumber(5);}
    @FXML void onNumberClick6(javafx.event.ActionEvent event) {placeNumber(6);}

    @FXML
    void onHelpClick(javafx.event.ActionEvent event) {
        if (selectedKey == null) return;

        if (gameStatus.canUseHelp()) {

            int correctValue = sudokuModel.getHelpValue(selectedKey);


            sudokuModel.validateAndPlace(selectedKey, correctValue);

            Button currentButton = boardButtons.get(selectedKey);
            currentButton.setText(String.valueOf(correctValue));


            currentButton.getStyleClass().removeAll("error", "selected");
            currentButton.setDisable(true);


            gameStatus.addHelp();
            hintsText.setText(gameStatus.getHelpsFormatted());


            if (sudokuModel.isGameFinished()) {
                handleWin();
            }

            selectedKey = null;
        }
    }




    @FXML void onResetClick(javafx.event.ActionEvent event) {

        timer.reset();
        gameStatus.reset();
        sudokuModel.generateBoard();


        timeText.setText(timer.getFormattedTime());
        mistakeText.setText(gameStatus.getMistakesFormatted());
        hintsText.setText(gameStatus.getHelpsFormatted());


        for (String key : boardButtons.keySet()) {
            Button btn = boardButtons.get(key);


            btn.getStyleClass().removeAll("selected", "error");


            btn.setDisable(false);
        }
        renderBoard();


        selectedKey = null;

        System.out.println("Juego reiniciado con éxito.");
    }


    @FXML
    void handleCellClick(javafx.event.ActionEvent event) {

        Button clickedButton = (Button) event.getSource();


        if (clickedButton.isDisable()) return;

        for (String key : boardButtons.keySet()) {
            if (boardButtons.get(key).equals(clickedButton)) {


                if (selectedKey != null) {
                    boardButtons.get(selectedKey).getStyleClass().remove("selected");
                }


                selectedKey = key;


                if (!clickedButton.getStyleClass().contains("selected")) {
                    clickedButton.getStyleClass().add("selected");
                }


                break;
            }
        }

    }

    private void placeNumber(int number) {

        if (selectedKey == null) return;

        Button currentButton = boardButtons.get(selectedKey);


        if (sudokuModel.validateAndPlace(selectedKey, number)) {

            currentButton.setText(String.valueOf(number));
            currentButton.getStyleClass().remove("error");
            System.out.println("Celdas llenas: " + sudokuModel.getBoard().size());

            if (sudokuModel.isGameFinished()) {
                handleWin();
            }

        } else {

            gameStatus.addMistake();

            currentButton.setText(String.valueOf(number));
            if (!currentButton.getStyleClass().contains("error")) {
                currentButton.getStyleClass().add("error");
            }


            mistakeText.setText(gameStatus.getMistakesFormatted());
            if (gameStatus.isGameOver()) {
                handleLoss();
            }

        }


    }

    private void handleWin() {
        if (timeline != null) timeline.stop();

        boardButtons.forEach((key, btn) -> btn.setDisable(true));


        changeToEndStage(timer.getFormattedTime(),
                gameStatus.getMistakes(),
                gameStatus.getHelpsUsed(),
                true);
    }

    private void handleLoss() {

        if (timeline != null) timeline.stop();
        boardButtons.forEach((key, btn) -> btn.setDisable(true));


        changeToEndStage(timer.getFormattedTime(),
                gameStatus.getMistakes(),
                gameStatus.getHelpsUsed(),
                false);
    }



    private void changeToEndStage(String time, int mistakes, int helps, boolean won) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));


        pause.setOnFinished(event -> {
            try {
                Music.getInstance().stopAndDispose();

                var resource = getClass().getResource("/com/example/sudoku_game/view/EndView.fxml");

                if (resource == null) {
                    System.out.println("Error: No se encontró el archivo FXML.");
                    return;
                }


                FXMLLoader loader = new FXMLLoader(resource);
                Parent root = loader.load();

                EndController endController = loader.getController();

                endController.setData(time, mistakes, helps, won);

                Stage stage = (Stage) timeText.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        pause.play();
    }








    private void mapButtons() {

        boardButtons.put("0,0", cell_00); boardButtons.put("0,1", cell_01); boardButtons.put("0,2", cell_02); boardButtons.put("0,3", cell_03); boardButtons.put("0,4", cell_04); boardButtons.put("0,5", cell_05);
        boardButtons.put("1,0", cell_10); boardButtons.put("1,1", cell_11); boardButtons.put("1,2", cell_12); boardButtons.put("1,3", cell_13); boardButtons.put("1,4", cell_14); boardButtons.put("1,5", cell_15);
        boardButtons.put("2,0", cell_20); boardButtons.put("2,1", cell_21); boardButtons.put("2,2", cell_22); boardButtons.put("2,3", cell_23); boardButtons.put("2,4", cell_24); boardButtons.put("2,5", cell_25);
        boardButtons.put("3,0", cell_30); boardButtons.put("3,1", cell_31); boardButtons.put("3,2", cell_32); boardButtons.put("3,3", cell_33); boardButtons.put("3,4", cell_34); boardButtons.put("3,5", cell_35);
        boardButtons.put("4,0", cell_40); boardButtons.put("4,1", cell_41); boardButtons.put("4,2", cell_42); boardButtons.put("4,3", cell_43); boardButtons.put("4,4", cell_44); boardButtons.put("4,5", cell_45);
        boardButtons.put("5,0", cell_50); boardButtons.put("5,1", cell_51); boardButtons.put("5,2", cell_52); boardButtons.put("5,3", cell_53); boardButtons.put("5,4", cell_54); boardButtons.put("5,5", cell_55);
    }


    private void renderBoard() {
        HashMap<String, Integer> currentBoard = sudokuModel.getBoard();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                String key = row + "," + col;
                Button btn = boardButtons.get(key);

                if (btn != null) {
                    Integer value = currentBoard.get(key);
                    if (value != null) {
                        btn.setText(value.toString());
                        btn.setDisable(true);
                    } else {
                        btn.setText("");
                        btn.setDisable(false);
                    }
                }
            }
        }
    }
}

