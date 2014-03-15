package board;

/**
 * Chess board
 * 
 * @author Florin
 *
 */
public class Board {
    private String [][] field;
    
    public Board() {
        field = new String[8][8];
        
        setPiece(new byte[]{0, 0}, "BR");
        setPiece(new byte[]{0, 1}, "BN");
        setPiece(new byte[]{0, 2}, "BB");
        setPiece(new byte[]{0, 3}, "BQ");
        setPiece(new byte[]{0, 4}, "BK");
        setPiece(new byte[]{0, 5}, "BB");
        setPiece(new byte[]{0, 6}, "BN");
        setPiece(new byte[]{0, 7}, "BR");
        
        for (byte i = 0; i < 8; i++) {
            setPiece(new byte[]{1, i}, "BP");
            for (byte j = 2; j <= 5; j++) {
                setPiece(new byte[]{j, i}, "##");
            }
            setPiece(new byte[]{6, i}, "WP");
        }
        
        setPiece(new byte[]{7, 0}, "WR");
        setPiece(new byte[]{7, 1}, "WN");
        setPiece(new byte[]{7, 2}, "WB");
        setPiece(new byte[]{7, 3}, "WQ");
        setPiece(new byte[]{7, 4}, "WK");
        setPiece(new byte[]{7, 5}, "WB");
        setPiece(new byte[]{7, 6}, "WN");
        setPiece(new byte[]{7, 7}, "WR");
    }
    
    /**
     * Put "piece" at position "pos".
     * @param pos
     * @param piece
     */
    public void setPiece(byte [] pos, String piece) {
        byte i = pos[0];
        byte j = pos[1];
        field[i][j] = piece;
    }
    
    /**
     * Get piese from position "pos".
     * @param pos
     * @return
     */
    public String getPiece(byte [] pos) {
        byte i = pos[0];
        byte j = pos[1];
        return field[i][j];
    }
    
    public void applyMove(Move move) {
        setPiece(move.getTo(), getPiece(move.getFrom()));
        setPiece(move.getFrom(), "##");
    }
    
    public void print() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
