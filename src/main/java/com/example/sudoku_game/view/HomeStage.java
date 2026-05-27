package com.example.sudoku_game.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeStage extends Stage {

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
