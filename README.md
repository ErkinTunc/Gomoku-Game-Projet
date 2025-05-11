# Gomoku Mini-Project

## Overview

This project implements a terminal-based version of the classic strategy game **Gomoku** (Five in a Row).  
Players compete on a dynamically expandable grid to align pieces horizontally, vertically, or diagonally.

This project demonstrates strong Object-Oriented Design, Serialization-based Save/Load, AI opponent play, and Dynamic Game Expansion.

---

## How to Compile and Run

### Option 1: Manually from Terminal

1. Open a terminal in the project root.
2. Compile:

```bash
javac -d target/classes src/main/java/*/*.java
```

3. Run:

```bash
java -cp target/classes app.Gomoku
```

### Option 2: Using Provided Scripts

- On **Windows**: `runGomoku.bat`
- On **Linux/Mac**: `runGomoku.sh`

## Game Rules

- Grid size: **15x15** by default (must be an odd number).
- Each player starts with **60 tokens**.
- First move **must be placed in the center** of the grid.
- Every next move must be placed **adjacent** to existing pieces (8 directions).
- Objective: **Align N pieces** (default N = 4) in any direction to win.

---

## Features

- **Dynamic Grid**: Automatically resizes when space runs out. Repositions existing pieces to the center.
- **Win Detection**: Checks alignments efficiently in all directions using recursion.
- **AI Opponent**: A basic AI (`AIPlayer`) blocks threats and simulates strategy.
- **Game Persistence**: Save and load games using `.dat` files and serialization.
- **Custom Rules**: Change grid size, win condition length, and token count.
- **Terminal UI**: Colored ANSI output, save/exit mid-turn options.
- **Implementation Details**:
  - 2D grid with `Piece[][]` and `EnumMap<Direction, Piece>` links.
  - Cleanly separated folders for AI, model, save, and utilities.

---

## Requirements

- **Java 11 or higher**
- No external libraries — built entirely with Java Standard Library.

---

## 📂 Project Structure

### Folder Overview:

