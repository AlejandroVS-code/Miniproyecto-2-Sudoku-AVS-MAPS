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

import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;


/**
 * Main controller for the Sudoku game screen.
 *
 * This class manages all game interactions including cell selection,
 * number placement, hint usage, undo, reset, timer updates, and
 * win detection. It acts as the central coordinator between the
 * game model ({@link Sudoku}, {@link GameStatus}, {@link Timer})
 * and the JavaFX user interface defined in GameView.fxml.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see Sudoku
 * @see GameStatus
 * @see Timer
 * @see KeyboardHandler
 */
public class GameController {
    // -------------------------------------------------------------------------
    // FXML Labels
    // -------------------------------------------------------------------------

    /** Displays the game title at the top of the screen. */
    @FXML private Label titleText;

    /** Displays the number of hints used by the player. */
    @FXML private Label hintsText;

    /** Displays the elapsed game time in MM:SS format. */
    @FXML private Label timeText;

    /** Displays the number of mistakes made by the player. */
    @FXML private Label mistakeText;


    // -------------------------------------------------------------------------
    // FXML Board Cell Buttons (6x6 grid)
    // -------------------------------------------------------------------------

    /** Grid of buttons representing each cell of the 6x6 Sudoku board. */
    @FXML private Button cell_00; @FXML private Button cell_01; @FXML private Button cell_02; @FXML private Button cell_03; @FXML private Button cell_04; @FXML private Button cell_05;
    @FXML private Button cell_10; @FXML private Button cell_11; @FXML private Button cell_12; @FXML private Button cell_13; @FXML private Button cell_14; @FXML private Button cell_15;
    @FXML private Button cell_20; @FXML private Button cell_21; @FXML private Button cell_22; @FXML private Button cell_23; @FXML private Button cell_24; @FXML private Button cell_25;
    @FXML private Button cell_30; @FXML private Button cell_31; @FXML private Button cell_32; @FXML private Button cell_33; @FXML private Button cell_34; @FXML private Button cell_35;
    @FXML private Button cell_40; @FXML private Button cell_41; @FXML private Button cell_42; @FXML private Button cell_43; @FXML private Button cell_44; @FXML private Button cell_45;
    @FXML private Button cell_50; @FXML private Button cell_51; @FXML private Button cell_52; @FXML private Button cell_53; @FXML private Button cell_54; @FXML private Button cell_55;


    // -------------------------------------------------------------------------
    // Model and State Fields
    // -------------------------------------------------------------------------

    /** Maps cell coordinates in "row,col" format to their corresponding UI buttons. */
    private HashMap<String, Button> boardButtons;

    /** The Sudoku game logic model managing the board and solution. */
    private Sudoku sudokuModel;

    /** The coordinate key of the currently selected cell, or null if none. */
    private String selectedKey;

    /** Tracks the player's mistakes and hints during the game session. */
    private GameStatus gameStatus;

    /** Tracks the elapsed time of the current game session. */
    private Timer timer;

    /** JavaFX timeline that drives the per-second timer updates. */
    private Timeline timeline;

    // -------------------------------------------------------------------------
    // Initialization
    // -------------------------------------------------------------------------

    /**
     * Initializes the game screen after the FXML layout is loaded.
     *
     * This method is called automatically by the JavaFX framework.
     * It sets up all models, generates a new board, maps buttons,
     * renders the initial board state, and starts the timer and music.
     */
    @FXML
    public void initialize() {

        sudokuModel = new Sudoku();
        boardButtons = new HashMap<>();
        gameStatus = new GameStatus();
        timer = new Timer();


        sudokuModel.generateBoard();


        mapButtons();


        renderBoard();
        hintsText.setText("Pistas: 0");
        mistakeText.setText("Errores: 0");


        setupTimer();
        Music.getInstance().playLoop("Background.mp3");

    }

    /**
     * Starts the game timer by creating a one-second interval timeline.
     * The timeline runs indefinitely, updating the timer model and UI label
     * each second via {@link TimerHandler}.
     */
    private void setupTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new TimerHandler()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    /**
     * Registers the {@link KeyboardHandler} as the key press listener for the given scene.
     * This enables keyboard-based number input and shortcuts during the game.
     *
     * @param scene the {@link Scene} to attach the keyboard event handler to.
     */
    public void setupKeyEvents(Scene scene) {
        scene.setOnKeyPressed(new KeyboardHandler(this));
    }


    /**
     * Erases the value of the currently selected cell.
     *
     * Removes the cell's value from the model and clears the button text
     * and any error styling. Does nothing if no cell is selected.
     */

