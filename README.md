# Othello-game
Othello Game in Java

# Overview

This project is a Java implementation of the classic strategy game Othello, where two players compete to dominate the board by flipping the opponent’s pieces. Built with an object-oriented design, this project features classes for the game board, players, positions, and specialized unplayable positions. The game offers essential functionalities like move validation, piece flipping, turn management, and game completion detection.

Features

	1.	Game Initialization: Sets up the Othello board with an initial configuration.
	2.	Move Validation: Ensures moves are legal, checking for potential flips of opponent pieces.
	3.	Piece Flipping: Flips opponent pieces when a valid move is made.
	4.	Game End Detection: Determines when the game ends, based on available moves.
	5.	Winner Calculation: Identifies the player with the highest score when the game concludes.
	6.	Save & Load: Option to load the board from a saved file for resuming gameplay.

Project Structure

	•	Game.java: Main class managing game flow, starting configurations, and player turns.
	•	Board.java: Handles board operations, including move validation, piece flipping, and detecting the game end.
	•	Player.java: Defines player properties, including name and symbol.
	•	Position.java: Represents individual board cells and checks for playability.
	•	UnplayablePosition.java: A subclass of Position, marking specific board cells as unplayable.

# Class Details

Game Class

	•	main: Entry point for running the game.
	•	start: Sets the initial board configuration.
	•	makeMove: Validates and performs player moves.
	•	isGameOver: Checks if the game has ended.
	•	getWinner: Calculates the winner based on the final board state.

Board Class

	•	initializeBoard: Sets up the board’s initial piece layout.
	•	canFlip: Checks if a move can flip opponent pieces in a direction.
	•	flipPieces: Flips opponent pieces after a valid move.
	•	hasValidMove: Verifies if a player has any legal moves available.
	•	switchPlayer: Alternates between players.

Player Class

	•	getName: Retrieves player names.
	•	getSymbol: Retrieves player symbols, differentiating their pieces on the board.

Position and UnplayablePosition Classes

	•	getPiece: Retrieves the piece at a position.
	•	setPiece: Sets the piece at a position.
	•	canPlay: Determines if a cell is playable.
	•	UnplayablePosition: Marks cells as invalid for gameplay, e.g., edges or corners.
 Future Enhancements

# Potential improvements:
	•	AI Opponent: Implement AI to allow single-player mode.
	•	Graphical Interface: Add a GUI for better user interaction.
	•	Advanced Move Suggestions: Highlight possible moves to help beginners.
