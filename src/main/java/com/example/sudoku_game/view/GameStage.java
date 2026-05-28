package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import com.example.sudoku_game.controller.GameController;

/**
 * Represents the main game window (Stage) for the Sudoku application.
 * <p>
 * This class extends {@link Stage} and is responsible for initializing
 * and displaying the game view. It loads the FXML layout, sets up the
 * game controller, configures the window properties, and makes the
 * stage visible.
 * </p>
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */
public class GameStage extends Stage {

    /**
     * Constructs a new {@code GameStage} and initializes the game window.
     *
     * This constructor performs the following actions:
     *
     *   Loads the application icon from the resources folder.
     *   Loads the FXML layout file for the game view.
     *   Retrieves the {@link GameController} associated with the FXML.
     *   Configures window properties such as title, icon, and resizability.
     *   Sets up keyboard events through the controller.
     *   Displays the stage.
     *
     * @throws IOException if the FXML file or the icon image cannot be loaded
     *                     from the specified resource paths.
     */
    public GameStage() throws IOException {

        // Load the application icon from resources
        Image icon = new Image(getClass().getResourceAsStream("/com/example/Sudoku_game/Imagenes/gameLogo.png"));

        // Load the FXML layout for the game view
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/Sudoku_game/view/GameView.fxml")
        );

        // Parse the FXML and obtain the root UI node
        Parent root = loader.load();

        // Retrieve the controller linked to the FXML
        GameController controller = loader.getController();

        // Configure window properties
        this.setResizable(false);
        this.setTitle("Sudoku game");
        this.getIcons().add(icon);

        // Create and assign the scene
        Scene scene = new Scene(root);

        // Set up keyboard event handling via the controller
        controller.setupKeyEvents(scene);

        // Set the scene and display the stage
        setScene(scene);

        show();

    }
}
