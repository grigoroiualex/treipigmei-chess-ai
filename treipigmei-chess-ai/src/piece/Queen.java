package piece;

import helpers.Flags;
import helpers.Flags.Colour;

/**
 * Class that represents the Queen chess piece
 * 
 * @author grigoroiualex
 *
 */
public class Queen extends Piece {
    
    public Queen(Colour color, int[] position) {
        super(color, position);
        x = new int[] {-1, -1, 1, 1, -1, 1, 0, 0};
    	y = new int[] {1, -1, -1, 1, 0, 0, -1, 1};
    	this.value = 90;
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "q";
		}
	
		return "Q";
	}
    
    @Override
    public int getPosValue(int x, int y) {
        return positionValues[x][y];
    }
    
    /*
     *  White Queen position value representation; 
     *  For black use mirrored matrix
     */
    public static int[][] positionValues = {
        {-20,-10,-10, -5, -5,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        { -5,  0,  5,  5,  5,  5,  0, -5},
        {  0,  0,  5,  5,  5,  5,  0, -5},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}
    };
	
}
