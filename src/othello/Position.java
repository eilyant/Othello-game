package othello;

public class Position {
	private char piece;
	public static final char EMPTY = '.';
	public static final char BLACK = 'B';
	public static final char WHITE = 'W';

	public Position() {
		this.piece = EMPTY;
	}

	public char getPiece() {
		return piece;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}

	public boolean canPlay() {
		return piece == EMPTY;
	}
}