- **app/**: Coordinates gameplay — initialization, terminal display, and save/load calls.
- **model/**: Core game logic — grid, tokens, players, and their interactions.
- **ai/**: AI implementation — simple logic to challenge human players.
- **save/**: Handles game state persistence using serialization.
- **util/**: Console utilities — color management and text formatting.

### Visual Structure:

```
Gomoku-Game-Projet/
├── .vscode/                # Optional VSCode configs
├── Gomoku/
│   ├── generateDocs.bat    # Windows: Generate JavaDoc
│   ├── generateDocs.sh     # Linux/Mac: Generate JavaDoc
│   ├── runGomoku.bat       # Windows: Compile and Run Gomoku
│   ├── runGomoku.sh        # Linux/Mac: Compile and Run Gomoku
│   ├── pom.xml             # Optional Maven configuration
│   ├── data/               # Saved game files (.dat)
│   ├── src/
│   │   ├── main/java/
│   │   │   ├── ai/
│   │   │   ├── app/
│   │   │   ├── model/
│   │   │   ├── save/
│   │   │   └── util/
│   │   └── test/java/
│   └── target/
│       ├── classes/
│       └── test-classes/
├── rapport/                # Final report LaTeX source
│   └── rapport.tex
├── README.md
└── .gitignore
```

### `app/` - Application Entry and Main Flow

| Class        | Description                                                                                    |
| ------------ | ---------------------------------------------------------------------------------------------- |
| `Gomoku`     | Controls the main menu, user navigation, settings menu, and game launching.                    |
| `GameEngine` | Manages the entire gameplay logic including players, moves, grid expansion, and win detection. |

### `model/` - Core Game Model

| Class       | Description                                                               |
| ----------- | ------------------------------------------------------------------------- |
| `Piece`     | Represents a piece placed on the board.                                   |
| `Grid`      | Manages the 2D grid structure, piece placement, and expansion.            |
| `Player`    | Abstract class defining basic player behavior.                            |
| `Human`     | Concrete class for a human player with user input handling.               |
| `Direction` | Enum representing the 8 possible directions on the grid (N, NE, E, etc.). |

### `ai/` - Artificial Intelligence

| Class      | Description                                                      |
| ---------- | ---------------------------------------------------------------- |
| `AIPlayer` | Computer-controlled player with simple move evaluation strategy. |

### `save/` - Save/Load System

| Class         | Description                                                 |
| ------------- | ----------------------------------------------------------- |
| `SaveManager` | Handles saving and loading game states using serialization. |

### `util/` - Utilities

| Class                        | Description                                                         |
| ---------------------------- | ------------------------------------------------------------------- |
| `ColorInConsole`             | Provides ANSI color codes for styled terminal output.               |
| `ConvertToJavaStringLiteral` | Formats text for easier console printing with tabs and line breaks. |

---

## 📁 Other Important Files

| File                                   | Purpose                                                                    |
| -------------------------------------- | -------------------------------------------------------------------------- |
| `README.md`                            | This document — explaining the game, structure, how to run.                |
| `runGomoku.bat` / `runGomoku.sh`       | Scripts to **compile** and **run** the Gomoku game easily from terminal.   |
| `generateDocs.bat` / `generateDocs.sh` | Scripts to **generate JavaDoc documentation** (HTML docs of your classes). |
| `data/` (folder)                       | Stores all **saved game** files (`.dat`) created during gameplay.          |
| `target/classes/` (folder)             | Stores **compiled `.class` files** after you compile your project.         |

---

### 📜 About Scripts

### runGomoku.bat / runGomoku.sh

- Cross-platform scripts to compile and launch the game in one step.

### generateDocs.bat / generateDocs.sh

- Scripts to generate JavaDoc (HTML format) into the `/doc/` folder.
- Use these to browse your codebase in a web browser.

- **`runGomoku.bat`** (Windows) and **`runGomoku.sh`** (Linux/macOS):

  - Automatically compile all Java source files under `src/main/java/`
  - Output compiled `.class` files into `target/classes/`
  - Run the Gomoku game (`app.Gomoku`)

- **`generateDocs.bat`** (Windows) and **`generateDocs.sh`** (Linux/macOS):
  - Use `javadoc` to generate HTML documentation of your source code
  - Output the documentation into the `/doc/` folder
  - Useful for browsing your project architecture visually via browser

---

## Save Files

- Saved games are stored inside the `/data/` directory automatically.
- Filenames are user-specified during saving (example: `myEpicGame.dat`).

---

## 🚧 Will Be Added

These features and improvements are under consideration or planned for future versions:

- **A Better Save/Load System interface**
  A more user-friendly save/load interface

- **Smarter AI**:  
  A more advanced decision-making engine using **Minimax** with **Alpha-Beta pruning** is being explored to replace the current scoring-based AI system (`AIPlayer` class).

- **Multiplayer Extension**:  
  The current `SaveManager` implementation assumes two players. Future versions may introduce support for **multiple players** via dynamic serialization (e.g., list-based deserialization).

- **Grid Visualization Enhancements**:  
  Potential improvements in visual display, including an **interactive GUI** (JavaFX or Swing), or at least grid visualization with **coordinate legends**.

- **AI Strategy Tuning Panel**:  
  A settings menu allowing users to adjust AI behavior (defensive, balanced, aggressive) dynamically.

- **Game Statistics and Replay System**:  
  Ability to view game history, statistics (e.g., move counts, time per move), or replay previous games.

- **Online Play Mode**:  
  Investigating socket-based multiplayer via Java networking for LAN or remote play.

---

## Author

- **Erkin Tunç Boya**  
  _(Gomoku Project 2025 April-Mai)_

---

# 🎮 Enjoy Playing Gomoku and Good Luck! 🎮
