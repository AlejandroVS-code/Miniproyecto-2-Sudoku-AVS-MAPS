package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import com.example.sudoku_game.controller.GameController;


public class GameStage extends Stage {

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
