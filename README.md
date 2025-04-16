# Gomoku Mini-Project

## Overview

This project implements a terminal-based version of the classic strategy game **Gomoku** (also known as Five in a Row). The game is played on a grid where two players alternately place tokens, aiming to align a set number of tokens in a row, column, or diagonal to win. The project focuses on data structures and algorithmic strategies, including:

- A 2D array-based grid with directional links.
- Recursive verification of winning conditions.
- Dynamic customization of game parameters.
- Save/load functionality using Java NIO (to be implemented).
- AI strategy (coming soon).
- Dynamic grid expansion when full.

---

## Game Rules

- Grid size: **15x15 by default** (must be an odd number).
- Each player starts with **60 tokens**.
- First move **must be placed in the central cell**.
- All subsequent moves must be placed in a free cell **adjacent (8-connectivity)** to an occupied cell.
- The objective: **Align N tokens** of the same color (default is 5).

---

## Features

### ✔️ Grid Structure

- The board is implemented as a **2D array** (`Piece[][]`).
- Each placed piece holds references to its 8 neighbors using an `EnumMap<Direction, Piece>`, allowing directional traversal and efficient win-checking.

### ✔️ Token Placement Verification

- A recursive algorithm checks whether the latest move completes an alignment in any direction.
- Victory is declared if a required number of same-colored pieces are connected.

### ✔️ Terminal Display

- The grid is displayed in the terminal using `X` for black pieces, `O` for white pieces, and empty cells.
- Uses clean ASCII formatting for visual feedback.

### ✔️ Parameter Customization

In-game settings allow you to customize:

- Grid dimensions (must be an odd number).
- Number of tokens each player starts with.
- Required number of tokens to win.

### ✔️ Save & Load Game State (To Be Implemented)

- Future support for saving and restoring game state using:
  ```java
  void save(Path filePath);
  void load(Path filePath);
  ```
- Will use **Java NIO** for efficient file I/O.

### ✔️ Dynamic Grid Expansion

- When the current grid is full, a new grid of size `2n x 2n` is created (n being the current odd size).
- All existing pieces are re-centered in the new grid.
- This allows continued gameplay without hard limits on board size.

### ✔️ AI Strategy (Coming Soon)

- The menu already includes a "Play Against Computer" option.
- Future updates will introduce:
  - Basic AI (blocking opponent alignments).
  - Optional: Minimax algorithm for smarter play.

---

## Requirements

- **Java 11+**
- No external dependencies (uses only Java Standard Library)

---

## Usage

1. Compile the Java classes:
   ```bash
   javac *.java
   ```

2. Run the game:
   ```bash
   java Gomoku
   ```

3. Main menu options:
   - Start New Game (2 Players)
   - Play Against Computer *(AI coming soon)*
   - Change Settings (grid size, win length, token count)
   - Exit

---

## Project Structure

| Component             | Description                                                                 |
|----------------------|-----------------------------------------------------------------------------|
| `Piece`              | Represents a game token with neighbor links in 8 directions.                |
| `Grid`               | Manages piece placement, win checking, expansion, and display.              |
| `Direction`          | Enum for the 8 compass directions, with dx/dy and opposites.                |
| `Player`             | Abstract base class for players (name, color, tokens).                      |
| `Human`              | Terminal-controlled human player class.                                     |
| `Gomoku`             | Controls game loop, settings, flow, and player initialization.              |
| `SaveManager`        | *(Planned)* Will handle saving/loading game state to file.                  |
| `AIPlayer`           | *(Planned)* Will implement automated moves for computer player.             |

---

## Documentation

For a detailed explanation of the game structure and each feature, refer to:

- [📖 Organized Project Explanation](./html/projectOrganized.html)
- [📄 Original Project Description](./html/project.html)

---

## Useful References

- 📄 [EnumMap Java Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/EnumMap.html)

---

**Happy Coding & Enjoy Playing Gomoku! 🎮**
