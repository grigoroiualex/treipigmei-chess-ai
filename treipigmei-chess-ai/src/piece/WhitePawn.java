package piece;

import helpers.Flags.Colour;

public class WhitePawn extends Piece {
    
    public WhitePawn(Colour color, int[] position) {
        super(color, position);
        x = new int[]{0, -1, 1};
    	y = new int[]{-1, -1, -1};
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
			return "p";
	}
}
