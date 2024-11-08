package othello;

public class UnplayablePosition extends Position {
    public static final char UNPLAYABLE = '*';

    public UnplayablePosition() {
        setPiece(UNPLAYABLE);
    }

    @Override
    public boolean canPlay() {
        return false;
    }
}