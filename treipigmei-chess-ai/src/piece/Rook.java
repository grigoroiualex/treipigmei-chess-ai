package piece;

import helpers.Flags;
import helpers.Flags.Colour;

public class Rook extends Piece {
    
    public Rook(Colour color, int[] position) {
        super(color, position);
        x = new int[]{-1, 1, 0, 0};
        y = new int[]{0, 0, -1, 1};
        this.value = 50;
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "r";
		}
	
		return "R";
	}
    
    @Override
    public int getPosValue(int x, int y){
        return positionValues[x][y];
    }
    
    public static int[][] positionValues = {
       { 0,  0,  0,  0,  0,  0,  0,  0},
       { 5, 10, 10, 10, 10, 10, 10,  5},
       {-5,  0,  0,  0,  0,  0,  0, -5},
       {-5,  0,  0,  0,  0,  0,  0, -5},
       {-5,  0,  0,  0,  0,  0,  0, -5},
       {-5,  0,  0,  0,  0,  0,  0, -5},
       {-5,  0,  0,  0,  0,  0,  0, -5},
       { 0,  0,  0,  5,  5,  0,  0,  0}
    };
}
