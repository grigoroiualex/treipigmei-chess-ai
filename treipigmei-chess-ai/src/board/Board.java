package board;

import helpers.Flags;
import helpers.Flags.Colour;

import java.util.ArrayList;

import connection.ChessBoardConnect;
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
    Move lastMove;

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
        
        lastMove = null;
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
     * @param pos The position wanted 
     * @param piece The piece that is at position <i>pos</i>
     */
    public void setPiece(int[] pos, Piece piece) {
        int i = pos[0];
        int j = pos[1];
        field[i][j] = piece;
    }

    /**
     * Get <i>piece</i> from position <i>pos</i>.
     * 
     * @param pos The position on the board
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
        
        checkForRepetition(move);
        if(resignByRepetition() || resignByExtraMoves()) {
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
     * Creates o clone of the current board
     * 
     * @return clone
     */
    public Clone newClone() {
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        return new Clone(field, whites, blacks, Flags.PROMOTION, chessBoardConnect.getChessEngineColour());
    }
    
    /**
     * Checks for move repetition that might end the game 
     * 
     * @return boolean True if repetition threshold has been 
     */
    public boolean resignByRepetition() {
        return Flags.REPETITION > Flags.REPETITION_LIMIT ? true : false;
    }
    
    public boolean resignByExtraMoves() {
        return Flags.MOVES > Flags.MOVES_LIMIT ? true : false;
    }
    
    public void checkForRepetition(Move move) {
        Flags.MOVES++;
        
        if(lastMove == null) {
            lastMove = move;
            return;
        }
        
        if(lastMove.getTo()[0] == move.getFrom()[0] && lastMove.getTo()[1] == move.getFrom()[1]
                && lastMove.getFrom()[0] == move.getTo()[0] && lastMove.getFrom()[1] == move.getTo()[1]) {
            Flags.REPETITION++;
        } else {
            Flags.REPETITION = 0;
        }
        
        lastMove = move;
    }
}
