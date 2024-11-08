package othello;

import java.util.Scanner;

public class Game {
	private Board board;

	public Game(Player p1, Player p2, int start) {
		this.board = new Board(p1, p2, start);
	}

	public Game(Board board) {
		this.board = board;
	}

	public void start() {
		boolean gameOver = board.play();
		if (gameOver) {
			System.out.println("Returning to main menu.");
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Welcome to Othello!");
			System.out.println("1. Start New Game");
			System.out.println("2. Load Game");
			System.out.println("3. Quit");
			int choice = scanner.nextInt();
			scanner.nextLine(); 

			if (choice == 3) {
				System.out.println("Quitting the game.");
				break;
			}

			Game game;
			if (choice == 1) {
				System.out.println("Enter name for Player 1:");
				String player1Name = scanner.nextLine();
				System.out.println("Enter name for Player 2:");
				String player2Name = scanner.nextLine();
				Player player1 = new Player(player1Name, Position.BLACK);
				Player player2 = new Player(player2Name, Position.WHITE);

				System.out.println("Choose starting position:");
				System.out.println("1. Standard Starting Positions");
				System.out.println("2. Offset Starting Position");
				int startChoice = scanner.nextInt();
				scanner.nextLine(); 

				int start = 0; // Default to standard starting position
				if (startChoice == 2) {
					System.out.println("Choose offset starting position (1-4):");
					start = scanner.nextInt();
					scanner.nextLine(); 
					if (start < 1 || start > 4) {
						System.out.println("Invalid choice. Defaulting to standard starting position.");
						start = 0;
					}
				}

				game = new Game(player1, player2, start);
			} else if (choice == 2) {
				System.out.println("Enter filename to load game:");
				String filename = scanner.nextLine();
				Board board = Board.load(filename);
				if (board != null) {
					game = new Game(board);
				} else {
					System.out.println("Failed to load the game.");
					continue;
				}
			} else {
				System.out.println("Invalid choice. Please try again.");
				continue;
			}

			game.start();
		}
		scanner.close();
	}
}