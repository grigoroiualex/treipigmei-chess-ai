package piece;

import helpers.Flags.Colour;

public class WhitePawn extends Piece {
    
    public WhitePawn(Colour color, byte[] position) {
        super(color, position);
    }
    
	byte[] x = {0};
	byte[] y = {-1};

}
