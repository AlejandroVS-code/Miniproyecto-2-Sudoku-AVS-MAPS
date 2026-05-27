package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.ITimer;

public class Timer implements ITimer {

    private int seconds;

    public Timer() {
        this.seconds = 0;
    }

    @Override
    public void addSecond() {

        this.seconds++;
    }

    @Override
    public void reset() {
        this.seconds = 0;
    }

    @Override
    public String getFormattedTime() {

        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }
}
