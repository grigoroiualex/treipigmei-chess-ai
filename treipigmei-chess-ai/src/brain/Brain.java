package brain;

import java.util.ArrayList;


import helpers.Flags;
import piece.*;
import connection.ChessBoardConnect;
import board.Board;
import board.Clone;
import board.Evaluation;
import board.Move;

/**
 * Class Brain computes the next simple move
 * 
 * @author Florin
 * 
 */
public class Brain {
    static char[] moveToDo = new char[4];
    static int[] from = new int[2];
    static int[] to = new int[2];
    public static Move bestMove; // use it in negamex to get the best move

    /**
     * Returns the next possible move for the current pawn
     * 
     * @return move The move as a string ready for output
     */
    public static String think() {

        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Board board = Board.getInstance();

        ArrayList<Integer> moves;
        Piece pieceToMove = null;

        // If engine's colour is white
        if (chessBoardConnect.getChessEngineColour() == Flags.Colour.WHITE) {

            boolean whiteKingAttacked = isPositionAttacked(Flags.WHITE_KING
                    .getPosition());
            // and if the king's position is attacked, it tries to move the king
            if (whiteKingAttacked) {
                pieceToMove = Flags.WHITE_KING;
            } else {
                pieceToMove = board.getWhitePiece();
                while (board.getValidMoves(pieceToMove) == null
                        || pieceToMove instanceof King) {
                    pieceToMove = board.getWhitePiece();
                }
            }

            moves = board.getValidMoves(pieceToMove);
            if (moves == null) {
                to = new int[] { 8, 7 };
                return getMove(from[0], from[1], to[0], to[1]);
            }
            int move = moves.get((int) ((Math.random() * 100) % moves.size()));

            // If there aren't any valid moves for the king, resign
            if (whiteKingAttacked || pieceToMove instanceof King) {
                eliminateInvalidMoves(moves);

                if (moves.isEmpty()) {
                    to = new int[] { 8, 7 };
                }
            } else {
                to = new int[] { move / 8, move % 8 };
            }

        } else {
            boolean blackKingAttacked = isPositionAttacked(Flags.BLACK_KING
                    .getPosition());
            // and if the king's position is attacked, it tries to move the king
            if (blackKingAttacked) {
                pieceToMove = Flags.BLACK_KING;
            } else {
                pieceToMove = board.getBlackPiece();
                while (board.getValidMoves(pieceToMove) == null
                        || pieceToMove instanceof King) {
                    pieceToMove = board.getBlackPiece();
                }
            }

            moves = board.getValidMoves(pieceToMove);
            if (moves == null) {
                to = new int[] { 8, 7 };
                return getMove(from[0], from[1], to[0], to[1]);
            }
            int move = moves.get((int) ((Math.random() * 100) % moves.size()));

            // If there aren't any valid moves for the king, resign
            if (blackKingAttacked || pieceToMove instanceof King) {
                eliminateInvalidMoves(moves);

                if (moves.isEmpty()) {
                    to = new int[] { 8, 7 };
                }
            } else {
                to = new int[] { move / 8, move % 8 };
            }
        }
        from = pieceToMove.getPosition();

        return getMove(from[0], from[1], to[0], to[1]);
    }

    /**
     * Strips the moves that are attacked when is the king is on turn
     * 
     * @param moves
     *            Array containing all the possible moves for one piece
     */
    public static void eliminateInvalidMoves(ArrayList<Integer> moves) {
        ArrayList<Integer> newMoves = new ArrayList<Integer>();

        for (Integer i : moves) {

            if (!isPositionAttacked(new int[] { i / 8, i % 8 })) {
                newMoves.add(i);
            }
        }
        moves = newMoves;
    }

    /**
     * Decodes the move given as table coordinates to XBoard command
     * 
     * @param lineFrom
     *            The line from which to move
     * @param columnFrom
     *            The column from which to move
     * @param lineTo
     *            The line to move to
     * @param columnTo
     *            The column to move to
     * @return move The move to be done as a String
     */
    public static String getMove(int lineFrom, int columnFrom, int lineTo,
            int columnTo) {

        moveToDo[0] = (char) ('a' + columnFrom);
        moveToDo[1] = (char) ('8' - lineFrom);
        moveToDo[2] = (char) ('a' + columnTo);
        moveToDo[3] = (char) ('8' - lineTo);

        Board board = Board.getInstance();
        Piece currentPiece = board.getPiece(new int[] { lineFrom, columnFrom });

        if ((currentPiece instanceof BlackPawn && lineTo == 7)
                || (currentPiece instanceof WhitePawn && lineTo == 0)) {

            Flags.PROMOTION = true;
            return String.valueOf(moveToDo) + "q";
        }

        Flags.PROMOTION = false;
        return String.valueOf(moveToDo);
    }

