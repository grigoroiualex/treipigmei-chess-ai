package piece;

import helpers.Flags;
import helpers.Flags.Colour;

/**
 * Class that represents a chess piece abstractisation
 * 
 * @author grigoroiualex
 *
 */
public class Piece {
	
	private int[] position = new int[2];
    private Flags.Colour colour;
    protected int[] x;
    protected int[] y;
    protected int value;
    
    public Colour getColor() {
        return colour;
    }

    public Piece() {
    }
    
    public Piece(Colour colour, int[] position) {
        this.colour = colour;
        this.position = position;
    }
    
    /**
     * Determines whether the position is a valid one
     * 
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return boolean Whether the position is valid
     */
	public static boolean isValid(int x, int y) {
		
		if(x >= 0 && x <= 7 && y >= 0 && y <= 7)
			return true;
		
		return false;
	}
	
	@Override
	public boolean equals(Object o) {

		for (int i = 0; i < 2; i++) {
			if (this.position[i] != ((Piece) o).getPosition()[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the position on the board of the current piece
	 * 
	 * @return position The position of the current piece
	 */
	public int[] getPosition() {
		return this.position;
	}

	/**
	 * Sets the piece on board at the given position  
	 * 
	 * @param position The position to set the piece at
	 */
	public void setPosition(int[] position) {
		this.position[0] = position[0];
		this.position[1] = position[1];
	}

	/**
	 * Returns the Y coordinate of the current piece
	 * 
	 * @return yCoord The Y coordinate
	 */
	public int[] getY() {
		return y;
	}

	/**
	 * Returns the X coordinate of the current piece
     * 
     * @return yCoord The X coordinate
	 */
	public int[] getX() {
		return x;
	}
	
	/**
	 * Returns the value of the current piece
	 * 
	 * @return value The value of the current piece
	 */
	public int getValue(){
        return this.value;
    }
	
	/**
	 * Returns the value of the piece at the given position
	 * 
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @return value The value of the piece
	 */
    public int getPosValue(int x, int y) {
        return 0;
    }

}
