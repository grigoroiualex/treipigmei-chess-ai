package brain;

import board.Board;
import chessboard.ChessBoardConnect;

public class Brain {

	// declarare variabile statice
	static char[] moveToDo = new char[4];

	static byte whiteRow = 6;
	static byte blackRow = 1;
	static byte j = 0;

	public static String think() {

		ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
		Board board = Board.getInstance();

		if (chessBoardConnect.getWhiteOnTurn()) {

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
			System.out.println("BLACK <----");
		}

		// schimbat din moveToDo.toString();
		return String.valueOf(moveToDo);
	}

}
