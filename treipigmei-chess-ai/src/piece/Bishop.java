package piece;

import helpers.Flags.Colour;

public class Bishop extends Piece {
    
    public Bishop(Colour color, int[] position) {
        super(color, position);
        x = new int[] {-1, -1, 1, 1};
    	y = new int[] {1, -1, -1, 1};
    }

	
	
}
