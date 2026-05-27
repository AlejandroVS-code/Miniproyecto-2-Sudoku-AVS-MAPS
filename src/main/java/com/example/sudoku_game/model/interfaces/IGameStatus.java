package com.example.sudoku_game.model.interfaces;

public interface IGameStatus {

    void addMistake();

    int getMistakes();

    String getMistakesFormatted();

    void addHelp();

    int getHelpsUsed();

    boolean canUseHelp();

    String getHelpsFormatted();

    void reset();
}
