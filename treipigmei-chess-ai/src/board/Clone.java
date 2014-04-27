package board;

import java.util.ArrayList;

import connection.ChessBoardConnect;
import helpers.Flags;
import helpers.Flags.Colour;
import piece.Bishop;
import piece.BlackPawn;
import piece.King;
import piece.Knight;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import piece.WhitePawn;

/**
 * Class that clone an instance of Board class.
 * 
 * @author Florin
 *
 */
public class Clone {
    private ArrayList<Piece> whites, blacks;
    private Piece[][] field;

    public Clone() {

    }

    public Clone(Piece [][] f, ArrayList<Piece> w, ArrayList<Piece> b) {

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
        
        //System.out.println("Clona:\n" + printBoard());
    }

    /**
     * @return a copy of current clone
     */
    public Clone newClone() {
        return new Clone(field, whites, blacks);
    }

    /**
     * @param move
     * @return current instance with "move" applied
     */
    public Clone getCloneWithMove(Move move) {
        applyPieceMove(move);

        return this;
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
     * @return the piece that is at position pos
     */
    public Piece getPiece(int[] pos) {
        int i = pos[0];
        int j = pos[1];
        return field[i][j];
    }

    public void applyPieceMove(Move move) {

        Piece posWhere = getPiece(move.getTo());
        Piece currentPiece = getPiece(move.getFrom());

        // daca mut un rege, salvez pozitia unde il mut
        if (currentPiece instanceof King) {
            if (currentPiece.getColor() == Flags.Colour.WHITE) {
                Flags.WHITE_KING.setPosition(move.getTo());
            } else {
                Flags.BLACK_KING.setPosition(move.getTo());
            }
        }

        // daca se face promovarea pionului il elimin din lista de piese si pun 
        // o regina in locul lui, ca mai apoi sa se execute mutarea
        if(Flags.PROMOTION) {
            if (currentPiece.getColor() == Flags.Colour.WHITE) {
                for (int i = 0; i < whites.size(); i++) {
                    if (currentPiece.equals(whites.get(i))) {
                        whites.remove(i);
                        currentPiece = new Queen(Colour.WHITE, move.getFrom());
                        whites.add(currentPiece);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < blacks.size(); i++) {
                    if (currentPiece.equals(blacks.get(i))) {
                        blacks.remove(i);
                        currentPiece = new Queen(Colour.BLACK, move.getFrom());
                        blacks.add(currentPiece);
                        break;
                    }
                }
            }

        }

        // daca este luata vreo piesa
        if (posWhere != null) {

            // daca piesa este alba o caut in tabloul pieselor albe s-o elimin
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
       
//        currentPiece.setPosition(move.getTo());

        setPiece(move.getTo(), getPiece(move.getFrom()));
        setPiece(move.getFrom(), null);

    }

    /**
     * 
     * @return a list with all valid moves for all pieces
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
        
        //System.out.println(printBoard());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // verific daca se afla vreo piesa pe pozitia [i, j]
                piece = getPiece(new int [] {i, j});
                if (piece != null) {
                    // daca da, verific ce culoare are
                    if (piece.getColor() == chessEngineColour) {
                        // daca e piesa mea, ii calculez toate pseudo-mutarile
                        allValidMoves = getValidMoves(piece, i, j);

                        if(allValidMoves != null){
                            /* din toate pseudo-mutarile, le pastrez doar pe cele
                             * care nu lasa regele in sah*/
                            //System.out.println("Piesa: " + piece.toString());
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
                                } else if (!isPositionAttacked(currentKing.getPosition())) {
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
     * @param pieceToMove
     * @return a list of Integers with all pseudo-valid positions where this
     *         piece can be moved. The position in the matrix can be obtained
     *         like this: row = number / 8 column = number % 8
     */

    //Linia din matrice este modificata de vectorul y din piese

    public ArrayList<Integer> getValidMoves(Piece pieceToMove, int row, int column) {

        int /*row, column,*/ nextRow, nextColumn;

//        row = pieceToMove.getPosition()[0];
//        column = pieceToMove.getPosition()[1];

        ArrayList<Integer> array = new ArrayList<Integer>();
        //Board board = Board.getInstance();

        // daca piesa este pion
        if (pieceToMove instanceof BlackPawn
                || pieceToMove instanceof WhitePawn) {
            // daca poate ataca
            for (int i = 1; i < 3; i++) {
                nextRow =   (row + pieceToMove.getY()[i]);
                nextColumn =   (column + pieceToMove.getX()[i]);

                // daca nu ies din matrice
                if (Piece.isValid(nextRow, nextColumn)) {

                    Piece posWhere = getPiece(new int[] { nextRow,
                            nextColumn });
                    // daca am piese pe pozitia unde vreau sa mut
                    if (posWhere != null) {
                        /*
                         * daca am piesa de aceeasi culoare ma opresc altfel
                         * adaug pozitia ca mutare valida si apoi ma opresc
                         */
                        if (posWhere.getColor() != pieceToMove.getColor()) {
                            //System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
                            
                            array.add(nextRow * 8 + nextColumn);
                        }
                    }
                }
            }

            // daca nu poate ataca pionul testez daca poate inainta
            nextRow = (row + pieceToMove.getY()[0]);
            nextColumn = (column + pieceToMove.getX()[0]);
            Piece posWhere = null;
            if(nextRow < 8 && nextColumn < 8){
                    posWhere = getPiece(new int[] { nextRow, nextColumn });
            }
           
            if (Piece.isValid(nextRow, nextColumn) && posWhere == null) {
               // System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
                array.add(nextRow * 8 + nextColumn);
            }

        } else {

            for (int i = 0; i < pieceToMove.getX().length; i++) {

                /*
                 * daca piesa este tura, nebun sau regina iau un for de la 1 la
                 * 7 si generez toate mutarile posibile
                 */
                if (pieceToMove instanceof Rook
                        || pieceToMove instanceof Bishop
                        || pieceToMove instanceof Queen) {
                    for (int j = 1; j < 8; j++) {
                        nextRow = (row + pieceToMove.getY()[i] * j);
                        nextColumn = (column + pieceToMove.getX()[i] * j);

                        // daca nu ies din matrice
                        if (Piece.isValid(nextRow, nextColumn)) {

                            Piece posWhere = getPiece(new int[] {
                                    nextRow, nextColumn });
                            // daca am piese pe pozitia unde vreau sa mut
                            if (posWhere != null) {
                                /*
                                 * daca am piesa de aceeasi culoare ma opresc
                                 * altfel adaug pozitia ca mutare valida si apoi
                                 * ma opresc
                                 */
                                if (posWhere.getColor() == pieceToMove
                                        .getColor()) {
                                    break;
                                } else {
                                    //System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
                                    array.add(nextRow * 8 + nextColumn);
                                    break;
                                }

                                // daca nu e piesa machez pozitia ca mutare
                                // valida
                            } else {
                                //System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
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
                        Piece posWhere = getPiece(new int[] {
                                nextRow, nextColumn });
                        // daca am piese pe pozitia unde vreau sa mut
                        if (posWhere != null) {
                            /*
                             * daca am piesa de aceeasi culoare ma opresc
                             * altfel adaug pozitia ca mutare valida si apoi
                             * ma opresc
                             */
                            if (posWhere.getColor() == pieceToMove
                                    .getColor()) {
                                break;
                            } else {
                                //System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row ) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
                                array.add(nextRow * 8 + nextColumn);
                                break;
                            }

                            // daca nu e piesa machez pozitia ca mutare valida
                        } else {
                            //System.out.println("Piesa: " + pieceToMove.toString() + " mutarea: " + (8 - row) +" " +(column + 1) + " " + (8 - nextRow) + " " + (nextColumn + 1));
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
    
    public boolean isPositionAttacked(int[] pos) {
        Clone board = this;
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Piece piece;
        int x = pos[0];
        int y = pos[1];
        int i, j;
        int[] kx = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] ky = {-1, 1, -2, 2, -2, 2, -1, 1};
        
        i = y - 1;
        // for every position to the left
        while(Piece.isValid(x, i)) {
            // checks for any piece
            if((piece = board.getPiece(new int[]{x, i})) != null) {
                // If it's own engine's colour it's ok
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                // otherwise
                } else {
                    // the piece is a sliding one
                    if(piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--;
        }
        
        i = y + 1;
        // for every position to the right
        while(Piece.isValid(x, i)) {
            if((piece = board.getPiece(new int[]{x, i})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++;
        }
        
        i = x - 1;
        // for every position upwards   
        while(Piece.isValid(i, y)) {
            if((piece = board.getPiece(new int[]{i, y})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--;
        }
        
        i = x + 1;
        // for every position downwards
        while(Piece.isValid(i, y)) {
            if((piece = board.getPiece(new int[]{i, y})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++;
        }
        
        i = x + 1; j = y + 1;
        // for every diagonal position NE
        while(Piece.isValid(i, j)) {
            if((piece = board.getPiece(new int[]{i, j})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++; j++;
        }
        
        i = x + 1; j = y - 1;
        // for every diagonal position SE
        while(Piece.isValid(i, j)) {
            if((piece = board.getPiece(new int[]{i, j})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++; j--;
        }
        
        i = x - 1; j = y - 1;
        // for every diagonal position SV
        while(Piece.isValid(i, j)) {
            if((piece = board.getPiece(new int[]{i, j})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--; j--;
        }
        
        i = x - 1; j = y + 1;
        // for every diagonal position NV
        while(Piece.isValid(i, j)) {
            if((piece = board.getPiece(new int[]{i, j})) != null) {
                if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                    break;
                } else {
                    if(piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--; j++;
        }
        
        // for every possible knight position
        for(int k = 0; k < 8; k++) {
            if(Piece.isValid(x + kx[k], y + ky[k])) {
                if((piece = board.getPiece(new int[]{x + kx[k], y + ky[k]})) != null) {
                    if(piece.getColor() == chessBoardConnect.getChessEngineColour()) {
                        break;
                    } else {
                        if(piece instanceof Knight) {
                            return true;
                        }
                    }
                }
            }
        }
        
        // for black pawns
        if(Piece.isValid(x - 1, y - 1)) {
            if((piece = board.getPiece(new int[]{x - 1, y - 1})) != null) {
                if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                        piece instanceof BlackPawn) {
                    return true;
                }
            }
        }
        
        // for black pawns
        if(Piece.isValid(x - 1, y + 1)) {
            if((piece = board.getPiece(new int[]{x - 1, y + 1})) != null) {
                if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                        piece instanceof BlackPawn) {
                    return true;
                }
            }
        }
        
        // for white pawns
        if(Piece.isValid(x + 1, y + 1)) {
            if((piece = board.getPiece(new int[]{x + 1, y + 1})) != null) {
                if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                        piece instanceof WhitePawn) {
                    return true;
                }
            }
        }
        
        // for white pawns
        if(Piece.isValid(x + 1, y - 1)) {
            if((piece = board.getPiece(new int[]{x + 1, y - 1})) != null) {
                if(piece.getColor() != chessBoardConnect.getChessEngineColour() &&
                        piece instanceof WhitePawn) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /* Testing */
    
    public static void main(String[] args) {
        Board b = Board.getInstance();
        Piece[][] p = b.getField();
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                p[i][j] = null;
            }
        }
        
        p[0][0] = new Rook(Colour.BLACK, new int[]{0, 0});
        p[0][2] = new Bishop(Colour.BLACK, new int[]{0, 2});
        p[0][3] = new Queen(Colour.BLACK, new int[]{0,3});
        p[0][4] = new King(Colour.BLACK, new int[]{0,4});
        p[0][7] = new Rook(Colour.BLACK, new int[]{0,7});
        p[1][1] = new BlackPawn(Colour.BLACK, new int[]{1,1});
        p[1][2] = new BlackPawn(Colour.BLACK, new int[]{1,2});
        p[1][5] = new BlackPawn(Colour.BLACK, new int[]{1,5});
        p[2][0] = new BlackPawn(Colour.BLACK, new int[]{2,0});
        p[2][5] = new BlackPawn(Colour.BLACK, new int[]{2,5});
        p[2][7] = new BlackPawn(Colour.BLACK, new int[]{2,7});
        p[3][1] = new WhitePawn(Colour.WHITE, new int[]{3,1});
        p[3][3] = new WhitePawn(Colour.WHITE, new int[]{3,3});
        p[4][1] = new Knight(Colour.BLACK, new int[]{4,3});
        p[4][5] = new BlackPawn(Colour.BLACK, new int[]{4,5});
        p[5][2] = new Knight(Colour.WHITE, new int[]{5,2});
        p[5][5] = new WhitePawn(Colour.WHITE, new int[]{5,5});
        p[6][0] = new WhitePawn(Colour.WHITE, new int[]{6,0});
        p[6][1] = new WhitePawn(Colour.WHITE, new int[]{6,1});
        p[6][2] = new WhitePawn(Colour.WHITE, new int[]{6,2});
        p[6][4] = new Knight(Colour.WHITE, new int[]{6,4});
        p[6][6] = new WhitePawn(Colour.WHITE, new int[]{6,6});
        p[6][7] = new WhitePawn(Colour.WHITE, new int[]{6,7});
        p[7][0] = new Rook(Colour.WHITE, new int[]{7,0});
        p[7][4] = new King(Colour.WHITE, new int[]{7,4});
        p[7][5] = new Bishop(Colour.WHITE, new int[]{7,5});
        p[7][7] = new Rook(Colour.WHITE, new int[]{7,7});
        
        Clone c = b.newClone();
        ChessBoardConnect con = ChessBoardConnect.getInstance();
        con.setColour(Colour.WHITE);
        
        System.out.println(c.printBoard());
        System.out.println();
        System.out.println(c.isPositionAttacked(new int[]{7,4}));
    }

}
