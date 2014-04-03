package piece;

import helpers.Flags.Colour;

public class Queen extends Piece {
    
    public Queen(Colour color, byte[] position) {
        super(color, position);
    }

	byte[] x = {-1, -1, 1, 1, -1, 1, 0, 0};
	byte[] y = {1, -1, -1, 1, 0, 0, -1, 1};
	
}
