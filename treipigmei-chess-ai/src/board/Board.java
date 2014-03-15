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
        
        setPiece(new int[]{0, 0}, "BR");
        setPiece(new int[]{0, 1}, "BN");
        setPiece(new int[]{0, 2}, "BB");
        setPiece(new int[]{0, 3}, "BQ");
        setPiece(new int[]{0, 4}, "BK");
        setPiece(new int[]{0, 5}, "BB");
        setPiece(new int[]{0, 6}, "BN");
        setPiece(new int[]{0, 7}, "BR");
        
        for (int i = 0; i < 8; i++) {
            setPiece(new int[]{1, i}, "BP");
            for (int j = 2; j <= 5; j++) {
                setPiece(new int[]{j, i}, "##");
            }
            setPiece(new int[]{6, i}, "WP");
        }
        
        setPiece(new int[]{7, 0}, "WR");
        setPiece(new int[]{7, 1}, "WN");
        setPiece(new int[]{7, 2}, "WB");
        setPiece(new int[]{7, 3}, "WQ");
        setPiece(new int[]{7, 4}, "WK");
        setPiece(new int[]{7, 5}, "WB");
        setPiece(new int[]{7, 6}, "WN");
        setPiece(new int[]{7, 7}, "WR");
    }
    
    /**
     * Put "piece" at position "pos".
     * @param pos
     * @param piece
     */
    public void setPiece(int [] pos, String piece) {
        int i = pos[0];
        int j = pos[1];
        field[i][j] = piece;
    }
    
    /**
     * Get piese from position "pos".
     * @param pos
     * @return
     */
    public String getPiece(int [] pos) {
        int i = pos[0];
        int j = pos[1];
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
