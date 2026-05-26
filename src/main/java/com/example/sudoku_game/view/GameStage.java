package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import com.example.sudoku_game.controller.GameController;

/**
 * Custom Stage window for the main Sudoku gameplay screen.
 * This class encapsulates the window lifecycle settings, injects the application icon,
 * loads the FXML graphic hierarchy layout, and links keyboard inputs to the runtime controller.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */

public class GameStage extends Stage {
    /**
     * Constructs and initializes a new GameStage window container.
     * Loads the FXML user interface definition, binds the scene keyboard event listener
     * hooks to the controller, and displays the gameplay scene immediately on screen.
     * * @throws IOException If an input-output error occurs while loading the FXML view
     * resource layout file, or if asset binary streams fail to open.
     */
    public GameStage() throws IOException {
        Image icon = new Image(getClass().getResourceAsStream("/com/example/Sudoku_game/Imagenes/gameLogo.png"));


        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/Sudoku_game/view/GameView.fxml")
        );

        Parent root = loader.load();
        GameController controller = loader.getController();
        this.setResizable(false);
        this.setTitle("Sudoku game");
        this.getIcons().add(icon);


        Scene scene = new Scene(root);
        controller.setupKeyEvents(scene);
        setScene(scene);

        show();
    }
}
