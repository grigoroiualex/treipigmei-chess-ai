package piece;

import helpers.Flags;
import helpers.Flags.Colour;

public class Bishop extends Piece {
    
    public Bishop(Colour color, int[] position) {
        super(color, position);
        x = new int[] {-1, -1, 1, 1};
    	y = new int[] {1, -1, -1, 1};
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "b";
		}
	
		return "B";
	}
	
}
