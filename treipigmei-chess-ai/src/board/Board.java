package board;

import helpers.Flags;
import helpers.Flags.Colour;

import java.util.ArrayList;

import connection.ChessBoardConnect;
import brain.Brain;
import piece.*;

/**
 * Class that has the chess board representation. It is initialized with all
 * pieces on it.
 * 
 * @author grigoroiualex
 * 
 */
public class Board {
    private static Board instance = null;
    private Piece[][] field;
    private ArrayList<Piece> whites, blacks;


    private Board() {
        field = new Piece[8][8];
        whites = new ArrayList<>();
        blacks = new ArrayList<>();

        setPiece(new int[] { 0, 0 }, new Rook(Colour.BLACK,   new int[] { 0, 0 }));
        setPiece(new int[] { 0, 1 }, new Knight(Colour.BLACK, new int[] { 0, 1 }));
        setPiece(new int[] { 0, 2 }, new Bishop(Colour.BLACK, new int[] { 0, 2 }));
        setPiece(new int[] { 0, 3 }, new Queen(Colour.BLACK,  new int[] { 0, 3 }));
        setPiece(new int[] { 0, 4 }, new King(Colour.BLACK,   new int[] { 0, 4 }));
        setPiece(new int[] { 0, 5 }, new Bishop(Colour.BLACK, new int[] { 0, 5 }));
        setPiece(new int[] { 0, 6 }, new Knight(Colour.BLACK, new int[] { 0, 6 }));
        setPiece(new int[] { 0, 7 }, new Rook(Colour.BLACK,   new int[] { 0, 7 }));

        for (int i = 0; i < 8; i++) {
            setPiece(new int[] { 1, i }, new BlackPawn(Colour.BLACK, new int[] { 1, i }));
            setPiece(new int[] { 6, i }, new WhitePawn(Colour.WHITE, new int[] { 6, i }));

            for (int j = 2; j <= 5; j++) {
                setPiece(new int[] { j, i }, null);
            }
        }

        setPiece(new int[] { 7, 0 }, new Rook(Colour.WHITE,   new int[] { 7, 0 }));
        setPiece(new int[] { 7, 1 }, new Knight(Colour.WHITE, new int[] { 7, 1 }));
        setPiece(new int[] { 7, 2 }, new Bishop(Colour.WHITE, new int[] { 7, 2 }));
        setPiece(new int[] { 7, 3 }, new Queen(Colour.WHITE,  new int[] { 7, 3 }));
        setPiece(new int[] { 7, 4 }, new King(Colour.WHITE,   new int[] { 7, 4 }));
        setPiece(new int[] { 7, 5 }, new Bishop(Colour.WHITE, new int[] { 7, 5 }));
        setPiece(new int[] { 7, 6 }, new Knight(Colour.WHITE, new int[] { 7, 6 }));
        setPiece(new int[] { 7, 7 }, new Rook(Colour.WHITE,   new int[] { 7, 7 }));

        for (int i = 0; i < 8; i++) {
            whites.add(field[7][i]);
            blacks.add(field[0][i]);
        }

        for (int i = 0; i < 8; i++) {
            whites.add(field[6][i]);
            blacks.add(field[1][i]);
        }

        Flags.BLACK_KING = (King) getPiece(new int[] { 0, 4 });
        Flags.WHITE_KING = (King) getPiece(new int[] { 7, 4 });
        
    }

