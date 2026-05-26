package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ISudoku;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
/**
 * Core mathematical engine implementing the {@link ISudoku} interface constraints.
 * This class coordinates the state of a 6x6 Sudoku board using localized matrices managed by hash structures.
 * It features procedural generation via recursive backtracking, grid constraint validation routines,
 * automated clue pruning with protection limits, and solution verification.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public class Sudoku implements ISudoku {

    private HashMap<String, Integer> board;
    private HashMap<String, Integer> solution;
    private Stack<String> moveHistory;
    private Random random;
    /**
     * Constructs a new Sudoku core game engine instance.
     * Initializes structural tracking layers, history memory blocks, and state data containers.
     */
    public Sudoku() {
        this.board = new HashMap<>();
        this.solution = new HashMap<>();
        this.moveHistory = new Stack<>();
        this.random = new Random();
    }
    /**
     * Resets internal trackers and procedures to populate a brand-new randomized 6x6 Sudoku layout.
     * Fulfills the {@link ISudoku#generateBoard()} contract. It triggers recursive matrix filling,
     * clones the generated solution, and passes the board to a pruning process to reveal playable hints.
     */
    @Override
    public void generateBoard() {
        board.clear();
        solution.clear();
        fillBoard(0, 0);
        solution.putAll(board);
        createPuzzle();
    }
    /**
     * Recursively populates grid coordinates with unique digits using a Backtracking algorithm pattern.
     * Iterates through randomized valid candidates (1-6) per cell, evaluating subgrid and cross-axis line
     * constraints. Rollbacks occur automatically when candidate pathways collapse into a mathematical bottleneck.
     * * @param row The active vertical processing boundary tracking row index (0-5).
     * @param col The active horizontal processing boundary tracking column index (0-5).
     * @return true if the algorithm successfully populates all 36 coordinate nodes legally,
     * false if a dead-end requires a structural rollback to previous nodes.
     */
    private boolean fillBoard(int row, int col) {

        if (row == 6) return true;


        int nextRow = (col == 5) ? row + 1 : row;
        int nextCol = (col == 5) ? 0 : col + 1;


        HashMap<Integer, Boolean> triedNumbers = new HashMap<>();


        while (triedNumbers.size() < 6) {
            int num = random.nextInt(6) + 1;


            if (!triedNumbers.containsKey(num)) {
                triedNumbers.put(num, true);


                if (isValidMove(row, col, num)) {
                    board.put(row + "," + col, num);


                    if (fillBoard(nextRow, nextCol)) {
                        return true;
                    }


                    board.remove(row + "," + col);
                }
            }
        }


        return false;
    }
    /**
     * Evaluates a digit injection attempt against the traditional Sudoku alignment constraints.
     * Fulfills the {@link ISudoku#isValidMove(int, int, int)} contract, evaluating cross-axis conflicts
     * across the entire 6-node length and verifying constraints within localized 2x3 mini-block sectors.
     * * @param row Vertical grid line tracker index boundary (0-5).
     * @param col Horizontal grid line tracker index boundary (0-5).
     * @param value The numerical digit token candidate being evaluated (1-6).
     * @return true if the placement candidate satisfies all constraints, false if conflicts occur.
     */
    @Override
    public boolean isValidMove(int row, int col, int value) {

        for (int i = 0; i < 6; i++) {
            Integer rVal = board.get(row + "," + i);
            if (rVal != null && rVal == value) return false;

            Integer cVal = board.get(i + "," + col);
            if (cVal != null && cVal == value) return false;
        }


        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                Integer bVal = board.get(r + "," + c);
                if (bVal != null && bVal == value) return false;
            }
        }
        return true;
    }
    /**
     * Selectively removes numbers from the fully solved board to create a playable puzzle layout.
     * Iterates through the matrix randomly to prune up to 20 coordinates, applying strict safety caps
     * to protect minimal visible structures and prevent absolute unsolvable stalemates.
     */
    private void createPuzzle() {
        int removed = 0;

        int maxAttempts = 100;
        int attempts = 0;

        while (removed < 20 && attempts < maxAttempts) {
            attempts++;
            int r = random.nextInt(6);
            int c = random.nextInt(6);
            String key = r + "," + c;

            if (board.containsKey(key)) {

                int backupValue = board.get(key);


                board.remove(key);


                if (hasValidStalemate(r, c)) {

                    board.put(key, backupValue);
                } else {

                    removed++;
                }
            }
        }
    }

    /**
     * Evaluates whether pruning a coordinate leaves its localized 2x3 subgrid block underpopulated.
     * Prevents excessive information loss by enforcing that each block retains at least two visible values,
     * preserving a playable entry point for the user.
     * * @param row The vertical coordinate index context of the evaluated node.
     * @param col The horizontal coordinate index context of the evaluated node.
     * @return true if the local sector risks underpopulation (less than 2 items), triggering a protection lock.
     */
    private boolean hasValidStalemate(int row, int col) {

        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;

        int visibleInBlock = 0;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board.containsKey(r + "," + c)) {
                    visibleInBlock++;
                }
            }
        }

        return visibleInBlock < 2;
    }
    /**
     * Retrieves the current interactive runtime representation layout of the playable board.
     * Fulfills the {@link ISudoku#getBoard()} contract.
     * * @return A HashMap containing active player coordinates and visible tokens.
     */
    @Override
    public HashMap<String, Integer> getBoard() {
        return board;
    }

    /**
     * Direct write injection operation updating internal board matrices.
     * Fulfills the {@link ISudoku#updateCellValue(int, int, int)} contract.
     * * @param row Vertical grid target node row positioning index (0-5).
     * @param col Horizontal grid target node column positioning index (0-5).
     * @param value The numerical value token to store at the coordinate.
     */
    @Override
    public void updateCellValue(int row, int col, int value) {
        board.put(row + "," + col, value);
    }
    /**
     * Parses a string coordinate key to evaluate and apply an input candidate.
     * Fulfills the {@link ISudoku#validateAndPlace(String, int)} contract. If the move is valid,
     * the cell value is updated and saved.
     * * @param coordinate Serialized position string mapping the target node (e.g., "4,2").
     * @param value Numerical input token supplied by the active event stream.
     * @return true if the placement satisfies all structural rules, false if it is an invalid move.
     */
    @Override
    public boolean validateAndPlace(String coordinate, int value) {

        int commaPos = coordinate.indexOf(",");
        int row = Integer.parseInt(coordinate.substring(0, commaPos));
        int col = Integer.parseInt(coordinate.substring(commaPos + 1));

        if (isValidMove(row, col, value)) {
            updateCellValue(row, col, value);
            return true;
        }
        return false;
    }
    /**
     * Extracts the correct mathematical answer for a specific coordinate cell.
     * Fulfills the {@link ISudoku#getHelpValue(String)} contract, retrieving the value from the solved baseline map.
     * * @param coordinate Serialized lookup position key string mapping layout targets (e.g., "1,5").
     * @return The pre-calculated correct integer matching that specific coordinate.
     */
    public int getHelpValue(String coordinate) {

        return solution.get(coordinate);
    }
    /**
     * Evaluates whether the game board has been fully and correctly completed.
     * Checks if all 36 cells are filled and verifies that the current board state exactly matches the solved matrix.
     * * @return true if the board is completely filled and matching the solution, false otherwise.
     */
    public boolean isGameFinished() {

        if (board.size() < 36) return false;


        return board.equals(solution);
    }

    /**
     * Retrieves the fully pre-calculated solution matrix structure for the current game session.
     * Fulfills the {@link ISudoku#getFullSolution()} contract.
     * * @return The complete solution baseline map context data structure.
     */
    @Override
    public HashMap<String, Integer> getFullSolution() {
        return this.solution;
    }


}