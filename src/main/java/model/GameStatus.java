package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.IGameStatus;

public class GameStatus implements IGameStatus {
    private int mistakes;
    private final int MAX_MISTAKES = 3;
    private int helpsUsed = 0;
    private final int MAX_HELPS = 3;
    @Override
    public void reset() {
        this.mistakes = 0;
        this.helpsUsed = 0;
    }
    @Override
    public void addHelp() { if (helpsUsed < MAX_HELPS) helpsUsed++; }

    @Override
    public boolean canUseHelp() { return helpsUsed < MAX_HELPS; }

    @Override
    public String getHelpsFormatted() {
        return "Pistas: " + helpsUsed + "/" + MAX_HELPS;
    }
    public int getHelpsUsed() {
        return helpsUsed;
    }


    public GameStatus() {
        this.mistakes = 0;
    }

    @Override
    public void addMistake() {
        mistakes++;
    }

    @Override
    public int getMistakes() {
        return mistakes;
    }

    @Override
    public boolean isGameOver() {
        return mistakes >= MAX_MISTAKES;
    }

    public String getMistakesFormatted() {
        return "Errores: " + mistakes + "/" + MAX_MISTAKES;
    }
}
