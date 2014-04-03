package board;

import helpers.Flags;
import helpers.Flags.Colour;
import java.util.ArrayList;
import connection.ChessBoardConnect;
import piece.*;

/**
 * Class that has the chess board representation.
 * It is initialized with all pieces on it.
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
        
        setPiece(new byte[]{0, 0}, new Rook(Colour.BLACK,   new byte[]{0, 0}));
        setPiece(new byte[]{0, 1}, new Knight(Colour.BLACK, new byte[]{0, 1}));
        setPiece(new byte[]{0, 2}, new Bishop(Colour.BLACK, new byte[]{0, 2}));
        setPiece(new byte[]{0, 3}, new Queen(Colour.BLACK,  new byte[]{0, 3}));
        setPiece(new byte[]{0, 4}, new King(Colour.BLACK,   new byte[]{0, 4}));
        setPiece(new byte[]{0, 5}, new Bishop(Colour.BLACK, new byte[]{0, 5}));
        setPiece(new byte[]{0, 6}, new Knight(Colour.BLACK, new byte[]{0, 6}));
        setPiece(new byte[]{0, 7}, new Rook(Colour.BLACK,   new byte[]{0, 7}));
        
        for (byte i = 0; i < 8; i++) {
        	setPiece(new byte[]{1, i}, new BlackPawn(Colour.BLACK, new byte[]{1, i}));
        	setPiece(new byte[]{6, i}, new WhitePawn(Colour.WHITE, new byte[]{6, i}));

        	for (byte j = 2; j <= 5; j++) {
                setPiece(new byte[]{j, i}, null);
            }
        }
        
        setPiece(new byte[]{7, 0}, new Rook(Colour.WHITE,   new byte[]{0, 0}));
        setPiece(new byte[]{7, 1}, new Knight(Colour.WHITE, new byte[]{0, 1}));
        setPiece(new byte[]{7, 2}, new Bishop(Colour.WHITE, new byte[]{0, 2}));
        setPiece(new byte[]{7, 3}, new Queen(Colour.WHITE,  new byte[]{0, 3}));
        setPiece(new byte[]{7, 4}, new King(Colour.WHITE,   new byte[]{0, 4}));
        setPiece(new byte[]{7, 5}, new Bishop(Colour.WHITE, new byte[]{0, 5}));
        setPiece(new byte[]{7, 6}, new Knight(Colour.WHITE, new byte[]{0, 6}));
        setPiece(new byte[]{7, 7}, new Rook(Colour.WHITE,   new byte[]{0, 7}));
        
        for(int i = 0; i < 8; i++) {
        	whites.add(field[7][i]);
        	blacks.add(field[0][i]);
        }
        
        for(int i = 0; i < 8; i++) {
        	whites.add(field[6][i]);
		    blacks.add(field[1][i]);
        }
    }
    
   	/**
     * Creates an instance of the class (if there wasn't one created
     * else it uses the same) and returns it.
     * 
     * @return instance of the class
     */
    public static Board getInstance() {
        if(instance == null) {
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
     * @return new instance of the class
     */
    public static Board getNewInstance() {
        Board.initialize();
        
        return getInstance();
    }
    
    /**
     * Put <i>piece</i> at position <i>pos</i>.
     * 
     * @param position wanted
     * @param piece that is at position <i>pos</i>
     */
    public void setPiece(byte[] pos, Piece piece) {
        byte i = pos[0];
        byte j = pos[1];
        field[i][j] = piece;
    }
    
    /**
     * Get <i>piece</i> from position <i>pos</i>.
     * 
     * @param position on the board
     * @return the piece that is at position pos
     */
    public Piece getPiece(byte [] pos) {
        byte i = pos[0];
        byte j = pos[1];
        return field[i][j];
    }
    
    /**
     * Moves a piece on the chess board if possible
     * 
     * @param the move to execute
     * @return true if the move is executed, false otherwise
     */
    public boolean movePiece(Move move) {
        // aici o sa verificam daca mutarea primita e valida
        
        applyPieceMove(move);
        return true;
    }
    
    /**
     * Moves one of our pieces
     * 
     * @param   the move we want to apply
     * @return  true if the move is executed, false otherwise
     */
    public boolean moveMyPiece(Move move) {
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        
        if (chessBoardConnect.getChessEngineColour() == Flags.Colour.BLACK) {
            // verifica daca piesa pe care o mut e pion si daca are coloarea corespunzatoare si daca unde vreau sa mut e vreo piesa
            if (!(getPiece(move.getFrom()) instanceof BlackPawn) || getPiece(move.getTo()) != null) {
                return false;
            }
        } else {
            
            if (!(getPiece(move.getFrom()) instanceof WhitePawn) || getPiece(move.getTo()) != null) {
                return false;
            }
        }
        
        
        applyPieceMove(move);
        return true;
    }
    
    
    /**
     * Applies a move on the chess board without verifying if it is valid  
     * 
     * @param the move to be executes
     */
    public void applyPieceMove(Move move) {
        setPiece(move.getTo(), getPiece(move.getFrom()));
        setPiece(move.getFrom(), null);
    }

	/**
	 * @param pieceToMove
	 * @return position where piece can be moved
	 */
	public static byte[] getOneValidMove(Piece pieceToMove) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Piece getWhitePiece() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Piece getBlackPiece() {
		// TODO Auto-generated method stub
		return null;
	}
}
