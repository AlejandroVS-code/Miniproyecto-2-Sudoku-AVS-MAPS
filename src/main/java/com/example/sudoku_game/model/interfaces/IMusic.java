package com.example.sudoku_game.model.interfaces;
/**
 * Defies the contract for the audio manager in the Sudoku application.
 *
 * Any class implementing this interface is responsible for controlling
 * background music playback, including loading, looping, and releasing
 * audio resources during the application lifecycle.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 * @see com.example.sudoku_game.model.Music
 */

public interface IMusic {

    /**
     * Loads and plays the specified audio file in an infinite loop.
     *
     * Any currently active audio should be stopped and disposed of
     * before starting the new track to prevent overlapping playback.
     * The audio file must be available in the application's sound resources.
     *
     * @param fileName the name of the audio file to play
     *                 (e.g., {@code "background.mp3"}).
     */
    void playLoop(String fileName);

    /**
     * Stops the current audio playback and releases all associated resources.
     *
     * This method should be called before loading a new audio track or
     * when navigating between views to prevent memory leaks and audio conflicts.
     * If no audio is currently playing, this method should have no effect.
     */
    void stopAndDispose();

}
