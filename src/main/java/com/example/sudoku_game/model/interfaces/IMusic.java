package com.example.sudoku_game.model.interfaces;
/**
 * Interface defining the operational contract for the application's audio playback subsystem.
 * It establishes mandatory methods for handling background media loops and managing
 * low-level multimedia resource deallocation to prevent memory leaks during view transitions.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public interface IMusic {
    /**
     * Starts continuous, infinite looping audio playback for a specified media asset file.
     * If another audio track is currently active, it should be automatically halted and cleared
     * before the new soundtrack stream begins initialization.
     * * @param fileName The simple name of the target audio file located within the dedicated
     * multimedia resource directory (e.g., "Background.mp3").
     */
    void playLoop(String fileName);
    /**
     * Abruptly halts the active audio track playback loop and explicitly disposes of
     * the underlying multimedia player infrastructure instance.
     * This method releases hardware audio channels, kills background native execution threads,
     * and clears active heap memory references safely.
     */
    void stopAndDispose();

}
