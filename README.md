# Gomoku Mini-Project

## Overview

This project implements a terminal-based version of the classic strategy game **Gomoku** (Five in a Row).  
Players compete on a dynamically expandable grid to align pieces horizontally, vertically, or diagonally.

This project demonstrates strong Object-Oriented Design, Recursive Win Checking, Serialization-based Save/Load, AI opponent play, and Dynamic Game Expansion.

---

## Game Rules

- Grid size: **15x15** by default (must be an odd number).
- Each player starts with **60 tokens**.
- First move **must be placed in the center** of the grid.
- Every next move must be placed **adjacent** to existing pieces (8 directions).
- Objective: **Align N pieces** (default N = 4) in any direction to win.

---

## Features

### ✔️ Grid & Piece System
- 2D array structure (`Piece[][]`) with neighbor links via `EnumMap<Direction, Piece>`.
- Efficient win checking in 8 directions using recursion.

### ✔️ Dynamic Settings
- Customize:
  - Grid Size
  - Win Condition Length
  - Player Token Count
  - Enable/Disable Expandable Grid

### ✔️ Save & Load Functionality
- Save the game anytime.
- Load saved games from the `/data/` folder.
- Save files are serialized `.dat` files.

### ✔️ AI Opponent
- Play against an AI (`AIPlayer`).
- AI evaluates board state and blocks opponent threats.

### ✔️ Expandable Grid
- Grid automatically expands when full (to `2n-1 x 2n-1`).
- Existing pieces are repositioned in the center.

### ✔️ Terminal User Interface
- Clear, colored prompts and grids using ANSI codes.
- Mid-turn Save/Exit options.

---

## Requirements

- **Java 11 or higher**
- No external libraries — built entirely with Java Standard Library.

---

## 📂 Project Structure

### `model/` - Game Model Classes
| Class         | Description |
|---------------|-------------|
| `Piece`       | Represents a piece on the board with links to neighbors. |
| `Grid`        | Manages the board: placement, expansion, and display. |
| `Player`      | Abstract class defining common player properties. |
| `Human`       | Subclass of Player; manages human player's moves. |
| `Direction`   | Enum defining 8 compass directions and their utilities. |

### `ai/` - AI Player
| Class          | Description |
|----------------|-------------|
| `AIPlayer`     | AI opponent that evaluates threats and opportunities. |

### `save/` - Saving and Loading
| Class           | Description |
|-----------------|-------------|
| `SaveManager`   | Manages saving and loading games with serialization. |

### `util/` - Utilities
| Class               | Description |
|---------------------|-------------|
| `ColorInConsole`     | Utility for colored text outputs. |
| `convertToJavaStringLiteral` | Utility to help format console output. |

### `app/` - Main Application
| Class    | Description |
|----------|-------------|
| `Gomoku` | Controls game flow, settings, menus, and main loop. |

---

## 📁 Other Important Files

| File                     | Purpose |
|---------------------------|---------|
| `README.md`               | This document — explaining the game, structure, how to run. |
| `runGomoku.bat` / `runGomoku.sh` | Scripts to **compile** and **run** the Gomoku game easily from terminal. |
| `generateDocs.bat` / `generateDocs.sh` | Scripts to **generate JavaDoc documentation** (HTML docs of your classes). |
| `data/` (folder)          | Stores all **saved game** files (`.dat`) created during gameplay. |
| `target/classes/` (folder) | Stores **compiled `.class` files** after you compile your project. |

---

### 📜 About Scripts:

- **`runGomoku.bat`** (Windows) and **`runGomoku.sh`** (Linux/macOS):
  - Automatically compile all Java source files under `src/main/java/`
  - Output compiled `.class` files into `target/classes/`
  - Run the Gomoku game (`app.Gomoku`)

- **`generateDocs.bat`** (Windows) and **`generateDocs.sh`** (Linux/macOS):
  - Use `javadoc` to generate HTML documentation of your source code
  - Output the documentation into the `/doc/` folder
  - Useful for browsing your project architecture visually via browser

---

## How to Compile and Run

1. Open terminal inside project root.

2. Compile:

```bash
javac -d target/classes src/main/java/model/*.java src/main/java/util/*.java src/main/java/ai/*.java src/main/java/save/*.java src/main/java/app/*.java
```

3. Run:

```bash
java -cp target/classes app.Gomoku
```

Or simply use:
- `runGomoku.bat` (for Windows)
- `runGomoku.sh` (for Linux/macOS)

---

## Save Files

- Saved games are stored inside the `/data/` directory automatically.
- Filenames are user-specified during saving (example: `myEpicGame.dat`).

---

## 📁 Visual Project Structure

```
Gomoku-Game-Projet/
├── .vscode/                    # VSCode settings (optional)
├── Gomoku/
│   ├── generateDocs.bat         # Windows: Generate JavaDoc
│   ├── generateDocs.sh          # Linux/Mac: Generate JavaDoc
│   ├── runGomoku.bat            # Windows: Compile and Run Gomoku
│   ├── runGomoku.sh             # Linux/Mac: Compile and Run Gomoku
│   ├── pom.xml                  # Maven configuration (optional)
│   ├── data/                    # Saved games
│   │   └── ErkinVsAI.dat
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       ├── ai/           # AI logic
│   │   │       ├── app/          # Main application
│   │   │       │   └── Gomoku.java
│   │   │       ├── model/        # Game models (pieces, grid, players)
│   │   │       ├── save/         # Save/Load manager
│   │   │       └── util/         # Utilities
│   │   └── test/                 # (reserved for future test cases)
│   │       └── java/
│   └── target/
│       ├── classes/              # Compiled .class files
├── html/                         # Project descriptions (HTML)
├── rapport/                      # Final project report (LaTeX)
│   └── rapport.tex
├── README.md                      # This documentation
└── .gitignore                     # Git rules
```

---

## Author

- **Erkin Tunç Boya**  
*(Gomoku Project 2025 April-Mai)*

---

# 🎮 Enjoy Playing Gomoku and Good Luck! 🎮
