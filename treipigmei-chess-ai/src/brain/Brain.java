package brain;

import piece.Piece;
import connection.ChessBoardConnect;
import connection.ChessBoardConnect.Colour;
import board.Board;
  
public class Brain {
  
    // declarare variabile statice
    static char[] moveToDo = new char[4];
  
    static byte whiteRow = 6;
    static byte blackRow = 1;
    static byte j = 0;
    
    public static String think() {
        
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();
        
        if (chessBoardConnect.getChessEngineColour() == Colour.WHITE) {
            byte whiteRowAux = whiteRow;
            if (board.getPiece(new byte[] { whiteRow, j }) != null) {
                whiteRow--;
            }
            
            if (board.getPiece(new byte[] { whiteRowAux, j }) != null &&
                    board.getPiece(new byte[] { whiteRowAux, j }).getColor() == "WHITE") {
                if (Piece.isValid(whiteRow, (byte)(j - 1))) {
                    if (board.getPiece(new byte[] { whiteRow, (byte)(j - 1) }) != null &&
                            board.getPiece(new byte[] { whiteRow, (byte)(j - 1) }).getColor() == "BLACK") {
                        moveToDo[0] = (char) ('a' + j);
                        moveToDo[1] = (char) ('8' - whiteRow - 1);
                        moveToDo[2] = (char) ('a' + j - 1);
                        moveToDo[3] = (char) ('8' - whiteRow);
                    }
                } else if (Piece.isValid(whiteRow, (byte)(j + 1))) {
                    if (board.getPiece(new byte[] { whiteRow, (byte)(j + 1) }) != null &&
                            board.getPiece(new byte[] { whiteRow, (byte)(j + 1) }).getColor() == "BLACK") {
                        moveToDo[0] = (char) ('a' + j);
                        moveToDo[1] = (char) ('8' - whiteRow - 1);
                        moveToDo[2] = (char) ('a' + j + 1);
                        moveToDo[3] = (char) ('8' - whiteRow);
                    }
                } else {
                    moveToDo[0] = (char) ('a' + j);
                    moveToDo[1] = (char) ('8' - whiteRow - 1);
                    moveToDo[2] = (char) ('a' + j);
                    moveToDo[3] = (char) ('8' - whiteRow);
                }
            } else {
                // resign
            }
            
            
        } else {
            byte blackRowAux = blackRow;
            if (board.getPiece(new byte[] { blackRow, j }) != null) {
                blackRow++;
            }
            
            if (board.getPiece(new byte[] { blackRowAux, j }) != null &&
                    board.getPiece(new byte[] { blackRowAux, j }).getColor() == "BLACK") {
                if (Piece.isValid(blackRow, (byte)(j - 1))) {
                    if (board.getPiece(new byte[] { blackRow, (byte)(j - 1) }) != null &&
                            board.getPiece(new byte[] { blackRow, (byte)(j - 1) }).getColor() == "WHITE") {
                        moveToDo[0] = (char) ('a' + j);
                        moveToDo[1] = (char) ('8' - blackRow - 1);
                        moveToDo[2] = (char) ('a' + j - 1);
                        moveToDo[3] = (char) ('8' - blackRow);
                    }
                } else if (Piece.isValid(blackRow, (byte)(j + 1))) {
                    if (board.getPiece(new byte[] { blackRow, (byte)(j + 1) }) != null &&
                            board.getPiece(new byte[] { blackRow, (byte)(j + 1) }).getColor() == "WHITE") {
                        moveToDo[0] = (char) ('a' + j);
                        moveToDo[1] = (char) ('8' - blackRow - 1);
                        moveToDo[2] = (char) ('a' + j + 1);
                        moveToDo[3] = (char) ('8' - blackRow);
                    }
                } else {
                    moveToDo[0] = (char) ('a' + j);
                    moveToDo[1] = (char) ('8' - blackRow + 1);
                    moveToDo[2] = (char) ('a' + j);
                    moveToDo[3] = (char) ('8' - blackRow);
                }
            } else {
                // resign
            }
 
        }
     
         
       // schimbat din moveToDo.toString();
       return String.valueOf(moveToDo);
    }
         
}
