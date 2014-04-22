package piece;

import helpers.Flags;
import helpers.Flags.Colour;

public class Knight extends Piece {
    
    public Knight(Colour color, int[] position) {
        super(color, position);
        x = new int[]{-2, -2, -1, -1, 1, 1, 2, 2};
    	y = new int[]{-1, 1, -2, 2, -2, 2, -1, 2};
    	this.value = 32;
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "c";
		}
	
		return "C";
	}
    
    @Override
    public int getPosValue(int x, int y) {
        return positionValues[x][y];
    }
    
    private static int[][] positionValues = {
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-30,  0, 10, 15, 15, 10,  0,-30},
        {-30,  5, 15, 20, 20, 15,  5,-30},
        {-30,  0, 15, 20, 20, 15,  0,-30},
        {-30,  5, 10, 15, 15, 10,  5,-30},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50},
    };
	
}
