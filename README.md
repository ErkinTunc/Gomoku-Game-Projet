
# Gomoku Mini-Project

## Overview

This project implements a terminal-based version of the classic strategy game **Gomoku** (also known as Five in a Row). The game is played on a grid where two players alternately place tokens, aiming to align a set number of tokens in a row, column, or diagonal to win. The project focuses on advanced data structures and algorithmic strategies, including:

- A doubly-linked grid structure with 8-connectivity.
- Recursive verification of winning conditions.
- Dynamic customization of game parameters.
- Save/load functionality using Java NIO.
- Optional AI strategy and infinite grid extension.

---

## Game Rules

- Grid size: **15x15 by default**.
- Each player starts with **60 tokens**.
- First move **must be in the central cell**.
- All subsequent moves must be placed in a free cell **adjacent (8-connectivity)** to an occupied cell.
- The objective: **Align 5 tokens** of the same color.

---

## Features

### ✔️ Grid Structure

- The board is implemented as a **doubly-linked matrix** where each cell holds references to its 8 neighboring cells.
- Each move updates neighboring relationships, ensuring bidirectional and consistent connectivity.

### ✔️ Token Placement Verification

- A recursive method checks whether a cell is part of an alignment of 4 matching tokens in any direction.

### ✔️ Terminal Display

- The grid is displayed in the terminal using **Unicode symbols** and **ANSI color codes** for clear visual feedback.

### ✔️ Parameter Customization

You can easily adjust:

- Grid dimensions.
- Number of starting tokens per player.
- Number of tokens required to win.

### ✔️ Save & Load Game State

- Supports saving and restoring game progress via:
  ```java
  void save(Path filePath);
  void load(Path filePath);
  ```

### ✔️ AI Strategy (Bonus)

- Implements a basic AI strategy to block the opponent’s potential alignments.
- Optional: Minimax algorithm can be integrated for a more challenging AI.

### ✔️ Infinite Grid Expansion (Bonus)

- The grid can dynamically expand by adding rows or columns when players reach the grid's edges.
- The token count becomes unlimited in this mode.

---

## Requirements

- **Java 11+**
- No external dependencies (Standard Java libraries)

---

## Usage

1. Compile the Java classes:
   ```bash
   javac *.java
   ```

2. Run the main program:
   ```bash
   java Main
   ```

3. Follow on-screen instructions to:

   - Enter player names.
   - Select token colors.
   - Input desired grid position (row, column).
   - Save/load the game if needed.

---

## Project Structure

| Component             | Description                                                                          |
|----------------------|--------------------------------------------------------------------------------------|
| `Cell`                | Represents a grid cell, with references to 8 neighbors and token status.              |
| `Grid`                | Manages grid initialization, display, and updates.                                   |
| `Player`              | Contains player information (name, color, tokens left).                              |
| `Main`                | Handles game flow, input/output, and player turns.                                   |
| `AIPlayer` (optional) | Implements AI behavior (basic strategy / Minimax).                                   |
| `SaveManager`         | Provides save/load functionalities using Java NIO.                                   |

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
