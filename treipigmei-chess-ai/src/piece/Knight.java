package piece;

import helpers.Flags;
import helpers.Flags.Colour;

public class Knight extends Piece {
    
    public Knight(Colour color, int[] position) {
        super(color, position);
        x = new int[]{-2, -2, -1, -1, 1, 1, 2, 2};
    	y = new int[]{-1, 1, -2, 2, -2, 2, -1, 2};
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "c";
		}
	
		return "C";
	}
	
}
