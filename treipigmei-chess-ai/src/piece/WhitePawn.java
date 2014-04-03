package piece;

import helpers.Flags.Colour;

public class WhitePawn extends Piece {
    
    public WhitePawn(Colour color, byte[] position) {
        super(color, position);
        x = new byte[]{0, -1, 1};
    	y = new byte[]{-1, -1, -1};
    }

}
