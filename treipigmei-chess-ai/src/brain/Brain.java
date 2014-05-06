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
     * Implementation of the negamax algorithm
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
    
    public static int alfaBeta(Flags.Colour playerColor, Clone chessBoard, int depth, int alfa, int beta) {
        
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
        
        if (moves != null) {
            for(Move m : moves) {
                Clone clonedBoard = chessBoard.newClone();
                clonedBoard = clonedBoard.getCloneWithMove(m);
                int score = -alfaBeta(playerColor, clonedBoard, depth - 1, -beta, -alfa);
                if(bestScore >= alfa) {
                    alfa = bestScore;
                }
                if(score >= beta) {
                    return beta;
                }
                if(score > alfa) {
                    alfa = score;
                    bestScore = score;
                    if(depth == Flags.NEGAMAX_DEPTH) {
                        bestMove = m;
                    }                  
                }
            }
        }
        if (bestMove == null) {
                bestMove = moves.get(0);
        }
        return bestScore;
    }
}
