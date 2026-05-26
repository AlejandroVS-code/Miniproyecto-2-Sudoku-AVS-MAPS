package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Custom Stage window for the final game results dashboard layout interface.
 * This class handles window property constraints, applies the unified application icon asset,
 * and renders the final victory or defeat presentation template view.
 * * @author Alejandro Valencia
 * @author Maria Alejandra Pizarro
 * @version 1.0
 */

public class EndStage extends Stage {


    /**
     * Constructs and initializes a new EndStage window container instance.
     * Extracts application brand icon stream contexts safely, parses the FXML structural tree elements,
     * unifies structural constraints, and pops up the final score metrics visual stage.
     * * @throws IOException If an input-output runtime exception occurs while fetching the FXML resource layout
     * files or if image asset binary streams fail to be extracted correctly.
     */
    public EndStage() throws IOException {
        Image icon = new Image(getClass().getResourceAsStream("/com/example/Sudoku_game/Imagenes/gameLogo.png"));


        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/Sudoku_game/view/EndView.fxml")
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
