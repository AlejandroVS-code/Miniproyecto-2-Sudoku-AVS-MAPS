package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ISudoku;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Sudoku implements ISudoku {

    private HashMap<String, Integer> board;
    private HashMap<String, Integer> solution;
    private Stack<String> moveHistory;
    private Random random;

    public Sudoku() {
        this.board = new HashMap<>();
        this.solution = new HashMap<>();
        this.moveHistory = new Stack<>();
        this.random = new Random();
    }

    public void pushMove(String coordinate) {
        moveHistory.push(coordinate);
    }

    public String popMove() {
        if (moveHistory.isEmpty()) return null;
        return moveHistory.pop();
    }




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


    @Override
    public HashMap<String, Integer> getBoard() {
        return board;
    }


    @Override
    public void updateCellValue(int row, int col, int value) {
        board.put(row + "," + col, value);
    }


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


    public int getHelpValue(String coordinate) {
        return solution.get(coordinate);
    }


    public boolean isGameFinished() {
        if (board.size() < 36) return false;
        return board.equals(solution);
    }


    @Override
    public HashMap<String, Integer> getFullSolution() {
        return this.solution;
    }
}