package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ITimer;
/**
 * Concrete implementation of the {@link ITimer} interface.
 * This class acts as the logical stopwatch engine for the Sudoku game session,
 * maintaining an internal counter of elapsed seconds and formatting it into a
 * standard chronological clock string display.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public class Timer implements ITimer {
    /** The cumulative total number of seconds elapsed during the active game session. */
    private int seconds;
    /**
     * Constructs a new Timer instance.
     * Initializes the elapsed seconds counter state to zero.
     */
    public Timer() {
        this.seconds = 0;
    }
    /**
     * Increments the internal game session duration counter by exactly one second.
     * This method fulfills the {@link ITimer#addSecond()} contract and is designed
     * to be invoked periodically by an external scheduler or UI Timeline loop.
     */
    @Override
    public void addSecond() {

        this.seconds++;
    }
    /**
     * Resets the internal time counter completely back to zero.
     * Fulfills the {@link ITimer#reset()} contract to clear elapsed metrics for
     * a brand new puzzle layout initialization.
     */
    @Override
    public void reset() {
        this.seconds = 0;
    }
    /**
     * Formats the cumulative internal seconds counter into a standardized digital clock string.
     * Fulfills the {@link ITimer#getFormattedTime()} contract, translating raw ticks into
     * a readable layout using a mathematical base-60 split.
     * * @return A zero-padded time display string formatted as "MM:SS" (e.g., "01:05" for 65 seconds).
     */
    @Override
    public String getFormattedTime() {

        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }
}
