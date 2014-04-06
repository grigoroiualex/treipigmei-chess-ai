package piece;

import helpers.Flags.Colour;

public class BlackPawn extends Piece {
    
    public BlackPawn(Colour color, int[] position) {
        super(color, position);
        x = new int[]{0, -1, 1};
    	y = new int[]{1, 1, 1};
    }
	
	
	
}
