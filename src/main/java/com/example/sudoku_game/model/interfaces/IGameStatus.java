package com.example.sudoku_game.model.interfaces;
/**
 * Interface defining the operational contract for tracking the status of a Sudoku game session.
 * It establishes mandatory rules for managing score metrics, such as user mistakes,
 * available hints/helps, and determining terminal game states (victory or defeat transitions).
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public interface IGameStatus {
    /**
     * Increments the total count of incorrect numerical inputs committed by the player.
     */
    void addMistake();
    /**
     * Retrieves the current cumulative number of mistakes made during the active session.
     * * @return The integer count of recorded user errors.
     */
    int getMistakes();
    /**
     * Evaluates whether the game has reached a terminal defeat state based on threshold limits.
     * * @return true if the allowed mistake limit is exceeded, false otherwise.
     */
    boolean isGameOver();
    /**
     * Generates a user-friendly, localized string representation of the mistakes counter.
     * Suitable for direct rendering onto user interface text labels.
     * * @return A formatted string representation (e.g., "Errores: 1/3").
     */
    String getMistakesFormatted();
    /**
     * Increments the total count of automated solution tips or hints used by the player.
     */
    void addHelp();
    /**
     * Retrieves the total number of hints that the user has consumed during the current game.
     * * @return The integer count of used help utilities.
     */
    int getHelpsUsed();
    /**
     * Checks if the user is legally permitted to request an additional hint.
     * This evaluation depends on whether the historical tracking count remains below the allocated maximum.
     * * @return true if hints are still available for usage, false otherwise.
     */
    boolean canUseHelp();
    /**
     * Generates a user-friendly, localized string representation of the remaining hints capacity.
     * Suitable for direct binding onto UI layout elements.
     * * @return A formatted string representation (e.g., "Pistas: 1/3").
     */
    String getHelpsFormatted();
    /**
     * Restores all underlying counters, statistics, and session tracking states back to their default values.
     * Wipes session history to prepare for a clean game reset.
     */
    void reset();
}
