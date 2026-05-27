package com.example.sudoku_game.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardHandler implements EventHandler<KeyEvent> {

    private final GameController controller;

    /**
     * Constructs a KeyboardHandler bound to the given GameController.
     * @param controller The active GameController managing the game session.
     */
    public KeyboardHandler(GameController controller) {
        this.controller = controller;
    }

    /**
     * Handles keyboard input events and delegates to the appropriate
     * GameController action based on the key pressed.
     * @param event The KeyEvent triggered by the user.
     */
    @Override
    public void handle(KeyEvent event) {
        String key = event.getText().toLowerCase();

        if (key.matches("[1-6]")) {
            controller.placeNumber(Integer.parseInt(key));
        }

        if (key.equals("ñ")) {
            controller.revealFullSolution();
        } else if (key.equals("n")) {
            controller.showSolutionWindow();
        }

        if (event.getCode() == KeyCode.BACK_SPACE) {
            controller.eraseSelectedCell();
        }
    }
}