    void eraseSelectedCell() {
        if (selectedKey == null) return;

        Button currentButton = boardButtons.get(selectedKey);

        sudokuModel.getBoard().remove(selectedKey);

        currentButton.setText("");
        currentButton.getStyleClass().removeAll("error");
    }

    /**
     * Reveals the full solution on the board by filling all cells with correct values.
     *
     * All buttons are set to their solution values and disabled. If the board
     * is detected as finished after revealing, the win sequence is triggered.
     */
    void revealFullSolution() {
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

    /**
     * Opens a separate window displaying the full solution in a styled grid.
     *
     * Each cell is shown as a labeled grid element with the correct solution value.
     * The window is non-resizable and styled to match the game's visual theme.
     */
    void showSolutionWindow() {
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

    /**
     * Handles clicks on the number buttons (1–6) in the UI.
     * Each method delegates to {@link #placeNumber(int)} with the corresponding value.
     *
     * @param event the {@link javafx.event.ActionEvent} triggered by the number button.
     */

    @FXML void onNumberClick1(javafx.event.ActionEvent event) {placeNumber(1);}
    @FXML void onNumberClick2(javafx.event.ActionEvent event) {placeNumber(2);}
    @FXML void onNumberClick3(javafx.event.ActionEvent event) {placeNumber(3);}
    @FXML void onNumberClick4(javafx.event.ActionEvent event) {placeNumber(4);}
    @FXML void onNumberClick5(javafx.event.ActionEvent event) {placeNumber(5);}
    @FXML void onNumberClick6(javafx.event.ActionEvent event) {placeNumber(6);}

    // -------------------------------------------------------------------------
    // Hint, Reset, and Solution
    // -------------------------------------------------------------------------

    /**
     * Handles the hint button click to reveal the correct value for a cell.
     *
     * If a cell is selected, the hint is applied to that cell. Otherwise,
     * a random empty cell is chosen. The revealed cell is disabled after
     * the hint is applied. The hint counter is incremented and the UI is updated.
     * If the board becomes complete after the hint, the win sequence is triggered.
     *
     * @param event the {@link javafx.event.ActionEvent} triggered by clicking the hint button.
     */

    @FXML
    void onHelpClick(javafx.event.ActionEvent event) {
        if (!gameStatus.canUseHelp()) return;

        String targetKey;

        if (selectedKey != null) {

            targetKey = selectedKey;
        } else {

            Stack<String> emptyCells = new Stack<>();

            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 6; c++) {
                    String key = r + "," + c;
                    if (!sudokuModel.getBoard().containsKey(key)) {
                        emptyCells.push(key);
                    }
                }
            }

            if (emptyCells.isEmpty()) return;


            int randomIndex = new java.util.Random().nextInt(emptyCells.size());
            for (int i = 0; i < randomIndex; i++) {
                emptyCells.pop();
            }
            targetKey = emptyCells.pop();
        }


        int correctValue = sudokuModel.getHelpValue(targetKey);
        sudokuModel.validateAndPlace(targetKey, correctValue);

        Button targetButton = boardButtons.get(targetKey);
        targetButton.setText(String.valueOf(correctValue));
        targetButton.getStyleClass().removeAll("error", "selected");
        targetButton.setDisable(true);


        gameStatus.addHelp();
        hintsText.setText(gameStatus.getHelpsFormatted());

        if (sudokuModel.isGameFinished()) {
            handleWin();
        }

        selectedKey = null;
    }

    /**
     * Resets the game to a fresh state with a newly generated puzzle.
     *
     * Resets the timer, game status, and board model. Clears all button
     * styles and re-renders the board. The move history is also cleared.
     *
     * @param event the {@link javafx.event.ActionEvent} triggered by clicking the reset button.
     */
    @FXML void onResetClick(javafx.event.ActionEvent event) {


        timer.reset();
        gameStatus.reset();
        sudokuModel.generateBoard();
        while (sudokuModel.popMove() != null) {}


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


    }

    /**
     * Handles a cell button click event to select or deselect a board cell.
     *
     * Applies the "selected" style to the clicked cell and removes it from
     * the previously selected cell. Disabled cells cannot be selected.
     *
     * @param event the {@link javafx.event.ActionEvent} triggered by clicking a cell button.
     */
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

