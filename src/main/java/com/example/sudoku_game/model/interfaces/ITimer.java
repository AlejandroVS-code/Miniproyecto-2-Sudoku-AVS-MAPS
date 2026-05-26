package com.example.sudoku_game.model.interfaces;
/**
 * Interface defining the operational contract for the application's gameplay stopwatch component.
 * It establishes mandatory rules for accumulating elapsed real-time metrics during an active session
 * and translating raw internal time states into user-friendly display readouts.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public interface ITimer {
    /**
     * Increments the internal time counter accumulation state by a fixed unit of one second.
     * This method is intended to be triggered periodically by a background scheduler or UI timeline loop.
     */
    void addSecond();
    /**
     * Restores the internal elapsed time metrics counter completely back to zero.
     * Wipes historical duration data clean to prepare for a brand new game session stopwatch initialization.
     */
    void reset();
    /**
     * Formats the cumulative internal elapsed seconds into a standard time measurement string representation.
     * The resulting output unifies chronological values into a readable layout suitable for direct UI integration.
     * * @return A standardized, zero-padded time display string formatted as "MM:SS" (e.g., "04:25").
     */
    String getFormattedTime();
}
