package piece;

import helpers.Flags.Colour;

public class Queen extends Piece {
    
    public Queen(Colour color, byte[] position) {
        super(color, position);
        x = new byte[] {-1, -1, 1, 1, -1, 1, 0, 0};
    	y = new byte[] {1, -1, -1, 1, 0, 0, -1, 1};
    }

	
	
}