    /**
     * Checks if the position given as parameter is attacked by any of the
     * opponent's pieces
     * 
     * @param pos
     *            The position to check
     * @return boolean True if the position is attacked and false otherwise
     */
    public static boolean isPositionAttacked(int[] pos) {
        Board board = Board.getInstance();
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        Piece piece;
        int x = pos[0];
        int y = pos[1];
        int i, j;
        int[] kx = { -2, -2, -1, -1, 1, 1, 2, 2 };
        int[] ky = { -1, 1, -2, 2, -2, 2, -1, 2 };

        i = y - 1;
        // for every position upwards
        while (Piece.isValid(x, i)) {
            // checks for any piece
            if ((piece = board.getPiece(new int[] { x, i })) != null) {
                // If it's own engine's colour it's ok
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                    // otherwise
                } else {
                    // the piece is a sliding one
                    if (piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--;
        }

        i = y + 1;
        // for every position upwards
        while (Piece.isValid(x, i)) {
            if ((piece = board.getPiece(new int[] { x, i })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++;
        }

        i = x - 1;
        // for every position to the left
        while (Piece.isValid(i, y)) {
            if ((piece = board.getPiece(new int[] { i, y })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--;
        }

        i = x + 1;
        // for every position to the right
        while (Piece.isValid(i, y)) {
            if ((piece = board.getPiece(new int[] { i, y })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Rook || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++;
        }

        i = x + 1;
        j = y + 1;
        // for every diagonal position NE
        while (Piece.isValid(i, j)) {
            if ((piece = board.getPiece(new int[] { i, j })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++;
            j++;
        }

        i = x + 1;
        j = y - 1;
        // for every diagonal position SE
        while (Piece.isValid(i, j)) {
            if ((piece = board.getPiece(new int[] { i, j })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i++;
            j--;
        }

        i = x - 1;
        j = y - 1;
        // for every diagonal position SV
        while (Piece.isValid(i, j)) {
            if ((piece = board.getPiece(new int[] { i, j })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--;
            j--;
        }

        i = x - 1;
        j = y + 1;
        // for every diagonal position NV
        while (Piece.isValid(i, j)) {
            if ((piece = board.getPiece(new int[] { i, j })) != null) {
                if (piece.getColor() == chessBoardConnect
                        .getChessEngineColour()) {
                    break;
                } else {
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
            i--;
            j++;
        }

        // for every possible knight position
        for (int k = 0; k < 8; k++) {
            if (Piece.isValid(x + kx[k], y + ky[k])) {
                if ((piece = board.getPiece(new int[] { x + kx[k], y + ky[k] })) != null) {
                    if (piece.getColor() == chessBoardConnect
                            .getChessEngineColour()) {
                        break;
                    } else {
                        if (piece instanceof Knight) {
                            return true;
                        }
                    }
                }
            }
        }

        // for black pawns
        if (Piece.isValid(x - 1, y - 1)) {
            if ((piece = board.getPiece(new int[] { x - 1, y - 1 })) != null) {
                if (piece.getColor() != chessBoardConnect
                        .getChessEngineColour() && piece instanceof BlackPawn) {
                    return true;
                }
            }
        }

        // for black pawns
        if (Piece.isValid(x - 1, y + 1)) {
            if ((piece = board.getPiece(new int[] { x - 1, y + 1 })) != null) {
                if (piece.getColor() != chessBoardConnect
                        .getChessEngineColour() && piece instanceof BlackPawn) {
                    return true;
                }
            }
        }

        // for white pawns
        if (Piece.isValid(x + 1, y + 1)) {
            if ((piece = board.getPiece(new int[] { x + 1, y + 1 })) != null) {
                if (piece.getColor() != chessBoardConnect
                        .getChessEngineColour() && piece instanceof WhitePawn) {
                    return true;
                }
            }
        }

        // for white pawns
        if (Piece.isValid(x + 1, y - 1)) {
            if ((piece = board.getPiece(new int[] { x + 1, y - 1 })) != null) {
                if (piece.getColor() != chessBoardConnect
                        .getChessEngineColour() && piece instanceof WhitePawn) {
                    return true;
                }
            }
        }

        return false;
    }
    
    
    public static int negaMax(Clone chessBoard, int depth) {
        if (depth == 0) {
            Evaluation evaluatedBoard = new Evaluation(chessBoard);
            return evaluatedBoard.eval();
        }
        ArrayList<Move> moves = chessBoard.getAllMoves();

        if (moves.isEmpty()) {
            // System.out.println("intra in move.isEmpty");
            // se verifica daca e remiza sau a casigat cineva
            if (chessBoard.getWhites().size() == 1
                    && chessBoard.getBlacks().size() == 1) {
                return 0;
            } else {
                System.out.println("intra pe return -1 cand un rege e in sah");
                return 10000;
            }
        }

        int bestScore = Integer.MIN_VALUE;

        for (Move move : moves) {
            Clone clonedBoard = chessBoard.newClone();
            clonedBoard = clonedBoard.getCloneWithMove(move);
            int score = -negaMax(clonedBoard, depth - 1);
          
            if (score >= bestScore) {
                bestScore = score;
                if (depth == Flags.NEGAMAX_DEPTH)  //adancimea maxima
                    bestMove = move;
            }
        }
        if (bestMove == null) {
            bestMove = moves.get(0);
        }
        return bestScore;
    }

}
