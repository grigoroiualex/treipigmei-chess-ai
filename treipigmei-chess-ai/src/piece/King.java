package piece;

import helpers.Flags;
import helpers.Flags.Colour;

/**
 * Class that represents the King chess piece
 * 
 * @author grigoroiualex
 *
 */
public class King extends Piece {
    
    public King(Colour color, int[] position) {
        super(color, position);
        x = new int[] {-1, -1, 1, 1, -1, 1, 0, 0};
    	y = new int[] {1, -1, -1, 1, 0, 0, -1, 1};
    	this.value = 10000;
    }
	
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.getColor() == Flags.Colour.WHITE) {
			return "k";
		}
	
		return "K";
	}
	
    @Override
    public int getPosValue(int x, int y){
        return positionValues[x][y];
    }
    
    /*
     *  White King position value representation; 
     *  For black use mirrored matrix
     */
    public static int[][] positionValues = {
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        {20,  20,  0,  0,  0,  0, 20, 20},
        {20,  30, 10,  0,  0, 10, 30, 20},
    };

}
