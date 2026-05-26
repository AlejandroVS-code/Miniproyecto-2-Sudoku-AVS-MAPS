package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.IGameStatus;
/**
 * Concrete implementation of the {@link IGameStatus} interface.
 * This class serves as the scorekeeper and constraint manager for an active Sudoku game session.
 * It tracks user mistakes and consumed hints, enforcing maximum capacity limits and determining
 * terminal game-over states when safety thresholds are breached.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public class GameStatus implements IGameStatus {
    private int mistakes;
    private final int MAX_MISTAKES = 3;
    private int helpsUsed = 0;
    private final int MAX_HELPS = 3;

    /**
     * Restores all internal performance metrics and usage tracking states back to zero.
     * Fulfills the {@link IGameStatus#reset()} contract to prepare for a fresh game reset.
     */
    @Override
    public void reset() {
        this.mistakes = 0;
        this.helpsUsed = 0;
    }
    /**
     * Increments the consumed hints counter by one, provided the user has not yet
     * exhausted the maximum allowed allocation capacity.
     * Fulfills the {@link IGameStatus#addHelp()} contract.
     */
    @Override
    public void addHelp() { if (helpsUsed < MAX_HELPS) helpsUsed++; }
    /**
     * Evaluates whether the player is legally permitted to request another solution hint.
     * Fulfills the {@link IGameStatus#canUseHelp()} contract.
     * * @return true if the cumulative hints used are strictly below the maximum allowance, false otherwise.
     */
    @Override
    public boolean canUseHelp() { return helpsUsed < MAX_HELPS; }
    /**
     * Generates a user-friendly, localized string representation of the consumed hints ratio.
     * Fulfills the {@link IGameStatus#getHelpsFormatted()} contract, formatting metrics for direct UI label binding.
     * * @return A structured status string formatted as "Pistas: X/3".
     */
    @Override
    public String getHelpsFormatted() {
        return "Pistas: " + helpsUsed + "/" + MAX_HELPS;
    }
    /**
     * Retrieves the current cumulative number of hints consumed during the active session.
     * Fulfills the {@link IGameStatus#getHelpsUsed()} contract.
     * * @return The integer count of used help actions.
     */
    public int getHelpsUsed() {
        return helpsUsed;
    }

    /**
     * Constructs a new GameStatus tracking instance.
     * Initializes the cumulative player mistakes counter state to zero.
     */
    public GameStatus() {
        this.mistakes = 0;
    }
    /**
     * Increments the cumulative counter of incorrect cell entry attempts by one.
     * Fulfills the {@link IGameStatus#addMistake()} contract.
     */
    @Override
    public void addMistake() {
        mistakes++;
    }
    /**
     * Retrieves the current cumulative count of incorrect entry inputs committed during the active session.
     * Fulfills the {@link IGameStatus#getMistakes()} contract.
     * * @return The integer count of recorded player errors.
     */
    @Override
    public int getMistakes() {
        return mistakes;
    }
    /**
     * Evaluates whether the current game session has reached a terminal defeat state.
     * Fulfills the {@link IGameStatus#isGameOver()} contract. Defeat is triggered immediately
     * when the accumulated mistakes equal or exceed the maximum allowed threshold.
     * * @return true if the mistake tolerance limit has been reached, false otherwise.
     */
    @Override
    public boolean isGameOver() {
        return mistakes >= MAX_MISTAKES;
    }
    /**
     * Generates a user-friendly, localized string representation of the accumulated mistakes ratio.
     * Fulfills the {@link IGameStatus#getMistakesFormatted()} contract, formatting metrics for direct UI label rendering.
     * * @return A structured status string formatted as "Errores: X/3".
     */
    public String getMistakesFormatted() {
        return "Errores: " + mistakes + "/" + MAX_MISTAKES;
    }
}