    /**
     * Places a number in the currently selected cell and validates it against the solution.
     *
     * If the value is correct, it is placed on the board, any error styling is removed,
     * and the move is recorded in the history stack. If incorrect, a mistake is registered,
     * the UI is updated, and the cell is marked with the "error" style. After each correct
     * placement, the game checks whether the board is complete.
     *
     * @param number the number to place in the selected cell.
     */
    void placeNumber(int number) {
        if (selectedKey == null) return;

        Button currentButton = boardButtons.get(selectedKey);

        if (sudokuModel.validateAndPlace(selectedKey, number)) {
            currentButton.setText(String.valueOf(number));
            currentButton.getStyleClass().remove("error");
            sudokuModel.pushMove(selectedKey);
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


            sudokuModel.pushMove("e:" + selectedKey);
        }
    }
    /**
     * Handles the win condition when the player completes the board correctly.
     *
     * Stops the timer, disables all board cells, and triggers the transition
     * to the end screen after a short delay.
     */
    private void handleWin() {
        if (timeline != null) timeline.stop();
        boardButtons.forEach((key, btn) -> btn.setDisable(true));
        changeToEndStage(timer.getFormattedTime(),
                gameStatus.getMistakes(),
                gameStatus.getHelpsUsed(),
                true);
    }

    /**
     * Transitions to the end screen after a 3-second pause.
     *
     * Loads the EndView.fxml layout, passes the game result data to
     * {@link EndController}, stops the music, and replaces the current scene.
     *
     * @param time     the elapsed game time in MM:SS format.
     * @param mistakes the total number of mistakes made by the player.
     * @param helps    the total number of hints used by the player.
     * @param won      whether the player won the game.
     */
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


    /**
     * Maps each FXML cell button to its coordinate key in "row,col" format.
     *
     * This map is used throughout the controller to access buttons by position.
     */

    private void mapButtons() {

        boardButtons.put("0,0", cell_00); boardButtons.put("0,1", cell_01); boardButtons.put("0,2", cell_02); boardButtons.put("0,3", cell_03); boardButtons.put("0,4", cell_04); boardButtons.put("0,5", cell_05);
        boardButtons.put("1,0", cell_10); boardButtons.put("1,1", cell_11); boardButtons.put("1,2", cell_12); boardButtons.put("1,3", cell_13); boardButtons.put("1,4", cell_14); boardButtons.put("1,5", cell_15);
        boardButtons.put("2,0", cell_20); boardButtons.put("2,1", cell_21); boardButtons.put("2,2", cell_22); boardButtons.put("2,3", cell_23); boardButtons.put("2,4", cell_24); boardButtons.put("2,5", cell_25);
        boardButtons.put("3,0", cell_30); boardButtons.put("3,1", cell_31); boardButtons.put("3,2", cell_32); boardButtons.put("3,3", cell_33); boardButtons.put("3,4", cell_34); boardButtons.put("3,5", cell_35);
        boardButtons.put("4,0", cell_40); boardButtons.put("4,1", cell_41); boardButtons.put("4,2", cell_42); boardButtons.put("4,3", cell_43); boardButtons.put("4,4", cell_44); boardButtons.put("4,5", cell_45);
        boardButtons.put("5,0", cell_50); boardButtons.put("5,1", cell_51); boardButtons.put("5,2", cell_52); boardButtons.put("5,3", cell_53); boardButtons.put("5,4", cell_54); boardButtons.put("5,5", cell_55);
    }

    /**
     * Renders the current board state onto the UI button grid.
     *
     * Pre-filled cells display their value and are disabled to prevent editing.
     * Empty cells are cleared and enabled for player input.
     */
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

    /**
     * Undoes the player's last move by popping it from the move history stack.
     *
     * Removes the value from the model and clears the button text,
     * error, and selected styles. Does nothing if the history is empty.
     *
     * @param event the {@link javafx.event.ActionEvent} triggered by clicking the undo button.
     */
    @FXML
    void onBackClick(javafx.event.ActionEvent event) {
        String lastKey = sudokuModel.popMove();
        if (lastKey == null) return;


        boolean wasError = lastKey.startsWith("e:");
        String realKey = wasError ? lastKey.substring(2) : lastKey;

        Button btn = boardButtons.get(realKey);
        if (btn == null) return;

        if (wasError) {

            btn.setText("");
            btn.getStyleClass().removeAll("error", "selected");
        } else {

            sudokuModel.getBoard().remove(realKey);
            btn.setText("");
            btn.getStyleClass().removeAll("error", "selected");
            btn.setDisable(false);
        }
    }

    /**
     * Inner class that handles the per-second timer tick event.
     *
     * On each tick, it increments the timer model by one second
     * and updates the time label in the user interface.
     */
    private class TimerHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
        /**
         * Called once per second by the {@link Timeline}.
         *
         * Updates the timer model and refreshes the displayed time label.
         *
         * @param event the {@link javafx.event.ActionEvent} fired by the timeline tick.
         */
        @Override
        public void handle(javafx.event.ActionEvent event) {
            timer.addSecond();
            timeText.setText(timer.getFormattedTime());
        }
    }

}

