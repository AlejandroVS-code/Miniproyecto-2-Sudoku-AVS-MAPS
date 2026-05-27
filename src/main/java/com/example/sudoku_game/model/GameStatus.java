package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.IGameStatus;

public class GameStatus implements IGameStatus {
    private int mistakes;

    private int helpsUsed = 0;



    @Override
    public void reset() {
        this.mistakes = 0;
        this.helpsUsed = 0;
    }

    @Override
    public void addHelp() { helpsUsed++; }

    @Override
    public boolean canUseHelp() { return true;  }

    @Override
    public String getHelpsFormatted() {  return "Pistas: " + helpsUsed;
    }

    @Override
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
    public String getMistakesFormatted() {
        return "Errores: " + mistakes;
    }
}
