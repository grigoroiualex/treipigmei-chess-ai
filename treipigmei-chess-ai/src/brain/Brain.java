package brain;

import piece.Piece;
import connection.ChessBoardConnect;
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
		byte[] opponent1 = new byte[] { whiteRow, (byte)(j - 1) };
        byte[] opponent2 = new byte[] { whiteRow, (byte)(j + 1) };

		if (chessBoardConnect.getWhiteOnTurn()) {
		    byte whiteRowAux = whiteRow;
			if (board.getPiece(new byte[] { whiteRow, j }) != null) {
				whiteRow--;
			}
			
			if (board.getPiece(new byte[] { whiteRowAux, j }).getColor() == "WHITE") {
			    
    			moveToDo[0] = (char) ('a' + j);
    			moveToDo[1] = (char) ('8' - whiteRow - 1);
    			
    			opponent1 = new byte[] { whiteRow, (byte)(j - 1) };
    			opponent2 = new byte[] { whiteRow, (byte)(j + 1) };
    			
    			if (Piece.isValid(opponent1[0], opponent1[1]) && board.getPiece(opponent1) instanceof Piece) {
			        if (board.getPiece(opponent1).getColor() == "BLACK") {
			            moveToDo[2] = (char) ('a' + opponent1[1]);
			            moveToDo[3] = (char) ('8' - opponent1[0]); 
			            j--;
			        }
    			} else {
        			if (Piece.isValid(opponent2[0], opponent2[1]) && board.getPiece(opponent2) instanceof Piece) {
                        if (board.getPiece(opponent2).getColor() == "BLACK") {
                            moveToDo[2] = (char) ('a' + opponent2[1]);
                            moveToDo[3] = (char) ('8' - opponent2[0]); 
                            j++;
                        }
                    } else {
            			moveToDo[2] = (char) ('a' + j);
            			moveToDo[3] = (char) ('8' - whiteRow);
                    }
    			}
			} else {
			    // resign
			}
		} else {
		    byte blackRowAux = blackRow;
			if (board.getPiece(new byte[] { blackRow, j }) != null) {
				blackRow++;
			}
			
			if (board.getPiece(new byte[] { blackRowAux, j }).getColor() == "BLACK") {
			
    			moveToDo[0] = (char) ('a' + j);
    			moveToDo[1] = (char) ('8' - blackRow + 1);
    			
    			opponent1 = new byte[] { blackRow, (byte)(j - 1) };
                opponent2 = new byte[] { blackRow, (byte)(j + 1) };
    			
    			if (Piece.isValid(opponent1[0], opponent1[1]) && board.getPiece(opponent1) instanceof Piece) {
                    if (board.getPiece(opponent1).getColor() == "WHITE") {
                        moveToDo[2] = (char) ('a' + opponent1[1]);
                        moveToDo[3] = (char) ('8' - opponent1[0]); 
                        j--;
                    }
                } else {
                    if (Piece.isValid(opponent2[0], opponent2[1]) && board.getPiece(opponent2) instanceof Piece) {
                        if (board.getPiece(opponent2).getColor() == "WHITE") {
                            moveToDo[2] = (char) ('a' + opponent2[1]);
                            moveToDo[3] = (char) ('8' - opponent2[0]);
                            j++;
                        }
                    } else {
                        moveToDo[2] = (char) ('a' + j);
                        moveToDo[3] = (char) ('8' - blackRow);
                    }
                }
			} else {
			    // resign
			}
		}
		
		

		// schimbat din moveToDo.toString();
		return String.valueOf(moveToDo);
	}

}
