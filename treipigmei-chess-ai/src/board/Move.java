package board;

public class Move {
    private String move;
    private int [] pos;
    
    public Move(String s) {
        this.move = new String(s);
        this.pos = new int[4];
        decodeMove();
    }
    
    /**
     * 
     * @return Source position
     */
    public int [] getFrom() {
        int [] aux = new int[2];
        aux[0] = this.pos[0];
        aux[1] = this.pos[1];
        
        return aux;
    }
    
    /**
     * 
     * @return Destination position
     */
    public int [] getTo() {
        int [] aux = new int[2];
        aux[0] = this.pos[2];
        aux[1] = this.pos[3];
        
        return aux;
    }
    
    /**
     * Transform move String in i and j position for "from" and "to" and
     * store in pos array.
     */
     private void decodeMove() {
         pos[0] = 8 - (move.charAt(1) - '0');
         pos[1] = move.charAt(0) - 'a';
         pos[2] = 8 - (move.charAt(3) - '0') % 8;
         pos[3] = move.charAt(2) - 'a';
    }
    
}
