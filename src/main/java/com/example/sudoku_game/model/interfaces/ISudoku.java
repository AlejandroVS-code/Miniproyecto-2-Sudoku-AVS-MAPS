package com.example.sudoku_game.model.interfaces;

import java.util.HashMap;
/**
 * Interface defining the operational contract for the core Sudoku mathematical engine.
 * It establishes rules for a 6x6 grid grid system, including matrix layout generation,
 * coordinate mapping, placement rule validations, assistance calculations, and backtracking
 * terminal solution evaluations.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public interface ISudoku {
    /**
     * Generates a playable, randomized 6x6 Sudoku board layout matrix.
     * This method builds the puzzle infrastructure by populating specific starting coordinate clues
     * while keeping cell constraints legally valid.
     */
    void generateBoard();
    /**
     * Checks if a numerical token value can legally be placed onto the board matrix
     * coordinates without violating the traditional row, column, or subgrid region constraints.
     * * @param row The target vertical matrix row boundary identifier (0-5).
     * @param col The target horizontal matrix column boundary identifier (0-5).
     * @param value The numerical token digit candidate attempted to be written down (1-6).
     * @return true if the positioning candidate meets all local constraint rules, false otherwise.
     */
    boolean isValidMove(int row, int col, int value);
    /**
     * Retrieves the current interactive runtime representation layout of the playable board.
     * * @return A HashMap linking string coordinates format ("row,col") to their
     * current visible integer values. Empty cells are represented by the absence of their key.
     */
    HashMap<String, Integer> getBoard();
    /**
     * Forcefully overrides or writes down a numerical value token onto specific grid matrix coordinates.
     * Does not trigger historical validation routines or evaluation status metrics.
     * * @param row The target vertical matrix row index identifier (0-5).
     * @param col The target horizontal matrix column index identifier (0-5).
     * @param value The numerical value token to be saved onto the location.
     */
    void updateCellValue(int row, int col, int value);
    /**
     * Evaluates a user interaction input candidate on a specific cell coordinate.
     * If the value is mathematically correct according to the board solution, it places it onto
     * the board tracker and updates state metrics.
     * * @param coordinate The serialized string key identifier for the target cell location (e.g., "3,4").
     * @param value The numerical token attempt supplied by the active input source.
     * @return true if the number choice is mathematically correct, false if it is a mismatch mistake.
     */
    boolean validateAndPlace(String coordinate, int value);
    /**
     * Extracts the single correct mathematical numerical answer for a specific targeted coordinate cell.
     * Used by hint systems to automatically reveal a single valid value on the player board.
     * * @param coordinate The serialized string key identifier of the cell needing assistance (e.g., "1,2").
     * @return The correct legal integer digit matching the solution map for that location.
     */
    int getHelpValue(String coordinate);
    /**
     * Evaluates whether the game session has been completely solved and filled successfully.
     * * @return true if every coordinate cell contains its exact matching correct mathematical solution,
     * false if empty slots or mistakes remain active on the grid layout.
     */
    boolean isGameFinished();
    /**
     * Retrieves the fully pre-calculated solution matrix structure for the current game layout session.
     * Typically generated behind the scenes using backtracking algorithms stack processes.
     * * @return A complete HashMap linking all string coordinates format keys to their
     * terminal solution integer results.
     */
    HashMap<String, Integer> getFullSolution();
}
