package piece;

public class Knight extends Piece {
    
    public Knight(String color) {
        super(color);
    }

	byte[] x = {-2, -2, -1, -1, 1, 1, 2, 2};
	byte[] y = {-1, 1, -2, 2, -2, 2, -1, 2};
	
}