    /**
     * Creates an instance of the class (if there wasn't one created else it
     * uses the same) and returns it.
     * 
     * @return instance The current instance of the class
     */
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }

        return instance;
    }

    /**
     * Destroys current instance
     */
    public static void initialize() {
        instance = null;
    }

    /**
     * Creates a new instance of the class and returns it.
     * 
     * @return instance  New instance of the class
     */
    public static Board getNewInstance() {
        Board.initialize();
        return getInstance();
    }

    /**
     * Put <i>piece</i> at position <i>pos</i>.
     * 
     * @param position
     *            wanted
     * @param piece
     *            that is at position <i>pos</i>
     */
    public void setPiece(int[] pos, Piece piece) {
        int i = pos[0];
        int j = pos[1];
        field[i][j] = piece;
    }

    /**
     * Get <i>piece</i> from position <i>pos</i>.
     * 
     * @param position
     *            on the board
     * @return piece The piece that is at position pos
     */
    public Piece getPiece(int[] pos) {
        int i = pos[0];
        int j = pos[1];
        return field[i][j];
    }

    /**
     * Moves a piece on the chess board if possible
     * 
     * @param move The move to execute
     * @return true if the move is executed, false otherwise
     */
    public boolean movePiece(Move move) {
        // if the piece is moved two positions then it's castling
        Piece currentPiece = getPiece(move.getFrom());
        if (currentPiece instanceof King
                && Math.abs(move.getFrom()[1] - move.getTo()[1]) > 1) {
            Move rookMove;
            // if it's the white king
            if (currentPiece.getColor() == Flags.Colour.WHITE) {
                // if the castling is to the left or right
                if(move.getFrom()[1] > move.getTo()[1]) {
                    rookMove = new Move("a1d1");
                } else {
                    rookMove = new Move("h1f1");
                }

            } else {

                // if the castling is to the left or right
                if(move.getFrom()[1] > move.getTo()[1]) {
                    rookMove = new Move("a8d8 	");
                } else {
                    rookMove = new Move("h8f8");
                }
            }

            applyPieceMove(rookMove);
        }

        applyPieceMove(move);
        return true;
    }

    /**
     * Moves one of our pieces
     * 
     * @param move The move we want to apply
     * @return true if the move is executed, false otherwise
     */
    public boolean moveMyPiece(Move move) {

        int x = move.getTo()[0];
        int y = move.getTo()[1];

        // if the move is out of the table. This case is when the king has no valid moves
        if(!Piece.isValid(x, y)) {
            return false;
        }
        applyPieceMove(move);
        return true;
    }

    /**
     * Applies a move on the chess board without verifying if it is valid
     * 
     * @param move The move to be executed
     */
    public void applyPieceMove(Move move) {

        Board board = Board.getInstance();
        Piece posWhere = getPiece(move.getTo());
        Piece currentPiece = board.getPiece(move.getFrom());

        if ((currentPiece instanceof BlackPawn && move.getTo()[0] == 7) ||
                (currentPiece instanceof WhitePawn) && move.getTo()[0] == 0) {
            Flags.PROMOTION = true;
        }
        // if a king is moved, its position is savrd
        if (currentPiece instanceof King) {
            if (currentPiece.getColor() == Flags.Colour.WHITE) {
                Flags.WHITE_KING.setPosition(move.getTo());
            } else {
                Flags.BLACK_KING.setPosition(move.getTo());
            }
        }

        // in case of pawn promotion, exchange it for a queen 
        if(Flags.PROMOTION) {
            if (currentPiece.getColor() == Flags.Colour.WHITE) {
                for (int i = 0; i < whites.size(); i++) {
                    if (currentPiece.equals(whites.get(i))) {
                        whites.remove(i);
                        currentPiece = new Queen(Colour.WHITE, move.getFrom());
                        whites.add(currentPiece);
                        Flags.PROMOTION = false;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < blacks.size(); i++) {
                    if (currentPiece.equals(blacks.get(i))) {
                        blacks.remove(i);
                        currentPiece = new Queen(Colour.BLACK, move.getFrom());
                        blacks.add(currentPiece);
                        Flags.PROMOTION = false;
                        break;
                    }
                }
            }

        }

        // if any piece is taken
        if (posWhere != null) {

            // if the piece is white, search for it in the whites array
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

        currentPiece.setPosition(move.getTo());

        setPiece(move.getTo(), currentPiece);
        setPiece(move.getFrom(), null);

    }

    /**
     * Returns a list of Integers with all pseudo-valid positions where this
     * piece can be moved. The position in the matrix can be obtained
     * like this: row = number / 8 column = number % 8
     * 
     * @param pieceToMove The piece to be moved
     * @return validMoves ArrayList with all the valid moves 
     */
    public ArrayList<Integer> getValidMoves(Piece pieceToMove) {

        int row, column, nextRow, nextColumn;

        row = pieceToMove.getPosition()[0];
        column = pieceToMove.getPosition()[1];

        ArrayList<Integer> array = new ArrayList<Integer>();
        Board board = Board.getInstance();

        if (pieceToMove instanceof BlackPawn
                || pieceToMove instanceof WhitePawn) {
            // if it can attack
            for (int i = 1; i < 3; i++) {
                nextRow =   (row + pieceToMove.getY()[i]);
                nextColumn =   (column + pieceToMove.getX()[i]);

                // if it's within bounds
                if (Piece.isValid(nextRow, nextColumn)) {

                    Piece posWhere = board.getPiece(new int[] { nextRow,
                            nextColumn });
                    // if there is any piece
                    if (posWhere != null) {
                        // if the piece is of the same colour stops
                        // otherwise, it adds the move as valid and stops
                        if (posWhere.getColor() != pieceToMove.getColor()) {
                            array.add(nextRow * 8 + nextColumn);
                        }
                    }
                }
            }

            // else it tests if the pawn can advance
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

				Piece posWhere = board
						.getPiece(new int[] { nextRow, nextColumn });

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

                        // if it is within bounds
                        if (Piece.isValid(nextRow, nextColumn)) {

                            Piece posWhere = board.getPiece(new int[] {
                                    nextRow, nextColumn });
                            // if there is any piece
                            if (posWhere != null) {
                                // if the piece is of the same colour stops
                                // otherwise, it adds the move as valid and stops
                                if (posWhere.getColor() == pieceToMove
                                        .getColor()) {
                                    break;
                                } else {
                                    array.add(nextRow * 8 + nextColumn);
                                    break;
                                }

                                // if there is no piece adds the move as valid
                            } else {
                                array.add(nextRow * 8 + nextColumn);
                            }
                        } else {
                            break;
                        }
                    }
                } else {

                    nextRow = (row + pieceToMove.getY()[i]);
                    nextColumn = (column + pieceToMove.getX()[i]);

                    if (Piece.isValid(nextRow, nextColumn)) {
                        Piece posWhere = board.getPiece(new int[] {
                                nextRow, nextColumn });
                        // if there is any piece
                        if (posWhere != null) {
                            // if the piece is of the same colour stops
                            // otherwise, it adds the move as valid and stops
                            if (posWhere.getColor() == pieceToMove
                                    .getColor()) {
                                break;
                            } else {
                                array.add(nextRow * 8 + nextColumn);
                                break;
                            }

                            // if there is no piece adds the move as valid
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

    /**
     * Returns an instance of a random white piece
     * 
     * @return piece One instance of a random white piece
     */
    public Piece getWhitePiece() {

        int x = (int) ((Math.random() * 100) % whites.size());
        return whites.get(x);
    }

    /**
     * Returns an instance of a random black piece
     * 
     * @return piece One instance of a random black piece
     */
    public Piece getBlackPiece() {

        int x = (int) ((Math.random() * 100) % blacks.size());
        return blacks.get(x);
    }

    /**
     * Creates a string for outputing the board's content
     * 
     * @return board The contents of the board
     */
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
     * Creates a list with all valid moves for all pieces
     * 
     * @return allMoves An array with all possible moves
     */
    public ArrayList<Move> getAllMoves() {
        ArrayList<Move> array = new ArrayList<Move>();
        King currentKing;

        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Flags.Colour chessEngineColour = chessBoardConnect.getChessEngineColour();
        if (chessEngineColour == Flags.Colour.WHITE) {
            currentKing = Flags.WHITE_KING;
        } else {
            currentKing = Flags.BLACK_KING;
        }

        Piece piece, auxPiece;
        ArrayList<Integer> allValidMoves;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // checks if there is any piece at position [i, j]
                piece = getPiece(new int [] {i, j});
                if (piece != null) {
                    if (piece.getColor() == chessEngineColour) {
                        // if it's own piece checks all valid moves
                        allValidMoves = getValidMoves(piece);

                        // from all pseudo-valid moves it only keeps the valid ones
                        // (those that don't let the king in check)
                        for (int k = 0; k < allValidMoves.size(); k++) {
                            Move move = new Move();
                            move.setFrom(new int [] {i, j});
                            int row = allValidMoves.get(k) / 8;
                            int column = allValidMoves.get(k) % 8;
                            move.setTo(new int [] {row, column});
                            auxPiece = getPiece(move.getTo());
                            setPiece(move.getTo(), piece);
                            setPiece(move.getFrom(), auxPiece);

                            if (!Brain.isPositionAttacked(currentKing.getPosition())) {
                                array.add(move);
                            }

                            setPiece(move.getFrom(), piece);
                            setPiece(move.getTo(), auxPiece);
                        }
                    }
                }
            }
        }

        return array;
    }

    /**
     * Creates o clone of the current board
     * 
     * @return clone
     */
    public Clone newClone() {
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        return new Clone(field, whites, blacks, Flags.PROMOTION, chessBoardConnect.getChessEngineColour());
    }
}
