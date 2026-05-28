package com.example.sudoku_game.model;

import com.example.sudoku_game.model.interfaces.IMusic;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
/**
 * Concrete implementation of the {@link IMusic} interface applying the Singleton Design Pattern.
 *
 * This class acts as the centralized audio manager for the entire application, ensuring that
 * only a single audio stream tracks playback at any given time to avoid overlapping audio channels,
 * while safely disposing of low-level multimedia resources during view transitions.
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */
public class Music implements IMusic {

    /** The single shared instance of this class (Singleton pattern). */
    private static Music instance;

    /** The JavaFX media player used to control audio playback. */
    private MediaPlayer mediaPlayer;

    /**
     * Private constructor to prevent direct instantiation.
     *
     * Use {@link #getInstance()} to obtain the single shared instance.
     *
     */
    private Music() {}

    /**
     * Returns the single shared instance of {@code Music}.
     *
     * If no instance exists yet, one is created. This ensures only one
     * audio manager is active throughout the application lifecycle.
     *
     * @return the singleton {@code Music} instance.
     */
    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    /**
     * Loads and plays an audio file in an infinite loop at a reduced volume.
     *
     * Before playing, any currently active audio is stopped and disposed of
     * to prevent overlapping playback. The audio file is loaded from the
     * application's {@code /Sounds/} resource folder.
     *
     * @param fileName the name of the audio file to play (e.g., {@code "background.mp3"}).
     *                 The file must exist under {@code /com/example/sudoku_game/Sounds/}.
     */
    @Override
    public void playLoop(String fileName) {

        // Stop and release any currently playing audio
        stopAndDispose();

        try {

            // Resolve the audio file from the resources folder
            URL resource = getClass().getResource("/com/example/sudoku_game/Sounds/" + fileName);

            if (resource != null) {
                // Create and configure the media player
                Media media = new Media(resource.toExternalForm());
                mediaPlayer = new MediaPlayer(media);

                // Set playback to loop indefinitely at low volume
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.2);
                mediaPlayer.play();

            }
        } catch (Exception e) {
            System.out.println("Excepción al intentar reproducir (" + fileName + "): " + e.getMessage());
        }
    }

    /**
     * Stops the current audio playback and releases all associated resources.
     *
     * This method should be called before loading a new audio track or
     * when navigating between views to avoid memory leaks and audio conflicts.
     * If no audio is currently playing, this method has no effect.
     *
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
