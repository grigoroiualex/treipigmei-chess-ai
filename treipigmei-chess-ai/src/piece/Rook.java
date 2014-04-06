package piece;

import helpers.Flags.Colour;

public class Rook extends Piece {
    
    public Rook(Colour color, int[] position) {
        super(color, position);
        x = new int[]{-1, 1, 0, 0};
        y = new int[]{0, 0, -1, 1};
    }

}
