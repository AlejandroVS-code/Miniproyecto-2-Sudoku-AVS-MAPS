package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Represents the home window of the Sudoku Game application.
 *
 * This class is responsible for loading and displaying
 * the main menu interface using JavaFX.
 *
 * The stage configuration includes:
 * <ul>
 *     <li>Application title</li>
 *     <li>Window icon</li>
 *     <li>FXML view loading</li>
 *     <li>Scene initialization</li>
 * </ul>
 *
 * @author Alejandro Valencia Sandoval
 * @author Maria Alejandra Pizarro Sarria
 * @version 1.0
 */
public class HomeStage extends Stage {

    /**
     * Constructs and initializes the home stage.
     *
     * This constructor loads the FXML file associated with the
     * home view, configures the stage properties,
     * and displays the application window.
     *
     * @throws IOException if the FXML file cannot be loaded
     *                     or if a resource loading error occurs
     */
    public HomeStage() throws IOException {

        Image icon = new Image(getClass().getResourceAsStream("/com/example/sudoku_game/Imagenes/gameLogo.png"));


        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku_game/view/HomeView.fxml")
        );

        Parent root = loader.load();
        this.setResizable(false);
        this.setTitle("Sudoku game");
        this.getIcons().add(icon);


        Scene scene = new Scene(root);
        setScene(scene);
        show();
    }
}
