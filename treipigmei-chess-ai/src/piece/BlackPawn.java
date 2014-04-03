package piece;

import helpers.Flags.Colour;

public class BlackPawn extends Piece {
    
    public BlackPawn(Colour color, byte[] position) {
        super(color, position);
        x = new byte[]{0, -1, 1};
    	y = new byte[]{1, 1, 1};
    }
	
	
	
}
