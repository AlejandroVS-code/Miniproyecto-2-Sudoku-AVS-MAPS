package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.IGameStatus;

/**
 * Represents the current status of a Sudoku game session.
 *
 * This class implements {@link IGameStatus} and is responsible for tracking
 * the number of mistakes made and hints used by the player during a game.
 * It provides formatted strings for display in the user interface.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */
public class GameStatus implements IGameStatus {

    /** The total number of incorrect moves made by the player. */
    private int mistakes;

    /** The total number of hints used by the player. */
    private int helpsUsed = 0;


    /**
     * Resets the game status by setting both mistakes and hints used back to zero.
     *
     * This method should be called at the start of each new game session.
     *
     */
    @Override
    public void reset() {
        this.mistakes = 0;
        this.helpsUsed = 0;
    }


    /**
     * Resets the game status by setting both mistakes and hints used back to zero.
     *
     * This method should be called at the start of each new game session.
     *
     */
    @Override
    public void addHelp() { helpsUsed++; }

    /**
     * Determines whether the player is allowed to use a hint.
     *
     * Currently always returns {@code true}, meaning hints are unlimited.
     * This method can be modified to enforce a hint limit in future versions.
     *
     * @return {@code true} if the player can use a hint, {@code false} otherwise.
     */
    @Override
    public boolean canUseHelp() { return true;  }

    /**
     * Returns a formatted string showing the total number of hints used.
     *
     * Intended for display in the game's user interface.
     *
     * @return a {@code String} in the format {@code "Pistas: N"} where N is the hint count.
     */
    @Override
    public String getHelpsFormatted() {  return "Pistas: " + helpsUsed;
    }

    /**
     * Returns the total number of hints used by the player.
     *
     * @return the number of hints used as an integer.
     */
    @Override
    public int getHelpsUsed() {
        return helpsUsed;
    }

    /**
     * Constructs a new {@code GameStatus} instance with mistakes initialized to zero.
     */
    public GameStatus() {
        this.mistakes = 0;
    }

    /**
     * Increments the mistake counter by one.
     *
     * Called each time the player places an incorrect value on the board.
     */
    @Override
    public void addMistake() {
        mistakes++;
    }

    /**
     * Returns the total number of mistakes made by the player.
     *
     * @return the number of mistakes as an integer.
     */
    @Override
    public int getMistakes() {
        return mistakes;
    }

    /**
     * Returns a formatted string showing the total number of mistakes made.
     *
     * Intended for display in the game's user interface.
     *
     * @return a {@code String} in the format {@code "Errores: N"} where N is the mistake count.
     */
    @Override
    public String getMistakesFormatted() {
        return "Errores: " + mistakes;
    }
}
