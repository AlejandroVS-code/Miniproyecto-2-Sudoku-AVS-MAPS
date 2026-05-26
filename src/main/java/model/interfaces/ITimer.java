package com.example.sudoku_game.model.interfaces;

public interface ITimer {
    void addSecond();        // Lógica de incremento
    void reset();            // Reiniciar estado
    String getFormattedTime();
}
