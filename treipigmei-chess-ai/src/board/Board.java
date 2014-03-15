package board;
import Piece.Bishop;
import Piece.BlackPawn;
import Piece.King;
import Piece.Knight;
import Piece.Piece;
import Piece.Queen;
import Piece.Rook;
import Piece.WhitePawn;

public class Board {
    private Piece[][] field;
    
    public Board() {
        field = new Piece[8][8];
        
        setPiece(new byte[]{0, 0}, new Rook());
        setPiece(new byte[]{0, 1}, new Knight());
        setPiece(new byte[]{0, 2}, new Bishop());
        setPiece(new byte[]{0, 3}, new Queen());
        setPiece(new byte[]{0, 4}, new King());
        setPiece(new byte[]{0, 5}, new Bishop());
        setPiece(new byte[]{0, 6}, new Knight());
        setPiece(new byte[]{0, 7}, new Rook());
        
        for (byte i = 0; i < 8; i++) {
            setPiece(new byte[]{1, i}, new BlackPawn());
            for (byte j = 2; j <= 5; j++) {
                setPiece(new byte[]{j, i}, null);
            }
            setPiece(new byte[]{6, i}, new WhitePawn());
        }
        
        setPiece(new byte[]{7, 0}, new Rook());
        setPiece(new byte[]{7, 1}, new Knight());
        setPiece(new byte[]{7, 2}, new Bishop());
        setPiece(new byte[]{7, 3}, new Queen());
        setPiece(new byte[]{7, 4}, new King());
        setPiece(new byte[]{7, 5}, new Bishop());
        setPiece(new byte[]{7, 6}, new Knight());
        setPiece(new byte[]{7, 7}, new Rook());
    }
    
    /**
     * Put "piece" at position "pos".
     * @param pos
     * @param piece
     */
    public void setPiece(byte [] pos, Piece piece) {
        byte i = pos[0];
        byte j = pos[1];
        field[i][j] = piece;
    }
    
    /**
     * Get piese from position "pos".
     * @param pos
     * @return
     */
    public Piece getPiece(byte [] pos) {
        byte i = pos[0];
        byte j = pos[1];
        return field[i][j];
    }
    
    public boolean movePiece(Move move) {
 //       if (getPiece(move.getFrom()) instanceof )
        
        return false;
    }
    
    public void applyPieceMove(Move move) {
        setPiece(move.getTo(), getPiece(move.getFrom()));
        setPiece(move.getFrom(), null);
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
