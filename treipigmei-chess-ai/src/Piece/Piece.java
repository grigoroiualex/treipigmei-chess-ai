package Piece;

public abstract class Piece {

	
	public boolean isValid(byte x, byte y) {
		
		if(x >= 0 && x <=7 && y >= 0 && y <=7)
			return true;
		
		return false;
	}
	
	
	
	
	
	
}
