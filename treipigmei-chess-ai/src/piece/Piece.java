package piece;

import helpers.Flags;
import helpers.Flags.Colour;

public class Piece {
	
	private byte[] position = new byte[2];
    private Flags.Colour colour;
    
    public Colour getColor() {
        return colour;
    }

    public Piece() {
        
    }
    
    public Piece(Colour colour, byte[] position) {
        this.colour = colour;
        this.position = position;
    }

	public static boolean isValid(byte x, byte y) {
		
		if(x >= 0 && x <= 7 && y >= 0 && y <= 7)
			return true;
		
		return false;
	}


	public byte[] getPosition() {
		return this.position;
	}

	public void setPosition(byte[] position) {
		this.position = position;
	}
	
}
