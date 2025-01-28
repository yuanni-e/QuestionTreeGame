# QuestionTreeGame

## Description
**QuestionTreeGame** is an simplified interactive implementation of the "20 Questions" game (similar to the Akinator). The game leverages a binary search tree to store questions and answers. Over the course of its runtime, the program learns and improves by dynamically updating the tree based on the user's responses. Users can save and load their game progress using input and output files.

## Files
- QuestionTreeGame.java: implements game described above
- BinNode.java: binary tree node interface used to implement the game
- tree1.java: input file (can be blank or already populated): here left blank
- TreeTest.java: tester/driver file for the game using tree1.java as inpute, execute to play

## Features
- **Interactive Gameplay**: The computer guesses what the user is thinking by asking yes/no questions.
- **Self-Learning**: If the computer guesses incorrectly, it prompts the user for the correct answer and a distinguishing question, updating the tree dynamically.
- **File I/O**: Save the tree to a file and reload it for future games or being the game with an existing file.
- **Track Scores**: Keeps track of the number of wins and losses for the computer.


## Game Mechanics
1. **Tree Structure**:
   - Each node in the BST is either a question (non-leaf) or an answer (leaf).
   - The left child represents a "yes" response, and the right child represents a "no" response.

2. **Game Flow**:
   - The game starts with a single root node labeled "computer" if no file is provided.
   - As the user plays, new questions and answers are added to the tree.

3. **File Interaction**:
   - Pre-populated files can be loaded to initialize the tree with existing data.
   - After the game, the tree can be saved back to a file to retain new learning.

## Usage
1. Load files into Java IDE (this program was coded on jGRASP but other code editors should also work)
2. In a tester/driver file, instatiate a QuestionTreeGame object, read in data from a file, and run playGame() to begin playing (this process is outlined in TreeTest.java)
3. Run saveData() optionally to write data into a new file after the game is completed

## Input File Format
- The file should consist of:
  - **Questions**: Prefixed with "Q:".
  - **Answers**: Prefixed with "A:".
  - **Metadata**: Optional, prefixed with "#".

Example:
```
#20 Questions Game Data
Q: Is it a living thing?
Q: Does it have legs?
A: Dog
A: Snake
A: Rock
```

## Customization + possible improvements
- Pre-populating the game by using a prefilled text file as input.
- Modifying the question format or logic to support additional features (e.g., hints).
