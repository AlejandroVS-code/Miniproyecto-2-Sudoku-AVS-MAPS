package com.example.sudoku_game.model.interfaces;

/**
 * Defines the contract for a timer used in the Sudoku game.
 *
 * Any class implementing this interface is responsible for tracking
 * elapsed time during a game session, providing methods to increment,
 * reset, and retrieve the formatted time for display purposes.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see com.example.sudoku_game.model.Timer
 */
public interface ITimer {

    /**
     * Increments the elapsed time by one second.
     *
     * This method is typically called once per second by a scheduled
     * task or timeline to keep track of the game duration.
     *
     */
    void addSecond();

    /**
     * Resets the elapsed time back to zero.
     *
     * Should be called when a new game session starts or when
     * the timer needs to be restarted.
     *
     */
    void reset();

    /**
     * Returns the elapsed time as a formatted string in {@code MM:SS} format.
     *
     * Both minutes and seconds are always displayed with two digits,
     * padding with a leading zero when necessary (e.g., {@code "03:05"}).
     *
     *
     * @return a {@code String} representing the elapsed time in {@code MM:SS} format.
     */
    String getFormattedTime();
}
