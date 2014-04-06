package piece;

import helpers.Flags.Colour;

public class Knight extends Piece {
    
    public Knight(Colour color, int[] position) {
        super(color, position);
        x = new int[]{-2, -2, -1, -1, 1, 1, 2, 2};
    	y = new int[]{-1, 1, -2, 2, -2, 2, -1, 2};
    }

	
	
}
