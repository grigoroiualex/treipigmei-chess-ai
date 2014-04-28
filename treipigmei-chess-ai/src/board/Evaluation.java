package board;
import connection.ChessBoardConnect;
import piece.Piece;
import helpers.Flags;

/**
 * Class that evaluates the score of the current state
 * 
 * @author grigoroiualex
 *
 */
public class Evaluation {
    Clone board;

    public Evaluation(Clone chessBoard){
        board = chessBoard;
    }

    /**
     * Gets the score for each piece and returns the global score
     *  
     * @return score
     */
    public int eval(){
        ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
        int whiteScore = 0;
        int blackScore = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                int[] position = new int[] {i, j};
                Piece piece = board.getPiece(position);
                if(piece != null){
                    if(piece.getColor() == Flags.Colour.WHITE){
                        whiteScore += piece.getValue() + piece.getPosValue(i, j);
                    } else {
                        blackScore += piece.getValue() + piece.getPosValue(7 - i, 7 - j);
                    } 
                }
            }
        }

        if(chessBoardConnect.getChessEngineColour() == Flags.Colour.WHITE){
            return (whiteScore - blackScore);
        } else {
            return (blackScore - whiteScore);
        }
    }
}