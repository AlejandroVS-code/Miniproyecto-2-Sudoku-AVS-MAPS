# 🎮 Miniproyecto 2 — Sudoku 6x6

A fully functional 6x6 Sudoku game built with Java, JavaFX, and Scene Builder, following the MVC architecture pattern and event-driven programming principles.

---

## 👥 Authors

| Name | GitHub                                                   |
|---|----------------------------------------------------------|
| Alejandro Valencia | [@AlejandroVS-code](https://github.com/AlejandroVS-code) |
| Maria Alejandra Pizarro | [@Alejapizasar](https://github.com/Alejapizasar)         |

---

## 🕹️ How to Play

- Select a cell by clicking on it
- Enter a number from **1 to 6** using the keyboard
- Each row, column, and 2x3 block must contain the numbers 1–6 without repetition
- The game is won when the board is completely and correctly filled

### ⌨️ Keyboard Shortcuts

| Key | Action |
|---|---|
| `1` – `6` | Place a number in the selected cell |
| `Backspace` | Erase the content of the selected cell |
| `N` | Show the full solution in a separate window |
| `Ñ` | Auto-fill the entire board with the solution |

---

## ✨ Features

- ✅ Dynamic 6x6 board generated on every game start
- ✅ Exactly 2 clues revealed per 2x3 block
- ✅ Real-time validation of Sudoku rules (row, column, block)
- ✅ Visual error feedback (red highlight on invalid cells)
- ✅ Unlimited hint system — reveals the correct number for a cell
- ✅ Undo last move button
- ✅ Game timer
- ✅ Error counter
- ✅ Background music and sound effects
- ✅ Reset button to start a new game

---

## 🏗️ Architecture

This project follows the **Model-View-Controller (MVC)** pattern:

```
src/
├── model/
│   ├── Sudoku.java          # Core game logic, board generation, validation
│   ├── GameStatus.java      # Tracks mistakes and hints used
│   ├── Timer.java           # Game session stopwatch
│   ├── Music.java           # Audio manager (Singleton pattern)
│   └── interfaces/
│       ├── ISudoku.java
│       ├── IGameStatus.java
│       ├── ITimer.java
│       └── IMusic.java
├── view/
│   ├── HomeView.fxml
│   ├── GameView.fxml
│   └── EndView.fxml
└── controller/
    ├── HomeController.java
    ├── GameController.java
    ├── EndController.java
    └── KeyboardHandler.java  # Adapter class for keyboard events
```

---

## 🗂️ Data Structures

| Structure | Where used |
|---|---|
| `HashMap<String, Integer>` | Board state, solution map |
| `Stack<String>` | Move history, board generation, puzzle solving |

Both structures are used in the core board generation logic (`fillBoard`, `createPuzzle`, `isSolvable`).

---

## 🧩 Event Handling

| Class | Type | Responsibility |
|---|---|---|
| `KeyboardHandler` | Adapter class | Handles all keyboard input events |
| `TimerHandler` | Inner class | Handles the timer tick every second |

---

## 🛠️ Technologies

| Technology | Purpose |
|---|---|
| Java SE 17 | Core language |
| JavaFX | GUI framework |
| Scene Builder | FXML interface design |
| IntelliJ IDEA | IDE |
| Git & GitHub | Version control |
| Javadoc | Code documentation |

---

## ▶️ How to Run

1. Clone the repository:
```bash
git clone https://github.com/AlejandroVS-code/Miniproyecto-2-Sudoku-AVS-MAPS.git
```

2. Open the project in **IntelliJ IDEA**

3. Make sure **JavaFX SDK** is configured in your project settings

4. Run `Main.java`

---

## 📄 Documentation

Javadoc is available in the `/docs` folder of the repository.

---

## 📌 Repository

🔗 [https://github.com/AlejandroVS-code/Miniproyecto-2-Sudoku-AVS-MAPS](https://github.com/AlejandroVS-code/Miniproyecto-2-Sudoku-AVS-MAPS)
