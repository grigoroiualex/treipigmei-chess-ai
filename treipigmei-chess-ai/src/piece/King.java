package piece;

import helpers.Flags.Colour;

public class King extends Piece {
    
    public King(Colour color, int[] position) {
        super(color, position);
        x = new int[] {-1, -1, 1, 1, -1, 1, 0, 0};
    	y = new int[] {1, -1, -1, 1, 0, 0, -1, 1};
    }
	
	

}
