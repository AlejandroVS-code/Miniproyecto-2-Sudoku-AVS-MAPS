package com.example.sudoku_game.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles keyboard input events for the Sudoku game.
 *
 * This class implements {@link EventHandler} for {@link KeyEvent} and acts
 * as the keyboard listener bound to the game scene. It delegates each
 * recognized key action to the appropriate method in {@link GameController},
 * keeping input handling decoupled from game logic.
 *
 * Supported key bindings:
 * 1 to 6     : Places the corresponding number in the selected cell.
 * Ñ          : Reveals the full solution on the board.
 * N          : Opens the solution summary window.
 * Backspace  : Erases the value in the selected cell.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see GameController
 */
public class KeyboardHandler implements EventHandler<KeyEvent> {
    /**
     * The active game controller that handles all game logic operations
     * triggered by keyboard input.
     */
    private final GameController controller;

    /**
     * Constructs a {@code KeyboardHandler} bound to the given {@link GameController}.
     *
     * The provided controller will receive all delegated actions
     * triggered by recognized key events.
     *
     * @param controller the active {@link GameController} managing the game session.
     */
    public KeyboardHandler(GameController controller) {
        this.controller = controller;
    }

    /**
     * Processes a keyboard input event and delegates to the appropriate
     * {@link GameController} action based on the key pressed.
     *
     * Key mappings supported:
     * 1 to 6    : Calls {@link GameController#placeNumber(int)} with the pressed digit.
     * Ñ         : Calls {@link GameController#revealFullSolution()} to show the answer.
     * N         : Calls {@link GameController#showSolutionWindow()} to open the end screen.
     * Backspace : Calls {@link GameController#eraseSelectedCell()} to clear the cell.
     *
     * @param event the {@link KeyEvent} triggered by the user's keyboard input.
     */
    @Override
    public void handle(KeyEvent event) {
        // Normalize the key input to lowercase for consistent comparison
        String key = event.getText().toLowerCase();

        // Place a number (1–6) in the currently selected cell
        if (key.matches("[1-6]")) {
            controller.placeNumber(Integer.parseInt(key));
        }

        // Reveal the full solution or open the solution window
        if (key.equals("ñ")) {
            controller.revealFullSolution();
        } else if (key.equals("n")) {
            controller.showSolutionWindow();
        }

        // Erase the value in the currently selected cell
        if (event.getCode() == KeyCode.BACK_SPACE) {
            controller.eraseSelectedCell();
        }
    }
}
