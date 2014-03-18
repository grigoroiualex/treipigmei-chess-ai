package brain;
  
import connection.ChessBoardConnect;
import connection.ChessBoardConnect.Colour;
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
  
    static byte whiteRow = 6;
    static byte blackRow = 1;
    static byte j = 0;
    
    /**
     * Returns to initial values
     */
    public static void initialize() {
        whiteRow = 6;
        blackRow = 1;
        j = 0;
    }
    
    /**
     * Sets the next pawn on turn
     * 
     * @return  true if it's possible, false otherwise
     */
    public static byte changePawn() {
        byte aux = j;
        initialize();
        j = (byte)(aux + 1);
       
        // verific sa nu ies din tabla
        if (j < 8) {
            return j;
        }
        return -1;
    }
    
    /**
     * Returns the next possible move for the current pawn
     * 
     * @return string with the move
     */
    public static String think() {
        
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();
        
        if (chessBoardConnect.getChessEngineColour() == Colour.WHITE) {
            
            if (board.getPiece(new byte[] { whiteRow, j }) != null) {
                whiteRow--;
            }
            moveToDo[0] = (char) ('a' + j);
            moveToDo[1] = (char) ('8' - whiteRow - 1);
            moveToDo[2] = (char) ('a' + j);
            moveToDo[3] = (char) ('8' - whiteRow);
        } else {
            if (board.getPiece(new byte[] { blackRow, j }) != null) {
                blackRow++;
            }
            moveToDo[0] = (char) ('a' + j);
            moveToDo[1] = (char) ('8' - blackRow + 1);
            moveToDo[2] = (char) ('a' + j);
            moveToDo[3] = (char) ('8' - blackRow);
 
        }
     
         
       // schimbat din moveToDo.toString();
       return String.valueOf(moveToDo);
    }
         
}