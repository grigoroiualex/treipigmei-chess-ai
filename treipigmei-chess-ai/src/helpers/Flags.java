package helpers;

import piece.*;

public class Flags {
    public static enum Colour {
        WHITE, BLACK;
    }
    public static boolean PROMOTION;
    public static boolean CASTLING;
    public static King BLACK_KING, WHITE_KING;
    public static int NEGAMAX_DEPTH = 2;
}
