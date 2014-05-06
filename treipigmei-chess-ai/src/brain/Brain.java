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
    
    public static int principalVariation(Clone chessBoard, int depth, int alfa, int beta) {
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
        Move m = moves.remove(0);
        
        Clone clonedBoard = chessBoard.newClone();
        clonedBoard = clonedBoard.getCloneWithMove(m);
        
       best = -principalVariation(clonedBoard, depth - 1, -beta, -alfa);
       if(depth == Flags.NEGAMAX_DEPTH) {
           bestMove = m;
       }
       
       while(moves.size() > 0 && best < beta) {
           Move m1 = moves.remove(0);
           Clone newClone = chessBoard.newClone();
           newClone = newClone.getCloneWithMove(m1);
           if(best > alfa) {
               alfa = best;
           }
           int score = -principalVariation(newClone, depth-1, -alfa-1, -alfa);
           if(score > alfa && score < beta) {
               best = -principalVariation(chessBoard,  depth-1, -beta, -score);
               if(depth == Flags.NEGAMAX_DEPTH)
                   bestMove = m1;
           } else if (score > best) {
               best = score;
               if(depth == 4)
                   bestMove = m1;
           }
       }
       return best;
    }
} 
