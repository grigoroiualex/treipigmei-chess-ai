package board;

/**
 * Class that clone an instance of Board class.
 * 
 * @author Florin
 *
 */
public class Clone {
    private Board boardClone;
    
    public Clone() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardClone.setPiece(new int[] {i, j}, null);
            }
        }
    }
    
    /**
     * Copy "field" from board to boardClone.
     * 
     * @param board
     */
    public void newClone(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getPiece(new int [] {i, j}) != null) {
                    boardClone.setPiece(new int[] {i, j}, board.getPiece(new int [] {i, j}));
                }
            }
        }
    }
    
    /**
     * @param move
     * @return
     *          Return an instance of Board class with "move" applied.
     */
    public Board getClone(Move move) {
        boardClone.applyPieceMove(move);
        
        return boardClone;
    }
}
