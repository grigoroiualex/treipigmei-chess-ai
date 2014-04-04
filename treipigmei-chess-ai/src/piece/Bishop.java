package piece;

import helpers.Flags.Colour;

public class Bishop extends Piece {
    
    public Bishop(Colour color, byte[] position) {
        super(color, position);
        x = new byte[] {-1, -1, 1, 1};
    	y = new byte[] {1, -1, -1, 1};
    }

	
	
}
