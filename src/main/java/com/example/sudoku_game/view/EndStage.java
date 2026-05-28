package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the end game window (Stage) for the Sudoku application.
 * <p>
 * This class extends {@link Stage} and is responsible for initializing
 * and displaying the end screen view when the game has concluded.
 * It loads the FXML layout, configures the window properties,
 * and makes the stage visible.
 * </p>
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */

public class EndStage extends Stage {

    /**
     * Constructs a new {@code EndStage} and initializes the end game window.
     *
     * This constructor performs the following actions:
     *  Loads the application icon from the resources folder.
     *   Loads the FXML layout file for the end screen view.
     *   Configures window properties such as title, icon, and resizability.
     *   Displays the stage.
     * @throws IOException if the FXML file or the icon image cannot be loaded
     *                     from the specified resource paths.
     */


    public EndStage() throws IOException {

        // Load the application icon from resources
        Image icon = new Image(getClass().getResourceAsStream("/com/example/Sudoku_game/Imagenes/gameLogo.png"));

        // Load the FXML layout for the end screen view
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/Sudoku_game/view/EndView.fxml")
        );

        // Parse the FXML and obtain the root UI node
        Parent root = loader.load();

        // Configure window properties
        this.setResizable(false);
        this.setTitle("Sudoku game");
        this.getIcons().add(icon);

        // Create and assign the scene
        Scene scene = new Scene(root);
        setScene(scene);

        // Display the stage
        show();
    }

}
