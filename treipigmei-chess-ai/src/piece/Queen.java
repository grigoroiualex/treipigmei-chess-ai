package piece;

public class Queen extends Piece {
    
    public Queen(String color) {
        super(color);
    }

	byte[] x = {-1, -1, 1, 1, -1, 1, 0, 0};
	byte[] y = {1, -1, -1, 1, 0, 0, -1, 1};
	
}
