package piece;

import helpers.Flags.Colour;

public class Rook extends Piece {
    
    public Rook(Colour color, byte[] position) {
        super(color, position);
    }
	
	byte[] x = {-1, 1, 0, 0};
	byte[] y = {0, 0, -1, 1};

}
