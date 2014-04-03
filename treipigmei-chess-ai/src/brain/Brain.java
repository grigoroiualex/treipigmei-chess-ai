package brain;
  
import helpers.Flags;
import piece.Piece;
import connection.ChessBoardConnect;
import board.Board;

/**
 * Class Brain computes the next simple move 
 * 
 * @author Florin
 *
 */
public class Brain {
  
    // declarare variabile statice
    static char[] moveToDo = new char[4];
    static byte[] from = new byte[2];
    static byte[] to = new byte[2];
    
   
    /**
     * Returns the next possible move for the current pawn
     * 
     * @return string with the move
     */
    public static String think() {
        
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();
        Piece pieceToMove = null;
        
        if (chessBoardConnect.getChessEngineColour() == Flags.Colour.WHITE) {
            pieceToMove = board.getWhitePiece();
            while(board.getOneValidMove(pieceToMove) == null) {
            	pieceToMove = board.getWhitePiece();
            }

            from = pieceToMove.getPosition();
            to = board.getOneValidMove(pieceToMove);
          
        } else {
        	pieceToMove = board.getBlackPiece();
            while(board.getOneValidMove(pieceToMove) == null) {
            	pieceToMove = board.getBlackPiece();
            }

            from = pieceToMove.getPosition();
            to = board.getOneValidMove(pieceToMove);
          
 
        }
     
         
       // schimbat din moveToDo.toString();
       return getMove(from[0], from[1], to[0], to[1]);
    }
    
    /**
	 * 
	 * @param lineFrom
	 * @param columnFrom
	 * @param lineTo
	 * @param columnTo
	 * @return the move needed to do
	 */
	public static String getMove(byte lineFrom, byte columnFrom, byte lineTo,
			byte columnTo) {
		
		moveToDo[0] = (char) ('a' + columnFrom);
		moveToDo[1] = (char) ('8' - lineFrom);
		moveToDo[2] = (char) ('a' + columnTo);
		moveToDo[3] = (char) ('8' - lineTo);
		
		return String.valueOf(moveToDo);

	}
         
}