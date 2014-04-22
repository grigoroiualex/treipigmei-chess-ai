package piece;

import helpers.Flags.Colour;

public class WhitePawn extends Piece {
    
    public WhitePawn(Colour color, int[] position) {
        super(color, position);
        x = new int[]{0, -1, 1};
    	y = new int[]{-1, -1, -1};
    	this.value = 10;
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
			return "p";
	}
    
    @Override
    public int getPosValue(int x, int y){
        return positionValues[x][y];
    }
    
    public static int[][] positionValues = {
        { 0,  0,  0,  0,  0,  0,  0,  0},
        {50, 50, 50, 50, 50, 50, 50, 50},
        {10, 10, 20, 30, 30, 20, 10, 10},
        { 5,  5, 10, 25, 25, 10,  5,  5},
        { 0,  0,  0, 20, 20,  0,  0,  0},
        { 5, -5,-10,  0,  0,-10, -5,  5},
        { 5, 10, 10,-20,-20, 10, 10,  5},
        { 0,  0,  0,  0,  0,  0,  0,  0}
    };
}
