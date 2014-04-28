package piece;

import helpers.Flags.Colour;

/**
 * Class that represents the Black Pawn chess piece
 * 
 * @author grigoroiualex
 *
 */
public class BlackPawn extends Piece {
    
    public BlackPawn(Colour color, int[] position) {
        super(color, position);
        x = new int[]{0, -1, 1};
    	y = new int[]{1, 1, 1};
    	this.value = 10;
    }
	
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "P";
	}
   
    @Override
    public int getPosValue(int x, int y) {
        return positionValues[x][y];
    }
    
    /*
     *  Black Pawn position value representation; 
     */
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
