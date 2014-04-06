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
    static int[] from = new int[2];
    static int[] to = new int[2];
   
    /**
     * Returns the next possible move for the current pawn
     * 
     * @return string with the move
     */
    public static String think() {
    	
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();
        
        ArrayList<Integer> moves;
        Piece pieceToMove = null;
        
        //daca suntem cu albele
        if (chessBoardConnect.getChessEngineColour() == Flags.Colour.WHITE) {
        	
        	boolean whiteKingAttacked = isPositionAttacked(Flags.WHITE_KING.getPosition());
        	//daca pozitia regelui este atacata selectez regele si incerc sa-l mut
			if (whiteKingAttacked) {
				pieceToMove = Flags.WHITE_KING;
			} else {
				pieceToMove = board.getWhitePiece();
				while (board.getValidMoves(pieceToMove) == null) {
					pieceToMove = board.getWhitePiece();
				}
			}

            moves = board.getValidMoves(pieceToMove);
            int move = moves.get((int) ((Math.random() * 100) % moves.size()));
            
            /*
             * daca regele nu are mutari valide returnez linia 8 ceea ce insemana 0 dupa ce 
             * mutarea a fost decodata si testez inainte sa o aplic.
             */
            if(whiteKingAttacked) {
            	eliminateInvalidMoves(moves);
            	
            	if(moves.isEmpty()) {
            		to = new int[] {8, 7};
            	}
            } else {
	            to = new int[] {move / 8, move % 8};
            }
            
        } else {
        	boolean blackKingAttacked = isPositionAttacked(Flags.BLACK_KING.getPosition());
        	
        	//daca pozitia regelui este atacata selectez regele si incerc sa-l mut
        	if (blackKingAttacked) {
				pieceToMove = Flags.BLACK_KING;
			} else {
	        	pieceToMove = board.getBlackPiece();
	            while(board.getValidMoves(pieceToMove) == null) {
	            	pieceToMove = board.getBlackPiece();
	            }
			}

            moves = board.getValidMoves(pieceToMove);
            int move = moves.get((int) ((Math.random() * 100) % moves.size()));

            /*
             * daca regele nu are mutari valide returnez linia 8 ceea ce insemana 0 dupa ce 
             * mutarea a fost decodata si testez inainte sa o aplic.
             */
            if(blackKingAttacked) {
            	eliminateInvalidMoves(moves);
            	
            	if(moves.isEmpty()) {
            		to = new int[] {8, 7};
            	}
            } else {
	            to = new int[] {move / 8, move % 8};
            }
        }
       from = pieceToMove.getPosition();
       
       return getMove(from[0], from[1], to[0], to[1]);
    }
    
    /**
     * 
     * @param moves
     *  Eliminates the moves that are attacked when is the king is on turn;
     */
    public static void eliminateInvalidMoves(ArrayList<Integer> moves) {
    	ArrayList<Integer> newMoves = new ArrayList<Integer>();
    	
    	for(Integer i : moves) {
    		    		
    		if(!isPositionAttacked(new int[] {i / 8, i % 8})) {
    			newMoves.add(i);
    		}
    	}
    	moves = newMoves;
    }
    
    /**
	 * 
	 * @param lineFrom
	 * @param columnFrom
	 * @param lineTo
	 * @param columnTo
	 * @return the move needed to do
	 */
	public static String getMove(int lineFrom, int columnFrom, int lineTo,
			int columnTo) {
		
		moveToDo[0] = (char) ('a' + columnFrom);
		moveToDo[1] = (char) ('8' - lineFrom);
		moveToDo[2] = (char) ('a' + columnTo);
		moveToDo[3] = (char) ('8' - lineTo);
		
		Board board = Board.getInstance();
		Piece currentPiece = board.getPiece(new int[] {lineFrom, columnFrom});
		
		if((currentPiece instanceof BlackPawn && lineTo == 7) ||
				(currentPiece instanceof WhitePawn && lineTo == 0)) {
		        	
			Flags.PROMOTION = true;
			return String.valueOf(moveToDo) + "q";
		}
		
		Flags.PROMOTION = false;
		return String.valueOf(moveToDo);

	}
	
	public static boolean isPositionAttacked(int[] pos) {
	    Board board = Board.getInstance();
	    ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
	    Piece piece;
	    int x = pos[0];
	    int y = pos[1];
	    int i, j;
	    int[] kx = {-2, -2, -1, -1, 1, 1, 2, 2};
	    int[] ky = {-1, 1, -2, 2, -2, 2, -1, 2};
	    
	    i = y - 1;
	    // pentru fiecare patratica in jos
	    while(Piece.isValid(x, i)) {
	        // vede daca gaseste piesa
	        if((piece = board.getPiece(new int[]{x, i})) != null) {
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
	        }
	        i--;
	    }
	    
	    i = y + 1;
	    // pentru fiecare patratica in sus
	    while(Piece.isValid(x, i)) {
	        // vede daca gaseste piesa
            if((piece = board.getPiece(new int[]{x, i})) != null) {
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
            }
            i++;
        }
	    
	    i = x - 1;
	    // pentru fiecare patratica in stanga 	    
	    while(Piece.isValid(i, y)) {
	        // vede daca gaseste piesa    
	        if((piece = board.getPiece(new int[]{i, y})) != null) {
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
	        }
            i--;
        }
        
	    i = x + 1;
        // pentru fiecare patratica in dreapta
	    while(Piece.isValid(i, y)) {
	        // vede daca gaseste piesa
	        if((piece = board.getPiece(new int[]{i, y})) != null) {
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
	        }
            i++;
        }
	    
	    i = x + 1; j = y + 1;
	    // pentru fiecare patratica de pe diagonala NE
	    while(Piece.isValid(i, j)) {
	        // vede daca gaseste piesa
	        if((piece = board.getPiece(new int[]{i, j})) != null) {
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
	        }
            i++; j++;
	    }
	    
	    i = x + 1; j = y - 1;
        // pentru fiecare patratica de pe diagonala SE
        while(Piece.isValid(i, j)) {
            // vede daca gaseste piesa
            if((piece = board.getPiece(new int[]{i, j})) != null) {
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
            }
            i++; j--;
        }
        
        i = x - 1; j = y - 1;
        // pentru fiecare patratica de pe diagonala SV
        while(Piece.isValid(i, j)) {
            // vede daca gaseste piesa
            if((piece = board.getPiece(new int[]{i, j})) != null) {
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
            }
            i--; j--;
        }
        
        i = x - 1; j = y + 1;
        // pentru fiecare patratica de pe diagonala NV
        while(Piece.isValid(i, j)) {
            // vede daca gaseste piesa
            if((piece = board.getPiece(new int[]{i, j})) != null) {
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
            }
            i--; j++;
        }
        
        // verific pentru fiecare pozitie in care ar putea fi calul
        for(int k = 0; k < 8; k++) {
            // daca e o piesa pe acea pozitie
            if(Piece.isValid(x + kx[k], y + ky[k])) {
                // vede daca gaseste piesa                
                if((piece = board.getPiece(new int[]{x, y})) != null) {
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
        }
        
        // verifica pentru pozitiile in care ar putea fi pioni negri
        if(Piece.isValid(x - 1, y - 1)) {
            // vede daca gaseste piesa            
            if((piece = board.getPiece(new int[]{x - 1, y - 1})) != null) {
                // daca a gasit piesa verifica daca e pion si daca e advers
                if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                        piece instanceof BlackPawn) {
                    return true;
                }
            }
        }
        
        // verifica pentru pozitiile in care ar putea fi pioni albi
        if(Piece.isValid(x + 1, y + 1)) {
            // vede daca gaseste piesa
            if((piece = board.getPiece(new int[]{x + 1, y + 1})) != null) {
                // daca a gasit piesa verifica daca e pion si daca e advers
                if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                        piece instanceof WhitePawn) {
                    return true;
                }
            }
        }
        
	    return false;
	}
}
