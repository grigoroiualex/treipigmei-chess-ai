package piece;

import helpers.Flags;
import helpers.Flags.Colour;

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

	public int[] getPosition() {
		return this.position;
	}

	public void setPosition(int[] position) {
		this.position[0] = position[0];
		this.position[1] = position[1];
	}

	public int[] getY() {
		return y;
	}

	public int[] getX() {
		return x;
	}
	
	public int getValue(){
        return this.value;
    }
    
    public int getPosValue(int x, int xy) {
        return 0;
    }

}
