Collecting workspace information# Ludo Game with Expectiminimax Algorithm

## Project Overview
This project is a Java implementation of the classic **Ludo** board game, enhanced with an **Expectiminimax** algorithm for AI decision-making. The game allows two players: a human player and a CPU-controlled player. The CPU uses the Expectiminimax algorithm to evaluate possible moves and make optimal decisions based on probabilities and heuristics.

---

## Features
- **Two-player gameplay**: Human vs. CPU.
- **AI Decision-making**: The CPU uses the Expectiminimax algorithm to choose the best moves.
- **Graphical Representation**: The board is displayed in the console with symbols representing tokens and special squares.
- **Winning Conditions**: The game ends when one player moves all their tokens to the winning blocks.

---

## Project Structure
The project is organized into the following directories and files:

```
src/
├── algorithm/
│   └── ExpectMinMax.java       # Implementation of the Expectiminimax algorithm
├── game/
│   ├── Board.java              # Represents the game board
│   ├── Dice.java               # Simulates dice rolls
│   ├── Game.java               # Core game logic
│   ├── Player.java             # Represents a player and their tokens
│   ├── Role.java               # Enum for player roles (Player, CPU)
│   └── Square.java             # Represents a square on the board
└── main/
    ├── Main.java               # Entry point of the application
    └── Play.java               # Handles the game loop and user interaction
```

---

## How to clone the Project
- **Clone the Repository**:
   ```bash
   git clone https://github.com/abd-shan/Ludo-game
   cd Ludo
   ```

---

## Git Instructions
### Initialize a Git Repository
If you haven't already initialized a Git repository for this project:
```bash
git init
```

### Add Files to the Repository
Add all files to the staging area:
```bash
git add .
```

### Commit Changes
Commit the changes with a meaningful message:
```bash
git commit -m "Initial commit: Ludo game implementation"
```

### Push to a Remote Repository
1. Add a remote repository:
   ```bash
   git remote add origin <repository-url>
   ```
2. Push the code to the remote repository:
   ```bash
   git push -u origin main
   ```

---

## Additional Information
### Symbols Used in the Game
- 🔴: Human player's token.
- 🔵: CPU player's token.
- ⬜: Empty square.
- ☯: Special square.
- 🔄: Start square.
- ▶️ / ◀️: Winning block indicators.

### Expectiminimax Algorithm
The Expectiminimax algorithm is used by the CPU to evaluate possible moves. It considers:
1. **Probabilities**: The likelihood of dice rolls.
2. **Heuristics**: A scoring system to evaluate the board state.
3. **Depth**: The number of moves ahead to simulate.

This ensures that the CPU makes decisions that maximize its chances of winning while minimizing the player's advantage.

Enjoy playing the game! 🎲
