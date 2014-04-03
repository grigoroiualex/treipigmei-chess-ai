package piece;

import helpers.Flags.Colour;

public class King extends Piece {
    
    public King(Colour color) {
        super(color);
    }
	
	byte[] x = {-1, -1, 1, 1, -1, 1, 0, 0};
	byte[] y = {1, -1, -1, 1, 0, 0, -1, 1};

}
