package piece;

import helpers.Flags.Colour;

public class Bishop extends Piece {
    
    public Bishop(Colour color, byte[] position) {
        super(color, position);
    }

	byte[] x = {-1, -1, 1, 1};
	byte[] y = {1, -1, -1, 1};
	
}
