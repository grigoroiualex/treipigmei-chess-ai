package piece;

import helpers.Flags.Colour;

public class Rook extends Piece {
    
    public Rook(Colour color, byte[] position) {
        super(color, position);
        x = new byte[]{-1, 1, 0, 0};
        y = new byte[]{0, 0, -1, 1};
    }

}
