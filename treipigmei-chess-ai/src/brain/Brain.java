package brain;

import board.Board;
import chessboard.ChessBoardConnect;

public class Brain {
    
    public static String think() {
        char [] moveToDo = new char[4];
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();
        
        if (chessBoardConnect.getWhiteOnTurn()) {
            byte i = 6;
            byte j = 0;
            while (board.getPiece(new byte[]{i, j}) != null) {
                i --;
            }
            moveToDo[0] = (char)('a' + j);
            moveToDo[1] = (char)('8' - i);
            moveToDo[2] = (char)('a' + j);
            moveToDo[3] = (char)('8' - i - 1);
        } else {
            byte i = 1;
            byte j = 0;
            while (board.getPiece(new byte[]{i, j}) != null) {
                i ++;
            }
            moveToDo[0] = (char)('a' + j);
            moveToDo[1] = (char)(i);
            moveToDo[2] = (char)('a' + j);
            moveToDo[3] = (char)(i + 1);
        }
        
        return moveToDo.toString();
    }

}
