package com.example.sudoku_game.controller;

import com.example.sudoku_game.model.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class GameController {
    @FXML private Label titleText;
    @FXML private Label hintsText;
    @FXML private Label timeText; // Basado en tu FXML
    @FXML private Label mistakeText; // Basado en tu FXML
    @FXML private Button cell_00; @FXML private Button cell_01; @FXML private Button cell_02; @FXML private Button cell_03; @FXML private Button cell_04; @FXML private Button cell_05;
    @FXML private Button cell_10; @FXML private Button cell_11; @FXML private Button cell_12; @FXML private Button cell_13; @FXML private Button cell_14; @FXML private Button cell_15;
    @FXML private Button cell_20; @FXML private Button cell_21; @FXML private Button cell_22; @FXML private Button cell_23; @FXML private Button cell_24; @FXML private Button cell_25;
    @FXML private Button cell_30; @FXML private Button cell_31; @FXML private Button cell_32; @FXML private Button cell_33; @FXML private Button cell_34; @FXML private Button cell_35;
    @FXML private Button cell_40; @FXML private Button cell_41; @FXML private Button cell_42; @FXML private Button cell_43; @FXML private Button cell_44; @FXML private Button cell_45;
    @FXML private Button cell_50; @FXML private Button cell_51; @FXML private Button cell_52; @FXML private Button cell_53; @FXML private Button cell_54; @FXML private Button cell_55;






    // HashMap para mapear las coordenadas "fila,columna" a los objetos Button del FXML
    private HashMap<String, Button> boardButtons;
    private Sudoku sudokuModel;
    private String selectedKey;
    private GameStatus gameStatus;
    private Timer timer;
    private Timeline timeline;
    private MediaPlayer musicPlayer;



    private void playBackgroundMusic() {
        try {
            // Buscamos el recurso
            URL resource = getClass().getResource("/com/example/sudoku_game/Sounds/Background.mp3");

            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                musicPlayer = new MediaPlayer(media);

                // Repetición infinita
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                // Volumen sugerido (0.2 es perfecto para que no tape los clics)
                musicPlayer.setVolume(0.2);

                musicPlayer.play();
                System.out.println("Música neón iniciada...");
            } else {
                System.out.println("Error: No se encontró el archivo de sonido.");
            }
        } catch (Exception e) {
            System.out.println("No se pudo reproducir la música: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        sudokuModel = new Sudoku();
        boardButtons = new HashMap<>();
        gameStatus = new GameStatus();
        timer = new Timer();

        // Inicializamos el tablero lógico
        sudokuModel.generateBoard();

        // Mapeamos los botones del FXML al HashMap
        mapButtons();

        // Pintamos el tablero por primera vez
        renderBoard();
        hintsText.setText("Pistas: 0/3");
        mistakeText.setText("Errores: 0/3");
        setupTimer();
        playBackgroundMusic();
    }

    private void setupTimer() {
        // Creamos un evento que se dispara cada 1 segundo
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // ORDEN AL MODELO: "Añade un segundo"
            timer.addSecond();

            // ORDEN A LA VISTA: "Muestra lo que el modelo diga"
            timeText.setText(timer.getFormattedTime());
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Para que no se detenga solo
        timeline.play(); // ¡Arranca el reloj!
    }

    public void setupKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            String key = event.getText().toLowerCase();
            // Teclado numérico
            if (key.matches("[1-6]")) {
                placeNumber(Integer.parseInt(key));
            }
            // Teclas de solución
            if (key.equals("ñ")) {
                revealFullSolution(); // Autocompletar tablero actual
            } else if (key.equals("n")) {
                showSolutionWindow(); // Nueva ventana con la solución
            }
        });
    }

    private void revealFullSolution() {
        HashMap<String, Integer> sol = sudokuModel.getFullSolution();

        boardButtons.forEach((key, btn) -> {
            Integer value = sol.get(key);
            if (value != null) {
                btn.setText(value.toString());
                btn.getStyleClass().remove("error");
                btn.setDisable(true); // Bloquear celdas

                // Actualizar el modelo para que sepa que está lleno
                sudokuModel.validateAndPlace(key, value);
            }
        });

        // Validar victoria inmediata
        if (sudokuModel.isGameFinished()) {
            handleWin();
        }
    }

    private void showSolutionWindow() {
        Stage solutionStage = new Stage();
        solutionStage.setTitle("Solución Generada (Stack)");
        solutionStage.setResizable(false);

        // Un GridPane para organizar los números como el tablero
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setStyle("-fx-background-color: #0a0018; -fx-padding: 15; -fx-hgap: 8; -fx-vgap: 8;");

        HashMap<String, Integer> sol = sudokuModel.getFullSolution();

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                String key = r + "," + c;
                Label lbl = new Label(sol.get(key).toString());
                // Estilo neón cian para la solución
                lbl.setStyle("-fx-text-fill: #00e0ff; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #00e0ff; " +
                        "-fx-padding: 10; " +
                        "-fx-border-radius: 5; " +
                        "-fx-alignment: center;");
                lbl.setMinWidth(40);
                grid.add(lbl, c, r);
            }
        }

        Scene scene = new Scene(grid);
        solutionStage.setScene(scene);
        solutionStage.show();
    }










    @FXML void onNumberClick1(javafx.event.ActionEvent event) {placeNumber(1);}
    @FXML void onNumberClick2(javafx.event.ActionEvent event) {placeNumber(2);}
    @FXML void onNumberClick3(javafx.event.ActionEvent event) {placeNumber(3);}
    @FXML void onNumberClick4(javafx.event.ActionEvent event) {placeNumber(4);}
    @FXML void onNumberClick5(javafx.event.ActionEvent event) {placeNumber(5);}
    @FXML void onNumberClick6(javafx.event.ActionEvent event) {placeNumber(6);}

    @FXML
    void onHelpClick(javafx.event.ActionEvent event) {
        if (selectedKey == null) return;

        if (gameStatus.canUseHelp()) {
            // 1. Obtenemos el valor correcto del modelo
            int correctValue = sudokuModel.getHelpValue(selectedKey);

            // 2. ACTUALIZACIÓN CRÍTICA: Notificar al modelo que esta celda ya tiene su valor
            // Esto hace que el conteo interno de celdas llenas aumente.
            sudokuModel.validateAndPlace(selectedKey, correctValue);

            // 3. Actualizar la interfaz (Vista)
            Button currentButton = boardButtons.get(selectedKey);
            currentButton.setText(String.valueOf(correctValue));

            // Estética
            currentButton.getStyleClass().removeAll("error", "selected");
            currentButton.setDisable(true); // Bloquear para que no se pueda cambiar

            // 4. Actualizar contadores de ayuda
            gameStatus.addHelp();
            hintsText.setText(gameStatus.getHelpsFormatted());

            // 5. Verificar si con esta ayuda se completó el tablero
            if (sudokuModel.isGameFinished()) {
                handleWin();
            }

            selectedKey = null;
        }
    }




    @FXML void onResetClick(javafx.event.ActionEvent event) {

        timer.reset();
        gameStatus.reset();
        sudokuModel.generateBoard(); // Genera un tablero totalmente nuevo

        // 2. REINICIAR VISTA DE TEXTOS (Modelo de Mensajería)
        timeText.setText(timer.getFormattedTime());
        mistakeText.setText(gameStatus.getMistakesFormatted());
        hintsText.setText(gameStatus.getHelpsFormatted());

        // 3. LIMPIAR EL TABLERO VISUAL (Botones)
        for (String key : boardButtons.keySet()) {
            Button btn = boardButtons.get(key);

            // Quitar clases de estilo (seleccionado, error)
            btn.getStyleClass().removeAll("selected", "error");

            // Habilitar todos los botones para que el render pueda bloquear solo las pistas
            btn.setDisable(false);
        }
        renderBoard();

        // 5. LIMPIAR SELECCIÓN ACTUAL
        selectedKey = null;

        System.out.println("Juego reiniciado con éxito.");
    }


    @FXML
    void handleCellClick(javafx.event.ActionEvent event) {

        Button clickedButton = (Button) event.getSource();

        // Solo permitimos seleccionar celdas que no estén bloqueadas (las que no son pistas)
        if (clickedButton.isDisable()) return;

        for (String key : boardButtons.keySet()) {
            if (boardButtons.get(key).equals(clickedButton)) {

                // 1. Quitar la clase al anterior
                if (selectedKey != null) {
                    boardButtons.get(selectedKey).getStyleClass().remove("selected");
                }

                // 2. Actualizar la selección
                selectedKey = key;

                // 3. Añadir la clase al nuevo
                if (!clickedButton.getStyleClass().contains("selected")) {
                    clickedButton.getStyleClass().add("selected");
                }

                System.out.println("Celda seleccionada: " + selectedKey);
                break;
            }
        }

    }

    private void placeNumber(int number) {

        if (selectedKey == null) return;

        Button currentButton = boardButtons.get(selectedKey);

        // 1. Delegamos la lógica de validación y guardado al modelo Sudoku
        if (sudokuModel.validateAndPlace(selectedKey, number)) {
            // ÉXITO: El controlador solo actualiza la vista
            currentButton.setText(String.valueOf(number));
            currentButton.getStyleClass().remove("error");
            System.out.println("Celdas llenas: " + sudokuModel.getBoard().size());

            if (sudokuModel.isGameFinished()) {
                handleWin();
            }

        } else {
            // ERROR: El controlador solo notifica a los modelos
            gameStatus.addMistake();

            currentButton.setText(String.valueOf(number));
            if (!currentButton.getStyleClass().contains("error")) {
                currentButton.getStyleClass().add("error");
            }

            // 2. Delegamos la creación del mensaje al modelo GameStatus
            mistakeText.setText(gameStatus.getMistakesFormatted());
            if (gameStatus.isGameOver()) {
                handleLoss();
            }

        }


    }

    private void handleWin() {
        if (timeline != null) timeline.stop();

        boardButtons.forEach((key, btn) -> btn.setDisable(true));

        // Pasamos: tiempo, errores, ayudas (del modelo) y true (porque ganó)
        changeToEndStage(timer.getFormattedTime(),
                gameStatus.getMistakes(),
                gameStatus.getHelpsUsed(),
                true);
    }

    private void handleLoss() {

        if (timeline != null) timeline.stop();
        boardButtons.forEach((key, btn) -> btn.setDisable(true));

        // Pasamos: tiempo, errores, ayudas (del modelo) y false (porque perdió)
        changeToEndStage(timer.getFormattedTime(),
                gameStatus.getMistakes(),
                gameStatus.getHelpsUsed(),
                false);
    }



    private void changeToEndStage(String time, int mistakes, int helps, boolean won) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        // 2. Definimos qué pasará cuando termine la pausa
        pause.setOnFinished(event -> {
            try {
                if (musicPlayer != null) {
                    musicPlayer.stop();
                }

                // Aquí va tu código actual de cambio de ventana
                var resource = getClass().getResource("/com/example/Sudoku_game/view/EndView.fxml");

                if (resource == null) {
                    System.out.println("Error: No se encontró el archivo FXML.");
                    return;
                }


                FXMLLoader loader = new FXMLLoader(resource);
                Parent root = loader.load();
                // 1. Obtener el controlador de la nueva ventana
                EndController endController = loader.getController();

                // 2. Pasar los datos recolectados
                endController.setData(time, mistakes, helps, won);

                Stage stage = (Stage) timeText.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 3. ¡Arrancamos la pausa!
        pause.play();
    }







    /**
     * Maps FXML button IDs to the boardButtons HashMap.
     * We do this manually or with a loop to avoid using lists.
     */
    private void mapButtons() {

        boardButtons.put("0,0", cell_00); boardButtons.put("0,1", cell_01); boardButtons.put("0,2", cell_02); boardButtons.put("0,3", cell_03); boardButtons.put("0,4", cell_04); boardButtons.put("0,5", cell_05);
        boardButtons.put("1,0", cell_10); boardButtons.put("1,1", cell_11); boardButtons.put("1,2", cell_12); boardButtons.put("1,3", cell_13); boardButtons.put("1,4", cell_14); boardButtons.put("1,5", cell_15);
        boardButtons.put("2,0", cell_20); boardButtons.put("2,1", cell_21); boardButtons.put("2,2", cell_22); boardButtons.put("2,3", cell_23); boardButtons.put("2,4", cell_24); boardButtons.put("2,5", cell_25);
        boardButtons.put("3,0", cell_30); boardButtons.put("3,1", cell_31); boardButtons.put("3,2", cell_32); boardButtons.put("3,3", cell_33); boardButtons.put("3,4", cell_34); boardButtons.put("3,5", cell_35);
        boardButtons.put("4,0", cell_40); boardButtons.put("4,1", cell_41); boardButtons.put("4,2", cell_42); boardButtons.put("4,3", cell_43); boardButtons.put("4,4", cell_44); boardButtons.put("4,5", cell_45);
        boardButtons.put("5,0", cell_50); boardButtons.put("5,1", cell_51); boardButtons.put("5,2", cell_52); boardButtons.put("5,3", cell_53); boardButtons.put("5,4", cell_54); boardButtons.put("5,5", cell_55);
    }

    /**
     * Shows the generated Sudoku numbers on the UI buttons.
     */
    private void renderBoard() {
        HashMap<String, Integer> currentBoard = sudokuModel.getBoard();

        // Recorremos el tablero usando coordenadas
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                String key = row + "," + col;
                Button btn = boardButtons.get(key);

                if (btn != null) {
                    Integer value = currentBoard.get(key);
                    if (value != null) {
                        btn.setText(value.toString());
                        btn.setDisable(true); // Bloqueamos las pistas iniciales
                    } else {
                        btn.setText(""); // Celda vacía para jugar
                        btn.setDisable(false);
                    }
                }
            }
        }
    }
}

