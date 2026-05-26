package com.example.sudoku_game.model.interfaces;

import java.util.HashMap;

public interface ISudoku {

    void generateBoard();

    boolean isValidMove(int row, int col, int value);

    HashMap<String, Integer> getBoard();

    void updateCellValue(int row, int col, int value);
    boolean validateAndPlace(String coordinate, int value);
    int getHelpValue(String coordinate);
    boolean isGameFinished();
    HashMap<String, Integer> getFullSolution();
}
