package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.IMusic;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
/**
 * Concrete implementation of the {@link IMusic} interface applying the Singleton Design Pattern.
 * This class acts as the centralized audio manager for the entire application, ensuring that
 * only a single audio stream tracks playback at any given time to avoid overlapping audio channels,
 * while safely disposing of low-level multimedia resources during view transitions.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */
public class Music implements IMusic {

    private static Music instance;
    private MediaPlayer mediaPlayer;


    private Music() {}
    /**
     * Retrieves the globally accessible unique instance of the Music manager.
     * Implements lazy initialization to create the manager object only when it is requested for the first time.
     * * @return The single, global thread-safe instance of the {@link Music} manager.
     */
    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }
    /**
     * Initiates infinite, continuous background looping audio playback for a specified track asset.
     * Fulfills the {@link IMusic#playLoop(String)} contract. It automatically intercepts, halts,
     * and clears any pre-existing active media playback context before parsing the target resource stream.
     * * @param fileName The simple file name with its extension string located inside the
     * resource folder framework (e.g., "Background.mp3", "Finish.mp3").
     */
    @Override
    public void playLoop(String fileName) {

        stopAndDispose();

        try {

            URL resource = getClass().getResource("/com/example/sudoku_game/Sounds/" + fileName);

            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.2);
                mediaPlayer.play();

            }
        } catch (Exception e) {
            System.out.println("Excepción al intentar reproducir (" + fileName + "): " + e.getMessage());
        }
    }
    /**
     * Forcefully interrupts the running multimedia track and tears down the active hardware playback system.
     * Fulfills the {@link IMusic#stopAndDispose()} contract, freeing native system audio line decoders,
     * terminating background processing playback loops, and releasing heap pointer references for garbage collection.
     */
    @Override
    public void stopAndDispose() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

}
