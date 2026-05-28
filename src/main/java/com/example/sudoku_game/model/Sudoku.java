package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ISudoku;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
/**
 * Represents the Sudoku game logic for a 6x6 board.
 *
 * This class implements {@link ISudoku} and handles board generation,
 * move validation, puzzle creation, solution verification, and game state management.
 * The board uses a {@link HashMap} with keys in {@code "row,col"} format.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */
public class Sudoku implements ISudoku {

    /** Stores the current state of the puzzle board visible to the player. */
    private HashMap<String, Integer> board;

    /** Stores the complete and correct solution of the generated puzzle. */
    private HashMap<String, Integer> solution;

    /** Tracks the history of moves made by the player as cell coordinates. */
    private Stack<String> moveHistory;

    /** Random number generator used during board and puzzle generation. */
    private Random random;


    /**
     * Constructs a new {@code Sudoku} instance and initializes
     * the board, solution, move history, and random number generator.
     */
    public Sudoku() {
        this.board = new HashMap<>();
        this.solution = new HashMap<>();
        this.moveHistory = new Stack<>();
        this.random = new Random();
    }

    /**
     * Records a player move by pushing a cell coordinate onto the history stack.
     *
     * @param coordinate the cell coordinate in {@code "row,col"} format to record.
     */
    public void pushMove(String coordinate) {
        moveHistory.push(coordinate);
    }

    /**
     * Removes and returns the last recorded move from the history stack.
     * Used to support undo functionality.
     *
     * @return the last move coordinate in {@code "row,col"} format,
     *         or {@code null} if the move history is empty.
     */
    public String popMove() {
        if (moveHistory.isEmpty()) return null;
        return moveHistory.pop();
    }


    /**
     * Generates a new valid and solvable 6x6 Sudoku puzzle.
     *
     * Repeatedly attempts to fill the board and create a puzzle until
     * a solvable configuration is confirmed. All previous state is cleared
     * on each attempt.
     *
     */
    @Override
    public void generateBoard() {
        boolean solvable = false;
        while (!solvable) {
            board.clear();
            solution.clear();
            moveHistory.clear();
            boolean filled = fillBoard(0, 0);
            if (filled) {
                createPuzzle();
                solvable = isSolvable();
            }
        }
    }

