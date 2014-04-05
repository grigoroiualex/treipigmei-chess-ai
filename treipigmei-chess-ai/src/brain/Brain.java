package brain;

import java.util.ArrayList;
import helpers.Flags;
import piece.*;
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
    
    /*
     * TODO marirea lungimii tabloului moveToDo de tinut minte la rocada sunt
     * 2 mutari
     */
    
    
    //pentru mutari speciale cum ar fi rocada si promovarea pionului
    static byte[] special = new byte[2]; 
    
   
    /**
     * Returns the next possible move for the current pawn
     * 
     * @return string with the move
     */
    public static String think() {
    	
    	ArrayList<Integer> moves;
        
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();
        Piece pieceToMove = null;
        
        
        if (chessBoardConnect.getChessEngineColour() == Flags.Colour.WHITE) {
            pieceToMove = board.getWhitePiece();
            while(board.getValidMoves(pieceToMove) == null) {
            	pieceToMove = board.getWhitePiece();
            }

            from = pieceToMove.getPosition();
            moves = board.getValidMoves(pieceToMove);
            
            int move = moves.get((int) ((Math.random() * 100) % moves.size()));
            to = new byte[] {(byte) (move / 8), (byte) (move % 8)};
            
        } else {
        	pieceToMove = board.getBlackPiece();
            while(board.getValidMoves(pieceToMove) == null) {
            	pieceToMove = board.getBlackPiece();
            }

            from = pieceToMove.getPosition();
            moves = board.getValidMoves(pieceToMove);
            
            int move = moves.get((int) ((Math.random() * 100) % moves.size()));
            to = new byte[] {(byte) (move / 8), (byte) (move % 8)};
 
        }
     
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
		
		Board board = Board.getInstance();
		Piece currentPiece = board.getPiece(new byte[] {lineFrom, columnFrom});
		
		if((currentPiece instanceof BlackPawn && lineTo == 0) ||
				(currentPiece instanceof WhitePawn && lineTo == 7)) {
		        	
			Flags.PROMOTION = true;
			return String.valueOf(moveToDo) + "q";
		}
		
		Flags.PROMOTION = false;
		return String.valueOf(moveToDo);

	}
	
	public boolean isPositionAttacked(byte x, byte y) {
	    Board board = Board.getInstance();
	    ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
	    Piece piece;
	    byte i, j;
	    byte[] kx = {-2, -2, -1, -1, 1, 1, 2, 2};
	    byte[] ky = {-1, 1, -2, 2, -2, 2, -1, 2};
	    
	    i = 7;
	    // pentru fiecare patratica in jos
	    while(Piece.isValid(x, i) && (piece = board.getPiece(new byte[]{x, i})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Rook || piece instanceof Queen) {
                    return true;
                }
            }
            i--;
	    }
	    
	    i = 0;
	    // pentru fiecare patratica in sus
	    while(Piece.isValid(x, i) && (piece = board.getPiece(new byte[]{x, i})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Rook || piece instanceof Queen) {
                    return true;
                }
            }
            i++;
        }
	    
	    i = 7;
	    // pentru fiecare patratica in stanga 	    
	    while(Piece.isValid(i, y) && (piece = board.getPiece(new byte[]{x, i})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Rook || piece instanceof Queen) {
                    return true;
                }
            }
            i--;
        }
        
	    i = 0;
        // pentru fiecare patratica in dreapta
	    while(Piece.isValid(i, y) && (piece = board.getPiece(new byte[]{x, i})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Rook || piece instanceof Queen) {
                    return true;
                }
            }
            i++;
        }
	    
	    i = x; j = y;
	    // pentru fiecare patratica de pe diagonala NE
	    while(Piece.isValid(i, j) && (piece = board.getPiece(new byte[]{x, y})) != null) {
	        // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Bishop || piece instanceof Queen) {
                    return true;
                }
            }
            i++; j++;
	    }
	    
	    i = x; j = y;
        // pentru fiecare patratica de pe diagonala SE
        while(Piece.isValid(i, j) && (piece = board.getPiece(new byte[]{x, y})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Bishop || piece instanceof Queen) {
                    return true;
                }
            }
            i++; j--;
        }
        
        i = x; j = y;
        // pentru fiecare patratica de pe diagonala SV
        while(Piece.isValid(i, j) && (piece = board.getPiece(new byte[]{x, y})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Bishop || piece instanceof Queen) {
                    return true;
                }
            }
            i--; j--;
        }
        
        i = x; j = y;
        // pentru fiecare patratica de pe diagonala NV
        while(Piece.isValid(i, j) && (piece = board.getPiece(new byte[]{x, y})) != null) {
            // daca e propria culoare e in regula
            if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                break;
            // altfel verifica daca
            } else {
                // piesa este unu dintre tipurile care poate ataca in linie
                if(piece instanceof Bishop || piece instanceof Queen) {
                    return true;
                }
            }
            i--; j++;
        }
        
        // verific pentru fiecare pozitie in care ar putea fi calul
        for(int k = 0; k < 8; k++) {
            // daca e o piesa pe acea pozitie
            if(Piece.isValid((byte)(x + kx[k]), (byte)(y + ky[k])) && 
                    (piece = board.getPiece(new byte[]{x, y})) != null) {
                // daca e propria culoare e in regula
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                // altfel verifica daca
                } else {
                    // piesa este unu dintre tipurile care poate ataca in linie
                    if(piece instanceof Knight) {
                        return true;
                    }
                }
            }
        }
        
        // verifica pentru pozitiile in care ar putea fi pioni negri
        if(Piece.isValid((byte)(x - 1), (byte)(y - 1)) && 
                (piece = board.getPiece(new byte[]{(byte)(x - 1), (byte)(y - 1)})) != null) {
            // daca a gasit piesa verifica daca e pion si daca e advers
            if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                    piece instanceof BlackPawn) {
                return true;
            }
        }
        
        // verifica pentru pozitiile in care ar putea fi pioni albi
        if(Piece.isValid((byte)(x + 1), (byte)(y + 1)) && 
                (piece = board.getPiece(new byte[]{(byte)(x - 1), (byte)(y - 1)})) != null) {
            // daca a gasit piesa verifica daca e pion si daca e advers
            if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                    piece instanceof WhitePawn) {
                return true;
            }
        }
        
	    return false;
	}
}