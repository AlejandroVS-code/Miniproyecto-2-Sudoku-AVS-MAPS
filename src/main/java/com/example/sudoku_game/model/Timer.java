package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ITimer;

/**
 * Represents a timer for the Sudoku game.
 *
 * This class implements {@link ITimer} and is responsible for tracking
 * the elapsed time during a game session. It provides functionality
 * to increment, reset, and retrieve the formatted time.
 *
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */
public class Timer implements ITimer {

    /**
     * The total elapsed time in seconds.
     */
    private int seconds;

    /**
     * Constructs a new {@code Timer} with the elapsed time set to zero.
     */
    public Timer() {
        this.seconds = 0;
    }

    /**
     * Increments the elapsed time by one second.
     *
     * This method is typically called once per second by a scheduled
     * task or timeline to keep track of the game duration.
     *
     */
    @Override
    public void addSecond() {

        this.seconds++;
    }

    /**
     * Resets the elapsed time back to zero.
     *
     * This method is called when a new game session starts
     * or when the timer needs to be restarted.
     *
     */
    @Override
    public void reset() {
        this.seconds = 0;
    }

    /**
     * Returns the elapsed time formatted as a string in {@code MM:SS} format.
     *
     * The minutes and seconds are always displayed with two digits,
     * padding with a leading zero when necessary (e.g., {@code "03:05"}).
     *
     *
     * @return a {@code String} representing the elapsed time in {@code MM:SS} format.
     */
    @Override
    public String getFormattedTime() {

    // Calculate minutes and remaining seconds
        int mins = seconds / 60;
        int secs = seconds % 60;

        // Format and return as MM:SS
        return String.format("%02d:%02d", mins, secs);
    }
}
