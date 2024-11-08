package othello;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Board {
	private Position[][] boardPieces;
	private Player first;
	private Player second;
	private Player current;
	
	
	public Board(Player p1, Player p2, int start) {
		this.first = p1;
		this.second = p2;
		this.current = first;
		this.boardPieces = new Position[8][8];
		initializeBoard(start);
	}

	public Board(String saveFile) {
		this.boardPieces = new Position[8][8];
		load(saveFile);
	}

	private void initializeBoard(int start) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardPieces[i][j] = new Position();
			}
		}

		switch (start) {
		case 0: // Standard starting position
			boardPieces[3][3].setPiece(Position.WHITE);
			boardPieces[3][4].setPiece(Position.BLACK);
			boardPieces[4][3].setPiece(Position.BLACK);
			boardPieces[4][4].setPiece(Position.WHITE);
			break;
		case 1: // Offset starting position 1
			boardPieces[2][2].setPiece(Position.WHITE);
			boardPieces[2][3].setPiece(Position.BLACK);
			boardPieces[3][2].setPiece(Position.BLACK);
			boardPieces[3][3].setPiece(Position.WHITE);
			break;
		case 2: // Offset starting position 2
			boardPieces[2][4].setPiece(Position.WHITE);
			boardPieces[3][4].setPiece(Position.BLACK);
			boardPieces[2][5].setPiece(Position.BLACK);
			boardPieces[3][5].setPiece(Position.WHITE);
			break;
		case 3: // Offset starting position 3
			boardPieces[5][2].setPiece(Position.BLACK);
			boardPieces[4][2].setPiece(Position.WHITE);
			boardPieces[5][3].setPiece(Position.WHITE);
			boardPieces[4][3].setPiece(Position.BLACK);
			break;
		case 4: // Offset starting position 4
			boardPieces[5][4].setPiece(Position.BLACK);
			boardPieces[4][4].setPiece(Position.WHITE);
			boardPieces[5][5].setPiece(Position.WHITE);
			boardPieces[4][5].setPiece(Position.BLACK);
			break;
		}

		// Set the two center squares along the right column as unplayable
		boardPieces[3][7] = new UnplayablePosition();
		boardPieces[4][7] = new UnplayablePosition();
	}

	public void drawboard() {
		System.out.println("First player (Black): " + first.getName());
		System.out.println("Second player (White): " + second.getName());
		System.out.print("  ");
		for (int i = 0; i < 8; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < 8; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < 8; j++) {
				System.out.print(boardPieces[i][j].getPiece() + " ");
			}
			System.out.println();
		}
	}

	public boolean makeMove(Player player, int row, int col) {
		if (row < 0 || row >= 8 || col < 0 || col >= 8 || !boardPieces[row][col].canPlay()) {
			return false;
		}
		// Check if the move is valid and flip pieces
		boolean validMove = false;
		char opponentPiece;
		if (player.getSymbol() == Position.BLACK) {
			opponentPiece = Position.WHITE;
		} else {
			opponentPiece = Position.BLACK;
		}
		int[] directions = { -1, 0, 1 };

		for (int i = 0; i < directions.length; i++) {
			for (int j = 0; j < directions.length; j++) {
				int dr = directions[i];
				int dc = directions[j];
				if (dr == 0 && dc == 0)
					continue;
				if (canFlip(player.getSymbol(), opponentPiece, row, col, dr, dc)) {
					flipPieces(player.getSymbol(), row, col, dr, dc);
					validMove = true;
				}
			}
		}

		if (validMove) {
			boardPieces[row][col].setPiece(player.getSymbol());
			return true;
		}
		return false;
	}

	private boolean canFlip(char playerPiece, char opponentPiece, int row, int col, int dr, int dc) {
		int r = row + dr;
		int c = col + dc;
		boolean foundOpponent = false;

		while (r >= 0 && r < 8 && c >= 0 && c < 8) {
			if (boardPieces[r][c].getPiece() == opponentPiece) {
				foundOpponent = true;
			} else if (boardPieces[r][c].getPiece() == playerPiece) {
				return foundOpponent;
			} else {
				break;
			}
			r += dr;
			c += dc;
		}
		return false;
	}

	private void flipPieces(char playerPiece, int row, int col, int dr, int dc) {
		int r = row + dr;
		int c = col + dc;

		while (r >= 0 && r < 8 && c >= 0 && c < 8 && boardPieces[r][c].getPiece() != playerPiece) {
			boardPieces[r][c].setPiece(playerPiece);
			r += dr;
			c += dc;
		}
	}

	public boolean isGameOver() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardPieces[i][j].canPlay() && (canMove(first, i, j) || canMove(second, i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean canMove(Player player, int row, int col) {
		char opponentPiece;
		if (player.getSymbol() == Position.BLACK) {
			opponentPiece = Position.WHITE;
		} else {
			opponentPiece = Position.BLACK;
		}
		int[] directions = { -1, 0, 1 };

		for (int i = 0; i < directions.length; i++) {
			for (int j = 0; j < directions.length; j++) {
				int dr = directions[i];
				int dc = directions[j];
				if (dr == 0 && dc == 0)
					continue;
				if (canFlip(player.getSymbol(), opponentPiece, row, col, dr, dc)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getWinner() {
		int countBlack = 0;
		int countWhite = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardPieces[i][j].getPiece() == Position.BLACK) {
					countBlack++;
				} else if (boardPieces[i][j].getPiece() == Position.WHITE) {
					countWhite++;
				}
			}
		}

		if (countBlack > countWhite) {
			return first.getName();
		} else if (countWhite > countBlack) {
			return second.getName();
		} else {
			return "It's a tie!";
		}
	}

	public void save(String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
			writer.write(first.getName() + "\n");
			writer.write(second.getName() + "\n");
			writer.write(current.getName() + "\n");

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					writer.write(boardPieces[i][j].getPiece());
				}
				writer.write("\n");
			}
			System.out.println("Game saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Board(Player first, Player second, Position[][] boardPieces, Player current) {
		this.first = first;
		this.second = second;
		this.boardPieces = boardPieces;
		this.current = current;
	}

	public static Board load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String firstName = reader.readLine();
            String secondName = reader.readLine();
            String currentName = reader.readLine();

            Player first = new Player(firstName, Position.BLACK);
            Player second = new Player(secondName, Position.WHITE);
            Player current;

            if (currentName.equals(firstName)) {
                current = first;
            } else {
                current = second;
            }

            Position[][] boardPieces = new Position[8][8];
            for (int i = 0; i < 8; i++) {
                String line = reader.readLine();
                for (int j = 0; j < 8; j++) {
                    char piece = line.charAt(j);
                    if (piece == Position.EMPTY) {
                        boardPieces[i][j] = new Position();
                    } else if (piece == Position.BLACK || piece == Position.WHITE) {
                        boardPieces[i][j] = new Position();
                        boardPieces[i][j].setPiece(piece);
                    } else if (piece == UnplayablePosition.UNPLAYABLE) {
                        boardPieces[i][j] = new UnplayablePosition();
                    }
                }
            }
            return new Board(first, second, boardPieces, current);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	public boolean play() {
		while (true) {
			drawboard();
			boolean gameOver = takeTurn();
			if (gameOver) {
				return true; // Indicate game over
			}

			if (isGameOver()) {
				drawboard();
				System.out.println("Game over!");
				System.out.println("Winner: " + getWinner());
				return true; // Indicate game over
			}
		}
	}

	public boolean takeTurn() {
		Scanner scanner = new Scanner(System.in);

		if (!hasValidMove(current)) {
			System.out.println(current.getName() + " has no valid moves.");
			System.out.println("1. Save game");
			System.out.println("2. Concede game");
			System.out.println("3. Forfeit turn");
			if (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please enter a valid option.");
				scanner.next(); 
				return takeTurn(); // Retry
			}
			int choice = scanner.nextInt();
			scanner.nextLine(); 
			switch (choice) {
			case 1:
				System.out.println("Enter filename to save game:");
				String filename = scanner.nextLine();
				save(filename);
				return true; // Return to main menu after saving
			case 2:
				System.out.println(current.getName() + " concedes. Game over!");
				switchPlayer();
				System.out.println("Winner: " + current.getName());
				return true; // Return to main menu after conceding
			case 3:
				switchPlayer();
				return false; // Forfeit turn
			default:
				System.out.println("Invalid choice. Try again.");
				return takeTurn(); // Retry
			}
		}

		while (true) {
			System.out.println(
					current.getName() + "'s turn. Enter your move (row and column), or enter -1 to open menu:");
			if (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please enter a valid move.");
				scanner.next(); 
				continue;
			}
			int row = scanner.nextInt();
			if (row == -1) {
				System.out.println("1. Save game");
				System.out.println("2. Concede game");
				System.out.println("3. Return to game");
				if (!scanner.hasNextInt()) {
					System.out.println("Invalid input. Please enter a valid option.");
					scanner.next(); 
					continue;
				}
				int choice = scanner.nextInt();
				scanner.nextLine(); 

				switch (choice) {
				case 1:
					System.out.println("Enter filename to save game:");
					String filename = scanner.nextLine();
					save(filename);
					return true; // Return to main menu after saving
				case 2:
					System.out.println(current.getName() + " concedes. Game over!");
					switchPlayer();
					System.out.println("Winner: " + current.getName());
					return true; // Return to main menu after conceding
				case 3:
					continue;
				default:
					System.out.println("Invalid choice. Try again.");
				}
			} else {
				if (!scanner.hasNextInt()) {
					System.out.println("Invalid input. Please enter a valid column.");
					scanner.next(); // Consume the invalid input
					continue;
				}
				int col = scanner.nextInt();
				scanner.nextLine(); 
				if (makeMove(current, row, col)) {
					switchPlayer();
					break;
				} else {
					System.out.println("Invalid move. Try again.");
				}
			}
		}
		return false; // Continue playing
	}

	private boolean hasValidMove(Player player) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardPieces[i][j].canPlay() && canMove(player, i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	private void switchPlayer() {
		if (current == first) {
			current = second;
		} else {
			current = first;
		}
	}
}