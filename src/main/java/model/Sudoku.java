package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ISudoku;

import java.util.Collections;
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

    @Override
    public void generateBoard() {
        board.clear();
        solution.clear();
        fillBoard(0, 0);
        solution.putAll(board);
        createPuzzle();
    }

    private boolean fillBoard(int row, int col) {
        if (row == 6) return true;

        int nextRow = (col == 5) ? row + 1 : row;
        int nextCol = (col == 5) ? 0 : col + 1;

        // En lugar de una lista con shuffle, usamos un HashMap para marcar
        // qué números (1-6) ya intentamos en esta celda específica.
        HashMap<Integer, Boolean> triedNumbers = new HashMap<>();

        while (triedNumbers.size() < 6) {
            int num = random.nextInt(6) + 1;

            if (!triedNumbers.containsKey(num)) {
                triedNumbers.put(num, true);

                if (isValidMove(row, col, num)) {
                    board.put(row + "," + col, num);
                    if (fillBoard(nextRow, nextCol)) return true;
                    board.remove(row + "," + col);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isValidMove(int row, int col, int value) {
        // Validación de fila y columna usando solo el HashMap 'board'
        for (int i = 0; i < 6; i++) {
            Integer rVal = board.get(row + "," + i);
            if (rVal != null && rVal == value) return false;

            Integer cVal = board.get(i + "," + col);
            if (cVal != null && cVal == value) return false;
        }

        // Validación de bloque 2x3
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
        // Aquí quitamos números al azar para que el usuario juegue
        // Usamos un contador simple en lugar de recorrer una lista
        int removed = 0;
        while (removed < 20) { // Por ejemplo, quitar 20 números
            int r = random.nextInt(6);
            int c = random.nextInt(6);
            if (board.containsKey(r + "," + c)) {
                board.remove(r + "," + c);
                removed++;
            }
        }
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
            updateCellValue(row, col, value); // Guarda en el HashMap
            return true;
        }
        return false;
    }

    public int getHelpValue(String coordinate) {
        // Retorna el valor que guardamos en el tablero solución
        // cuando generamos el juego al principio
        return solution.get(coordinate);
    }
    public boolean isGameFinished() {
        // 1. Verificar que el tablero tenga las 36 celdas llenas (6x6)
        if (board.size() < 36) return false;

        // 2. Verificar que cada celda coincida con la solución generada
        return board.equals(solution);
    }
    @Override
    public HashMap<String, Integer> getFullSolution() {
        return this.solution;
    }


}