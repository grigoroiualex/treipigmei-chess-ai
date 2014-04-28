package board;

import java.util.ArrayList;

import helpers.Flags;
import helpers.Flags.Colour;
import piece.*;

/**
 * Class that clone an instance of Board class.
 * 
 * @author Florin
 *
 */
public class Clone {
    private ArrayList<Piece> whites, blacks;
    private Piece[][] field;
    private boolean promotion = false; 
    private Flags.Colour engineColour;

    public Clone() {

    }

    public Clone(Piece [][] f, ArrayList<Piece> w, ArrayList<Piece> b, boolean promotion, Flags.Colour engineColour) {

        field = new Piece[8][8];
        whites = new ArrayList<>();
        blacks = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setPiece(new int[] {i, j}, f[i][j]);
            }
        }

        for (int i = 0; i < w.size(); i++) {
            this.whites.add(w.get(i));
        }

        for (int i = 0; i < b.size(); i++) {
            this.blacks.add(b.get(i));
        }

        this.promotion = promotion;
        this.engineColour = engineColour;
    }

    /**
     * Creates a copy of the current clone
     * 
     * @return clone
     */
    public Clone newClone() {
        return new Clone(field, whites, blacks, promotion, engineColour);
    }

    /**
     * Creates a copy of the new clone and applies the given move
     * 
     * @param move The move to be made to the current copy
     * @return clone Current instance with "move" applied
     */
    public Clone getCloneWithMove(Move move) {
        applyPieceMove(move);

        return this;
    }

    /**
     * Put <i>piece</i> at position <i>pos</i>.
     * 
     * @param position The position to set the given piece at
     * @param piece The piece to set
     */
    public void setPiece(int[] pos, Piece piece) {
        int i = pos[0];
        int j = pos[1];
        field[i][j] = piece;
    }

    /**
     * Get <i>piece</i> from position <i>pos</i>.
     * 
     * @param position The position from where to get the wnated piece
     * @return piece The piece at the position given
     */
    public Piece getPiece(int[] pos) {
        int i = pos[0];
        int j = pos[1];
        return field[i][j];
    }

    /**
     * Returns the colour of the engine
     * 
     * @return colour The colour of the engine
     */
    public Flags.Colour getEnginesColour() {
        return this.engineColour;
    }
    
    /**
     * Get the king's position on board
     * 
     * @param engineColour Player's colour
     * @return position The position of the king
     */
    public int[] getKingPosition(Flags.Colour kingColor) { 

        if(kingColor == Flags.Colour.WHITE) {
            for(int i = 7; i >= 0; i--) {
                for(int j = 7; j >= 0; j--) {
                    Piece piece = getPiece(new int[] {i, j});
                    if(piece != null) {
                        if(piece instanceof King && piece.getColor() == kingColor) {
                            return new int[]{i, j};
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Piece piece = getPiece(new int[]{i, j});
                    if(piece != null) {
                        if(piece instanceof King && piece.getColor() == kingColor) {
                            return new int[]{i, j};
                        }
                    }
                }
            }
        }
        return new int[]{0,0};
    }

    public void applyPieceMove(Move move) {

        Piece posWhere = getPiece(move.getTo());
        Piece currentPiece = getPiece(move.getFrom());

        // Checks for pawn promotion
        if ((currentPiece instanceof BlackPawn && move.getTo()[0] == 7) ||
                (currentPiece instanceof WhitePawn) && move.getTo()[0] == 0) {
            this.promotion = true; 
        }
        
        if (promotion) {

            if (currentPiece.getColor() == Flags.Colour.WHITE) {
                for (int i = 0; i < whites.size(); i++) {
                    if (currentPiece.equals(whites.get(i))) {
                        whites.remove(i);
                        currentPiece = new Queen(Colour.WHITE, move.getFrom());
                        whites.add(currentPiece);
                        this.promotion = false;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < blacks.size(); i++) {
                    if (currentPiece.equals(blacks.get(i))) {
                        blacks.remove(i);
                        currentPiece = new Queen(Colour.BLACK, move.getFrom());
                        blacks.add(currentPiece);
                        this.promotion = false;
                        break;
                    }
                }
            }

        }
        
        if (posWhere != null) {
            
            if (posWhere.getColor() == Flags.Colour.WHITE) {
                for (int i = 0; i < whites.size(); i++) {
                    if (posWhere.equals(whites.get(i))) {
                        whites.remove(i);
                        break;

                    }
                }
            } else {
                for (int i = 0; i < blacks.size(); i++) {
                    if (posWhere.equals(blacks.get(i))) {
                        blacks.remove(i);
                        break;
                    }
                }
            }
        }

        setPiece(move.getTo(), currentPiece);
        setPiece(move.getFrom(), null);

    }

    /**
     * Creates a list with all valid moves for all pieces
     * 
     * @return allMoves A list with all valid moves for all pieces
     */
    public ArrayList<Move> getAllMoves() {
        ArrayList<Move> array = new ArrayList<Move>();
        int[] kingPosition = new int[2];

        kingPosition = getKingPosition(engineColour);

        Piece piece, auxPiece;
        ArrayList<Integer> allValidMoves;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piece = getPiece(new int [] {i, j});
                if (piece != null) {
                    if (piece.getColor() == engineColour) {
                        allValidMoves = getValidMoves(piece, i, j);

                        if(allValidMoves != null){
                            for (int k = 0; k < allValidMoves.size(); k++) {
                                Move move = new Move();
                                move.setFrom(new int [] {i, j});
                                int row = allValidMoves.get(k) / 8;
                                int column = allValidMoves.get(k) % 8;
                                move.setTo(new int [] {row, column});
                                auxPiece = getPiece(move.getTo());
                                setPiece(move.getTo(), piece);
                                setPiece(move.getFrom(), null);
                                if(piece instanceof King){
                                    if(!isPositionAttacked(piece.getPosition())){
                                        array.add(move);
                                    }
                                } else if (!isPositionAttacked(kingPosition)) {
                                    array.add(move);
                                }

                                setPiece(move.getFrom(), piece);
                                setPiece(move.getTo(), auxPiece);
                            }
                        }
                    }
                }
            }
        }
        return array;
    }

    /**
     * Returns a list of Integers with all pseudo-valid positions where this
     * piece can be moved. The position in the matrix can be obtained
     * like this: row = number / 8 column = number % 8
     * 
     * @param pieceToMove The piece to be moved
     * @return validMoves ArrayList with all the valid moves 
     */
    public ArrayList<Integer> getValidMoves(Piece pieceToMove, int row, int column) {

        int nextRow, nextColumn;

        ArrayList<Integer> array = new ArrayList<Integer>();

        if (pieceToMove instanceof BlackPawn
                || pieceToMove instanceof WhitePawn) {
            for (int i = 1; i < 3; i++) {
                nextRow =   (row + pieceToMove.getY()[i]);
                nextColumn =   (column + pieceToMove.getX()[i]);

                if (Piece.isValid(nextRow, nextColumn)) {

                    Piece posWhere = getPiece(new int[] { nextRow,
                            nextColumn });
                    if (posWhere != null) {
                        if (posWhere.getColor() != pieceToMove.getColor()) {
                            array.add(nextRow * 8 + nextColumn);
                        }
                    }
                }
            }

            for(int i = 1; i < 3; i++) {
            	
				nextRow = (row + pieceToMove.getY()[0] * i);
				nextColumn = (column + pieceToMove.getX()[0] * i);
				
				if (pieceToMove.getColor() == Colour.WHITE) {
					if (i == 2 && pieceToMove.getPosition()[0] != 6) {
						break;
					}
				} else {
					if (i == 2 && pieceToMove.getPosition()[0] != 1) {
						break;
					}
				}

				Piece posWhere = getPiece(new int[] { nextRow, nextColumn });

				if (Piece.isValid(nextRow, nextColumn) && posWhere == null) {
					array.add(nextRow * 8 + nextColumn);
				} else {
					break;
				}
            }

        } else {

            for (int i = 0; i < pieceToMove.getX().length; i++) {
                if (pieceToMove instanceof Rook
                        || pieceToMove instanceof Bishop
                        || pieceToMove instanceof Queen) {
                    for (int j = 1; j < 8; j++) {
                        nextRow = (row + pieceToMove.getY()[i] * j);
                        nextColumn = (column + pieceToMove.getX()[i] * j);
                        if (Piece.isValid(nextRow, nextColumn)) {

                            Piece posWhere = getPiece(new int[] { nextRow, nextColumn });
                            if (posWhere != null) {
                                if (posWhere.getColor() == pieceToMove.getColor()) {
                                    break;
                                } else {
                                    array.add(nextRow * 8 + nextColumn);
                                    break;
                                }
                            } else {
                                array.add(nextRow * 8 + nextColumn);
                            }
                        } else {
                            break;
                        }
                    }
                } else if(pieceToMove instanceof King){
                    nextRow = (row + pieceToMove.getY()[i]);
                    nextColumn = (column + pieceToMove.getX()[i]);

                    if (Piece.isValid(nextRow, nextColumn)) {
                        Piece posWhere = getPiece(new int[] { nextRow, nextColumn });
                        setPiece(new int[]{row, column}, null);

                        if(!isPositionAttacked(new int[]{nextRow, nextColumn})) {
                            if((posWhere != null) && (posWhere.getColor() != pieceToMove.getColor())) {
                                array.add(nextRow * 8 + nextColumn);
                                System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
                            }
                        }

                        setPiece(new int[]{row, column}, pieceToMove);
                    }
                } else {
                    nextRow = (row + pieceToMove.getY()[i]);
                    nextColumn = (column + pieceToMove.getX()[i]);

                    if (Piece.isValid(nextRow, nextColumn)) {
                        Piece posWhere = getPiece(new int[] { nextRow, nextColumn });
                        if (posWhere != null) {
                            if (posWhere.getColor() == pieceToMove.getColor()) {
                                break;
                            } else {
                                array.add(nextRow * 8 + nextColumn);
                                break;
                            }
                        } else {
                            array.add(nextRow * 8 + nextColumn);
                        }
                    }
                }
            }
        }

        if (array.size() > 0) {
            return array;
        }

        return null;

    }

    public ArrayList<Piece> getWhites() {
        return whites;
    }

    public ArrayList<Piece> getBlacks() {
        return blacks;
    }

    public String printBoard() {
        String q = new String();
        Piece p;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                p = getPiece(new int[] {i, j});
                if(p != null) {
                    q = q.concat(p + " ");
                } else {
                    q = q.concat(". ");
                }
            }
            q = q.concat("\n");

        }
        return q;
    }
    
    /**
     * Checks if the given position is attacked by going to each of the opponent's
     * piece and tests if the given position is on its way.
     * 
     * @param pos The position to be checked
     * @return boolean Wheter the position is attacked or not
     */
    public boolean isPositionAttacked(int[] pos) {
        Piece p;
        int l;
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                p = getPiece(new int[]{i, j});
                if(p != null && p.getColor() != engineColour) {
                    if(p instanceof Rook || p instanceof Queen) {
                        // check upwards
                        int k = i + 1;
                        while(Piece.isValid(k, j)) {
                            if(pos[0] == k && pos[1] == j) {
                                return true;
                            }

                            if(getPiece(new int[]{k, j}) != null) {
                                break;
                            }

                            k++;
                        }

                        // check downwards
                        k = i - 1;
                        while(Piece.isValid(k, j)) {
                            if(pos[0] == k && pos[1] == j) {
                                return true;
                            }

                            if(getPiece(new int[]{k, j}) != null) {
                                break;
                            }

                            k--;
                        }

                        // check to the right
                        k = j + 1;
                        while(Piece.isValid(i, k)) {
                            if(pos[0] == i && pos[1] == k) {
                                return true;
                            }

                            if(getPiece(new int[]{i, k}) != null) {
                                break;
                            }

                            k++;
                        }

                        // check to the left
                        k = j - 1;
                        while(Piece.isValid(i, k)) {
                            if(pos[0] == i && pos[1] == k) {
                                return true;
                            }

                            if(getPiece(new int[]{i, k}) != null) {
                                break;
                            }

                            k--;
                        }
                        if(p instanceof Queen) {
                            k = i + 1; l = j - 1;
                            while(Piece.isValid(k, l)) {
                                if(pos[0] == k && pos[1] == l) {
                                    return true;
                                }

                                if(getPiece(new int[]{k, l}) != null) {
                                    break;
                                }

                                k++; l--;
                            } 

                            // check SE
                            k = i + 1; l = j + 1;
                            while(Piece.isValid(k, l)) {
                                if(pos[0] == k && pos[1] == l) {
                                    return true;
                                }

                                if(getPiece(new int[]{k, l}) != null) {
                                    break;
                                }

                                k++; l++;
                            }

                            // check SW
                            k = i - 1; l = j + 1;
                            while(Piece.isValid(k, l)) {
                                if(pos[0] == k && pos[1] == l) {
                                    return true;
                                }

                                if(getPiece(new int[]{k, l}) != null) {
                                    break;
                                }

                                k--; l++;
                            }

                            // check NW
                            k = i - 1; l = j - 1;
                            while(Piece.isValid(k, l)) {
                                if(pos[0] == k && pos[1] == l) {
                                    return true;
                                }

                                if(getPiece(new int[]{k, l}) != null) {
                                    break;
                                }

                                k--; l--;
                            }
                        }
                    } else if(p instanceof Bishop) {
                        int k;

                        // check NE
                        k = i + 1; l = j - 1;
                        while(Piece.isValid(k, l)) {
                            if(pos[0] == k && pos[1] == l) {
                                return true;
                            }

                            if(getPiece(new int[]{k, l}) != null) {
                                break;
                            }

                            k++; l--;
                        } 

                        // check SE
                        k = i + 1; l = j + 1;
                        while(Piece.isValid(k, l)) {
                            if(pos[0] == k && pos[1] == l) {
                                return true;
                            }

                            if(getPiece(new int[]{k, l}) != null) {
                                break;
                            }

                            k++; l++;
                        }

                        // check SW
                        k = i - 1; l = j + 1;
                        while(Piece.isValid(k, l)) {
                            if(pos[0] == k && pos[1] == l) {
                                return true;
                            }

                            if(getPiece(new int[]{k, l}) != null) {
                                break;
                            }

                            k--; l++;
                        }

                        // check NW
                        k = i - 1; l = j - 1;
                        while(Piece.isValid(k, l)) {
                            if(pos[0] == k && pos[1] == l) {
                                return true;
                            }

                            if(getPiece(new int[]{k, l}) != null) {
                                break;
                            }

                            k--; l--;
                        }
                    } else if(p instanceof Knight || p instanceof BlackPawn || p instanceof WhitePawn || p instanceof King) {
                        int[] ky = p.getX();
                        int[] kx = p.getY();

                        for(int k = 0; k < p.getX().length; k++) {
                            if((i + kx[k] == pos[0]) && (j + ky[k] == pos[1])) {
                                if( (p instanceof WhitePawn && (kx[k] + ky[k] != -1)) ||
                                        (p instanceof BlackPawn && (kx[k] + ky[k] != 1)) ) {
                                    return true;
                                } else if (p instanceof Knight || p instanceof King) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    /**
     * Checks if the king has any possible move to get its self out of check
     * and the first it finds, translates it to a move and applies it.
     * 
     * @param chessEngineColour The chess engine's colour
     * @return move The move for the king
     */
    public String getKingOutOfCheck(Colour chessEngineColour) {
        Move m = new Move();
        String move = null;
        int x, y, nextRow, nextColumn;
        Piece king = (chessEngineColour == Colour.WHITE) ? Flags.WHITE_KING : Flags.BLACK_KING; 
        x = king.getPosition()[0];
        y = king.getPosition()[1];
        int[] kx, ky;
        ky = king.getX();
        kx = king.getY();

        for(int i = 0; i < 8; i++) {
            nextRow = x + kx[i];
            nextColumn = y + ky[i];

            if(Piece.isValid(nextRow, nextColumn)) {
                Piece posWhere = getPiece(new int[] { nextRow, nextColumn });
                setPiece(new int[]{x, y}, null);
                if(!isPositionAttacked(new int[]{nextRow, nextColumn})) {
                    if((posWhere != null) && (posWhere.getColor() != chessEngineColour)) {
                        m.setFrom(new int[]{x, y});
                        m.setTo(new int[]{nextRow, nextColumn});
                        move = m.toString();
                        setPiece(new int[]{x, y}, king);
                        break;
                    } else if(posWhere == null){
                        m.setFrom(new int[]{x, y});
                        m.setTo(new int[]{nextRow, nextColumn});
                        move = m.toString();
                        setPiece(new int[]{x, y}, king);
                        break;
                    }
                }

                setPiece(new int[]{x, y}, king);
            }
        }

        // if no move was found, returns an invalid move
        if(move == null) {
            m.setFrom(new int[]{x, y});
            m.setTo(new int[]{8, 8});
            move = m.toString();
        }

        return move;
    }
}
