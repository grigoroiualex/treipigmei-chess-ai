package helpers;

import piece.*;

/**
 * Class that saves all the flags that the engine needs
 * 
 * @author grigoroiualex
 *
 */
public class Flags {
    public static enum Colour {
        WHITE, BLACK;
    }
    public static boolean PROMOTION;
    public static boolean CASTLING;
    public static King BLACK_KING, WHITE_KING;
    public static int NEGAMAX_DEPTH = 4;
    public static int MOVES_LIMIT = 50;
    public static int MOVES = 0;
    public static int REPETITION = 0;
    public static int REPETITION_LIMIT = 3;
    
    public static void resetParams() {
        MOVES = 0;
        REPETITION = 0;
    }
}
