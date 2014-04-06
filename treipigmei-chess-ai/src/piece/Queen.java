package piece;

import helpers.Flags;
import helpers.Flags.Colour;

public class Queen extends Piece {
    
    public Queen(Colour color, int[] position) {
        super(color, position);
        x = new int[] {-1, -1, 1, 1, -1, 1, 0, 0};
    	y = new int[] {1, -1, -1, 1, 0, 0, -1, 1};
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "q";
		}
	
		return "Q";
	}
	
}
