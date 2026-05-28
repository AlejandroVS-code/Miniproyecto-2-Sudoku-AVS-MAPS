package com.example.sudoku_game.model.interfaces;

import java.util.HashMap;

/**
 * Defines the contract for the Sudoku game logic on a 6x6 board.
 *
 * Any class implementing this interface is responsible for managing
 * board generation, move validation, cell updates, hint support,
 * and game state verification.
 * The board uses a {@link HashMap} with keys in {@code "row,col"} format
 * and integer values from 1 to 6.
 *
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see com.example.sudoku_game.model.Sudoku
 */
public interface ISudoku {

    /**
     * Generates a new valid and solvable 6x6 Sudoku puzzle.
     *
     * Implementations must ensure the generated board has a unique
     * solution and that at least 2 cells per 2x3 block are pre-filled.
     *
     */
    void generateBoard();

    /**
     * Checks if placing a value at the given position is valid.
     *
     * Validates the value against the corresponding row, column,
     * and 2x3 block on the current player board.
     *
     *
     * @param row   the row index of the target cell (0–5).
     * @param col   the column index of the target cell (0–5).
     * @param value the value to validate (1–6).
     * @return {@code true} if the move does not cause any conflict,
     *         {@code false} otherwise.
     */
    boolean isValidMove(int row, int col, int value);

    /**
     * Returns the current state of the player's puzzle board.
     *
     * Only cells that have been filled (pre-filled or placed by the player)
     * are included in the returned map.
     *
     *
     * @return a {@link HashMap} mapping cell coordinates in {@code "row,col"}
     *         format to their current integer values.
     */
    HashMap<String, Integer> getBoard();

    /**
     * Directly updates the value of a specific cell on the player's board.
     *
     * This method does not perform validation. Use {@link #validateAndPlace}
     * for player input that requires correctness checking.
     *
     * @param row   the row index of the target cell (0–5).
     * @param col   the column index of the target cell (0–5).
     * @param value the new value to assign to the cell (1–6).
     */
    void updateCellValue(int row, int col, int value);

    /**
     * Validates a player's input against the solution and places it if correct.
     *
     * The value is placed on the board only if it matches the expected
     * solution value and does not violate any Sudoku rule.
     *
     * @param coordinate the target cell coordinate in {@code "row,col"} format.
     * @param value      the value the player wants to place (1–6).
     * @return {@code true} if the value was correctly placed on the board,
     *         {@code false} if it does not match the solution or breaks a rule.
     */
    boolean validateAndPlace(String coordinate, int value);

    /**
     * Returns the correct solution value for a given cell.
     *
     * Used by the hint system to reveal the expected value
     * at a specific position without exposing the full solution.
     *
     * @param coordinate the cell coordinate in {@code "row,col"} format.
     * @return the correct integer value for that cell (1–6).
     */
    int getHelpValue(String coordinate);

    /**
     * Checks whether the game has been completed successfully.
     *
     * The game is considered finished when all 36 cells are filled
     * and the player's board matches the solution exactly.
     *
     * @return {@code true} if the board is complete and correct,
     *         {@code false} otherwise.
     */
    boolean isGameFinished();

    /**
     * Returns the complete solution map of the current puzzle.
     *
     * Contains all 36 cells with their correct values.
     * Should be used carefully to avoid exposing the solution to the player.
     *
     * @return a {@link HashMap} with all cell coordinates in {@code "row,col"}
     *         format mapped to their correct solution values.
     */
    HashMap<String, Integer> getFullSolution();

    /**
     * Records a player move by pushing a cell coordinate onto the history stack.
     *
     * Used to support undo functionality by keeping track of
     * the order in which cells were filled.
     *
     * @param coordinate the cell coordinate in {@code "row,col"} format to record.
     */
    void pushMove(String coordinate);

    /**
     * Removes and returns the last recorded move from the history stack.
     *
     * Used to undo the player's most recent move. Returns {@code null}
     * if no moves have been recorded yet.
     *
     * @return the last move coordinate in {@code "row,col"} format,
     *         or {@code null} if the history is empty.
     */
    String popMove();
}
