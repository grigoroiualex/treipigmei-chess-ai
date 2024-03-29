package brain;

import java.util.ArrayList;
import helpers.Flags;
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
    public static Move bestMove;
    public static Move badMove;

    /**
     * Implementation of the Negamax algorithm
     * 
     * @param chessBoard Current board instance
     * @param depth Depth of the search
     * @return score The score for the current move
     */
    public static int negaMax(Clone chessBoard, int depth) {
        if (depth == 0) {
            Evaluation evaluatedBoard = new Evaluation(chessBoard);
            return evaluatedBoard.eval();
        }
        ArrayList<Move> moves = chessBoard.getAllMoves();

        if (moves.isEmpty()) {
            if (chessBoard.getWhites().size() == 1
                    && chessBoard.getBlacks().size() == 1) {
                return 0;
            } else {
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
                if (depth == Flags.NEGAMAX_DEPTH) { 
                    bestMove = move;
                }
            }
        }
        if (bestMove == null) {
            bestMove = moves.get(0);
        }
        return bestScore;
    }

    /**
     * Implementation of the Alpha Beta Pruning algorithm
     * 
     * @param chessBoard The clone to work with
     * @param depth The depth that has been reached so far
     * @param alpha Engine score
     * @param beta Opponent score
     * @return score Best score that was found
     */
    public static int alfaBeta(Clone chessBoard, int depth, int alfa, int beta) {

        if(depth == 0) {
            Evaluation evaluatedBoard = new Evaluation(chessBoard);
            return evaluatedBoard.eval();
        }

        ArrayList<Move> moves = chessBoard.getAllMoves();

        if (moves.isEmpty()) {
            if (chessBoard.getWhites().size() == 1
                    && chessBoard.getBlacks().size() == 1) {
                return 0;
            } else {
                return 10000;
            }
        }

        int bestScore = Integer.MIN_VALUE;

        for(Move m : moves) {
            Clone clonedBoard = chessBoard.newClone();
            clonedBoard = clonedBoard.getCloneWithMove(m);

            if(bestScore > alfa) {
                alfa = bestScore;
            }

            int score = -alfaBeta(clonedBoard, depth - 1, -beta, -alfa);

            if(score > bestScore) {
                bestScore = score;
                if(depth == Flags.NEGAMAX_DEPTH) {
                    bestMove = m;
                }
            }
        }

        if (bestMove == null) {
            bestMove = moves.get(0);
        }
        return bestScore;
    }

    /**
     * Implementation of the Negascout algorithm
     * 
     * @param chessBoard The clone to work with
     * @param depth The depth that has been reached so far
     * @param alpha Engine score
     * @param beta Opponent score
     * @return score Best score that was found
     */
    public static int negaScout(Clone chessBoard, int depth, int alpha, int beta) {
        if(depth == 0) {
            Evaluation evaluatedBoard = new Evaluation(chessBoard);
            return evaluatedBoard.eval();
        }

        ArrayList<Move> moves = chessBoard.getAllMoves();

        if (moves.isEmpty()) {
            if (chessBoard.getWhites().size() == 1
                    && chessBoard.getBlacks().size() == 1) {
                return 0;
            } else {
                return 10000;
            }
        }

        int best = Integer.MIN_VALUE;
        int n = beta;

        if(depth == Flags.NEGAMAX_DEPTH)
            bestMove = moves.get(0);
    
        while(moves.size() > 0) {
            Move m = moves.remove(0);
            Clone clone = chessBoard.newClone();
            clone = clone.getCloneWithMove(m);
            int score = -negaScout(clone, depth - 1, -n, -Math.max(alpha, best));
         
            if(score > best) {
                if(n == beta || depth <= 2) {
                    best = score;
                    if(depth == Flags.NEGAMAX_DEPTH){
                        bestMove = m;
                    }
                } else {
                    best = -negaScout(clone, depth-1, -beta, -score);
                    if(depth == Flags.NEGAMAX_DEPTH) 
                        bestMove = m;
                }
            }
            n = Math.max(alpha, best);
        }
        if(bestMove == null){
            bestMove = moves.get(0);
        }
        return best;
    }
} 
