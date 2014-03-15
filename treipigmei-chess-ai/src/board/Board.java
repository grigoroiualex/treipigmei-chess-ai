package board;

import piece.*;
import chessboard.ChessBoardConnect;

public class Board {
    private static Board instance = null;
    private Piece[][] field;
    
    private Board() {
        field = new Piece[8][8];
        
        setPiece(new byte[]{0, 0}, new Rook("BLACK"));
        setPiece(new byte[]{0, 1}, new Knight("BLACK"));
        setPiece(new byte[]{0, 2}, new Bishop("BLACK"));
        setPiece(new byte[]{0, 3}, new Queen("BLACK"));
        setPiece(new byte[]{0, 4}, new King("BLACK"));
        setPiece(new byte[]{0, 5}, new Bishop("BLACK"));
        setPiece(new byte[]{0, 6}, new Knight("BLACK"));
        setPiece(new byte[]{0, 7}, new Rook("BLACK"));
        
        for (byte i = 0; i < 8; i++) {
            setPiece(new byte[]{1, i}, new BlackPawn());
            for (byte j = 2; j <= 5; j++) {
                setPiece(new byte[]{j, i}, null);
            }
            setPiece(new byte[]{6, i}, new WhitePawn());
        }
        
        setPiece(new byte[]{7, 0}, new Rook("WHITE"));
        setPiece(new byte[]{7, 1}, new Knight("WHITE"));
        setPiece(new byte[]{7, 2}, new Bishop("WHITE"));
        setPiece(new byte[]{7, 3}, new Queen("WHITE"));
        setPiece(new byte[]{7, 4}, new King("WHITE"));
        setPiece(new byte[]{7, 5}, new Bishop("WHITE"));
        setPiece(new byte[]{7, 6}, new Knight("WHITE"));
        setPiece(new byte[]{7, 7}, new Rook("WHITE"));
    }
    
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        
        return instance;
    }
    
    /**
     * Put "piece" at position "pos".
     * @param pos
     * @param piece
     */
    public void setPiece(byte [] pos, Piece piece) {
        byte i = pos[0];
        byte j = pos[1];
        field[i][j] = piece;
    }
    
    /**
     * Get piese from position "pos".
     * @param pos
     * @return
     */
    public Piece getPiece(byte [] pos) {
        byte i = pos[0];
        byte j = pos[1];
        return field[i][j];
    }
    
    public boolean movePiece(Move move) {
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        if (getPiece(move.getTo()) != null) {
            if (chessBoardConnect.getWhiteOnTurn()) {
                if (getPiece(move.getFrom()) instanceof WhitePawn) {
                    if (getPiece(move.getTo()) instanceof Piece) {
                        return false;
                    }
                } else {
                    if (getPiece(move.getTo()).getColor().compareTo("WHITE") == 0) {
                        return false;
                    }
                }
            } else {
                if (getPiece(move.getFrom()) instanceof BlackPawn) {
                    if (getPiece(move.getTo()) instanceof Piece) {
                        return false;
                    }
                } else {
                    if (getPiece(move.getTo()).getColor().compareTo("BLACK") == 0) {
                        return false;
                    }
                }
            }
        }
        
        applyPieceMove(move);
        return true;
    }
    
    public void applyPieceMove(Move move) {
        setPiece(move.getTo(), getPiece(move.getFrom()));
        setPiece(move.getFrom(), null);
    }

    /*
    public void print() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
    */
    
}
