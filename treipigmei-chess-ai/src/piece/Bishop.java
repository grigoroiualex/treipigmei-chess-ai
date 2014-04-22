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
	
	@Override
    public int getPosValue(int x, int y){
        return positionValue[x][y];
    }
    
    /*
     *  White Bishop position's value representation; for black use the same
     *  matrix in the mirror
     */
    public static int[][] positionValue = {
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20},
    };
	
}
