package piece;

import debugging.DebugToFile;
import helpers.Flags;
import helpers.Flags.Colour;

public class Piece {
	
	private byte[] position = new byte[2];
    private Flags.Colour colour;
    protected byte[] x;
    protected byte[] y;
    private DebugToFile debugger;
    
    public Colour getColor() {
        return colour;
    }

    public Piece() {
    }
    
    public Piece(Colour colour, byte[] position) {
        this.colour = colour;
        this.position = position;
        debugger = DebugToFile.getInstance();
    }

	public static boolean isValid(byte x, byte y) {
		
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

	public byte[] getPosition() {
		return this.position;
	}

	public void setPosition(byte[] position) {
	    debugger.output("move.getTo() from Piece: " + position[0] + " " + position[1]);
		this.position[0] = position[0];
		this.position[1] = position[1];
	}

	public byte[] getY() {
		return y;
	}

	public byte[] getX() {
		return x;
	}

}
