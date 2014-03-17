package board;

public class Move {
    private String move;
    private byte [] pos;
    
    /**
     * 
     * 
     * @param s
     */
    public Move(String s) {
        this.move = new String(s);
        this.pos = new byte[4];
        decodeMove();
    }
    
    /**
     * 
     * 
     * @return Source position
     */
    public byte [] getFrom() {
        byte [] aux = new byte[2];
        aux[0] = this.pos[0];
        aux[1] = this.pos[1];
        
        return aux;
    }
    
    /**
     * 
     * 
     * @return Destination position
     */
    public byte [] getTo() {
        byte [] aux = new byte[2];
        aux[0] = this.pos[2];
        aux[1] = this.pos[3];
        
        return aux;
    }
    
    /**
     * Transforms move String at position i and j for <i>from</i>, <i>to</i>
     * and stores it in <i>pos</i> array.
     */
     private void decodeMove() {
         pos[0] = (byte)(8 - (move.charAt(1) - '0'));
         pos[1] = (byte)(move.charAt(0) - 'a');
         pos[2] = (byte)(8 - (move.charAt(3) - '0'));
         pos[3] = (byte)(move.charAt(2) - 'a');
    }
    
}
