package board;

public class Move {
    private String move;
    private int [] pos;
    
    public Move() {
        this.pos = new int[4];
    }

    /**
     * Receives the input string and transforms it in table coordinates
     * 
     * @param move The received move
     */
    public Move(String s) {
        this.move = new String(s);
        this.pos = new int[4];
        decodeMove();
    }
    
    /**
     * Returns the position from which the piece is moved
     * 
     * @return source position
     */
    public int [] getFrom() {
        int [] aux = new int[2];
        aux[0] = this.pos[0];
        aux[1] = this.pos[1];
        
        return aux;
    }
    
    /**
     * Returns the position to which the piece is moved
     * 
     * @return destination position
     */
    public int [] getTo() {
        int [] aux = new int[2];
        aux[0] = this.pos[2];
        aux[1] = this.pos[3];
        
        return aux;
    }
    
    /**
     * Sets the position from which the piece is moved
     * 
     * @param from
     */
    public void setFrom(int [] from) {
        this.pos[0] = from[0];
        this.pos[1] = from[1];
    }
    
    /**
     * Sets the position to which the piece is moved
     * 
     * @param to
     */
    public void setTo( int [] to) {
        this.pos[2] = to[0];
        this.pos[3] = to[1];
    }
    
    /**
     * Transforms move String at position i and j for <i>from</i>, <i>to</i>
     * and stores it in <i>pos</i> array.
     */
     private void decodeMove() {
    	 
         pos[0] = (8 - (move.charAt(1) - '0'));
         pos[1] = (move.charAt(0) - 'a');
         pos[2] = (8 - (move.charAt(3) - '0'));
         pos[3] = (move.charAt(2) - 'a');
    }
    
}