    /**
     * Recursively fills the solution board using backtracking.
     *
     * Tries random numbers from 1 to 6 for each cell, ensuring no conflicts
     * exist in the same row, column, or 2x3 block before placing a value.
     *
     * @param row the current row index (0–5).
     * @param col the current column index (0–5).
     * @return {@code true} if the board was successfully filled,
     *         {@code false} if no valid number exists and backtracking is needed.
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

                if (isValidSolutionMove(row, col, num)) {
                    solution.put(row + "," + col, num);

                    if (fillBoard(nextRow, nextCol)) {
                        return true;
                    }

                    solution.remove(row + "," + col);
                }
            }
        }
        return false;
    }

    /**
     * Checks if placing a value in the solution map is valid.
     *
     * Validates the given value against the corresponding row,
     * column, and 2x3 block in the solution map.
     *
     *
     * @param row   the row index of the target cell (0–5).
     * @param col   the column index of the target cell (0–5).
     * @param value the value to validate (1–6).
     * @return {@code true} if the placement does not cause any conflict,
     *         {@code false} otherwise.
     */
    private boolean isValidSolutionMove(int row, int col, int value) {
        for (int i = 0; i < 6; i++) {
            Integer rVal = solution.get(row + "," + i);
            if (rVal != null && rVal == value) return false;

            Integer cVal = solution.get(i + "," + col);
            if (cVal != null && cVal == value) return false;
        }

        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                Integer bVal = solution.get(r + "," + c);
                if (bVal != null && bVal == value) return false;
            }
        }
        return true;
    }

    /**
     * Checks if placing a value on the player's board is valid.
     *
     * Validates the given value against the current row, column,
     * and 2x3 block in the player's board map.
     *
     *
     * @param row   the row index of the target cell (0–5).
     * @param col   the column index of the target cell (0–5).
     * @param value the value to validate (1–6).
     * @return {@code true} if the move does not cause any conflict,
     *         {@code false} otherwise.
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
     * Creates the puzzle by revealing exactly 2 random cells per 2x3 block
     * from the complete solution.
     *
     * All six 2x3 blocks are processed, and 2 cells from each are copied
     * from the solution into the player's board. The remaining cells are
     * left empty for the player to solve.
     *
     */
    private void createPuzzle() {
        board.clear();

        Stack<String> blockOrigins = new Stack<>();
        blockOrigins.push("0,0");
        blockOrigins.push("0,3");
        blockOrigins.push("2,0");
        blockOrigins.push("2,3");
        blockOrigins.push("4,0");
        blockOrigins.push("4,3");

        while (!blockOrigins.isEmpty()) {
            String origin = blockOrigins.pop();
            int commaPos = origin.indexOf(",");
            int startRow = Integer.parseInt(origin.substring(0, commaPos));
            int startCol = Integer.parseInt(origin.substring(commaPos + 1));

            Stack<String> blockCells = new Stack<>();
            for (int r = startRow; r < startRow + 2; r++) {
                for (int c = startCol; c < startCol + 3; c++) {
                    blockCells.push(r + "," + c);
                }
            }

            Stack<String> shuffledCells = shuffleStack(blockCells);

            for (int i = 0; i < 2; i++) {
                if (!shuffledCells.isEmpty()) {
                    String key = shuffledCells.pop();
                    board.put(key, solution.get(key));
                }
            }
        }
    }

    /**
     * Verifies that the current puzzle has at least one valid solution.
     *
     * Uses an iterative backtracking algorithm over a temporary copy of the board
     * to confirm solvability without modifying the actual game state.
     *
     *
     * @return {@code true} if the puzzle can be solved, {@code false} otherwise.
     */
    private boolean isSolvable() {

        HashMap<String, Integer> tempBoard = new HashMap<>(board);


        Stack<String> emptyCells = new Stack<>();
        for (int r = 5; r >= 0; r--) {
            for (int c = 5; c >= 0; c--) {
                String key = r + "," + c;
                if (!tempBoard.containsKey(key)) {
                    emptyCells.push(key);
                }
            }
        }


        Stack<Integer> candidates = new Stack<>();
        for (int i = 0; i < emptyCells.size(); i++) {
            candidates.push(1);
        }


        Stack<String> workCells = new Stack<>();
        Stack<String> tempEmpty = new Stack<>();

        while (!emptyCells.isEmpty()) {
            tempEmpty.push(emptyCells.pop());
        }
        while (!tempEmpty.isEmpty()) {
            String cell = tempEmpty.pop();
            emptyCells.push(cell);
            workCells.push(cell);
        }


        Stack<String> placedCells = new Stack<>();
        Stack<Integer> placedValues = new Stack<>();

        Stack<String> pending = new Stack<>();
        while (!workCells.isEmpty()) {
            pending.push(workCells.pop());
        }


        HashMap<String, Integer> nextCandidate = new HashMap<>();

        while (true) {
            if (pending.isEmpty()) {

                while (!placedCells.isEmpty()) {
                    tempBoard.remove(placedCells.pop());
                    placedValues.pop();
                }
                return true;
            }

            String current = pending.pop();
            int startNum = nextCandidate.containsKey(current) ? nextCandidate.get(current) : 1;
            boolean placed = false;

            for (int num = startNum; num <= 6; num++) {
                if (isValidInMap(tempBoard, current, num)) {
                    tempBoard.put(current, num);
                    placedCells.push(current);
                    placedValues.push(num);
                    nextCandidate.put(current, num + 1);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                nextCandidate.remove(current);
                tempBoard.remove(current);

                if (placedCells.isEmpty()) {

                    return false;
                }


                String previous = placedCells.pop();
                int previousValue = placedValues.pop();
                tempBoard.remove(previous);
                pending.push(current);
                pending.push(previous);


                nextCandidate.put(previous, previousValue + 1);
            }
        }
    }

    /**
     * Checks if placing a value in a temporary board map is valid.
     *
     * Used internally by {@link #isSolvable()} to test candidate values
     * without affecting the actual board or solution.
     *
     *
     * @param tempBoard  the temporary board map to validate against.
     * @param coordinate the cell coordinate in {@code "row,col"} format.
     * @param value      the value to validate (1–6).
     * @return {@code true} if the value causes no conflict, {@code false} otherwise.
     */
    private boolean isValidInMap(HashMap<String, Integer> tempBoard, String coordinate, int value) {
        int commaPos = coordinate.indexOf(",");
        int row = Integer.parseInt(coordinate.substring(0, commaPos));
        int col = Integer.parseInt(coordinate.substring(commaPos + 1));

        for (int i = 0; i < 6; i++) {
            Integer rVal = tempBoard.get(row + "," + i);
            if (rVal != null && rVal == value) return false;

            Integer cVal = tempBoard.get(i + "," + col);
            if (cVal != null && cVal == value) return false;
        }

        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                Integer bVal = tempBoard.get(r + "," + c);
                if (bVal != null && bVal == value) return false;
            }
        }
        return true;
    }

    /**
     * Randomly shuffles the elements of a {@link Stack} of strings.
     *
     * Used during puzzle creation to randomize which cells are revealed
     * in each 2x3 block.
     *
     *
     * @param originalStack the stack of cell coordinates to shuffle.
     * @return a new {@link Stack} containing the same elements in random order.
     */
    private Stack<String> shuffleStack(Stack<String> originalStack) {
        Stack<String> shuffled = new Stack<>();
        Stack<String> temp = new Stack<>();

        while (!originalStack.isEmpty()) {
            String item = originalStack.pop();

            int depth = shuffled.isEmpty() ? 0 : random.nextInt(shuffled.size() + 1);
            for (int i = 0; i < depth; i++) {
                temp.push(shuffled.pop());
            }

            shuffled.push(item);

            while (!temp.isEmpty()) {
                shuffled.push(temp.pop());
            }
        }
        return shuffled;
    }

    /**
     * Returns the current state of the player's puzzle board.
     *
     * @return a {@link HashMap} mapping cell coordinates to their current values.
     */
    @Override
    public HashMap<String, Integer> getBoard() {
        return board;
    }

    /**
     * Updates the value of a specific cell on the player's board.
     *
     * @param row   the row index of the cell (0–5).
     * @param col   the column index of the cell (0–5).
     * @param value the new value to assign to the cell (1–6).
     */
    @Override
    public void updateCellValue(int row, int col, int value) {
        board.put(row + "," + col, value);
    }


    /**
     * Validates a player's input and places it on the board if correct.
     *
     * The value is placed only if it matches the expected solution value
     * and does not violate any Sudoku rule.
     *
     * @param coordinate the target cell coordinate in {@code "row,col"} format.
     * @param value      the value the player wants to place (1–6).
     * @return {@code true} if the value was correctly placed on the board,
     *         {@code false} if it does not match the solution or breaks a rule.
     */
    @Override
    public boolean validateAndPlace(String coordinate, int value) {
        int commaPos = coordinate.indexOf(",");
        int row = Integer.parseInt(coordinate.substring(0, commaPos));
        int col = Integer.parseInt(coordinate.substring(commaPos + 1));

        Integer correctValue = solution.get(coordinate);
        if (correctValue == null || correctValue != value) {
            return false;
        }

        if (isValidMove(row, col, value)) {
            updateCellValue(row, col, value);
            return true;
        }
        return false;
    }

    /**
     * Returns the correct solution value for a given cell.
     * Used by the hint system to assist the player.
     *
     * @param coordinate the cell coordinate in {@code "row,col"} format.
     * @return the correct integer value for that cell according to the solution.
     */
    public int getHelpValue(String coordinate) {
        return solution.get(coordinate);
    }

    /**
     * Checks whether the game has been completed successfully.
     *
     * The game is finished when all 36 cells are filled and
     * the player's board matches the solution exactly.
     *
     * @return {@code true} if the board is complete and correct,
     *         {@code false} otherwise.
     */
    public boolean isGameFinished() {
        if (board.size() < 36) return false;
        return board.equals(solution);
    }

    /**
     * Returns the complete solution map of the current puzzle.
     *
     * @return a {@link HashMap} with all 36 cell coordinates
     *         mapped to their correct solution values.
     */
    @Override
    public HashMap<String, Integer> getFullSolution() {
        return this.solution;
    }
}