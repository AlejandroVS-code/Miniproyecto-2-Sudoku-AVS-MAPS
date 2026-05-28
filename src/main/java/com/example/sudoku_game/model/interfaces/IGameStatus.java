package com.example.sudoku_game.model.interfaces;
/**
 * Defines the contract for tracking the player's game status
 * in the Sudoku application.
 *
 * Any class implementing this interface is responsible for managing
 * and exposing the player's mistakes and hints used during a game session,
 * as well as providing formatted strings for display in the user interface.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see com.example.sudoku_game.model.GameStatus
 */

public interface IGameStatus {

    /**
     * Increments the mistake counter by one.
     *
     * Should be called each time the player places an incorrect
     * value on the board.
     *
     */
    void addMistake();

    /**
     * Returns the total number of mistakes made by the player.
     *
     * @return the number of mistakes as an integer.
     */
    int getMistakes();

    /**
     * Returns a formatted string showing the total number of mistakes.
     *
     * Intended for display in the game's user interface.
     *
     * @return a {@code String} representing the mistake count
     *         (e.g., {@code "Errores: 3"}).
     */
    String getMistakesFormatted();

    /**
     * Increments the hints used counter by one.
     *
     * Should be called each time the player requests a hint during the game.
     */
    void addHelp();

    /**
     * Returns the total number of hints used by the player.
     *
     * @return the number of hints used as an integer.
     */
    int getHelpsUsed();

    /**
     * Determines whether the player is allowed to use a hint.
     *
     * Implementations may use this method to enforce a hint limit.
     * If hints are unlimited, this method should always return {@code true}.
     *
     * @return {@code true} if the player can request a hint,
     *         {@code false} if the hint limit has been reached.
     */
    boolean canUseHelp();
    /**
     * Returns a formatted string showing the total number of hints used.
     *
     * Intended for display in the game's user interface.
     *
     * @return a {@code String} representing the hint count
     *         (e.g., {@code "Pistas: 2"}).
     */
    String getHelpsFormatted();
    /**
     * Resets all game status counters back to zero.
     *
     * Should be called at the start of each new game session to ensure
     * mistakes and hints are cleared from the previous game.
     */
    void reset();
}
