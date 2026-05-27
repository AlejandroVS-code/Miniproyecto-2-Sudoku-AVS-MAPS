package com.example.sudoku_game.model.interfaces;

public interface ITimer {

    void addSecond();

    void reset();

    String getFormattedTime();
}
