package piece;

import helpers.Flags.Colour;

public class Knight extends Piece {
    
    public Knight(Colour color, byte[] position) {
        super(color, position);
    }

	byte[] x = {-2, -2, -1, -1, 1, 1, 2, 2};
	byte[] y = {-1, 1, -2, 2, -2, 2, -1, 2};
	
}
