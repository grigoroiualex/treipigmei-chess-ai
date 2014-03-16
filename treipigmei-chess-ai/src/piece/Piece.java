package piece;

public class Piece {
    
    private String color;
    
    public String getColor() {
        return color;
    }

    public Piece() {
        
    }
    
    public Piece(String color) {
        this.color = new String(color);
    }

	public static boolean isValid(byte x, byte y) {
		
		if(x >= 0 && x <= 7 && y >= 0 && y <= 7)
			return true;
		
		return false;
	}
	
}
