
import connection.*;

/**
 * Main entry in the program. 
 * 
 * @author grigoroiualex
 *
 */
public class Main {
    public static void main(String[] args) {
        ChessBoardConnect.setProtocolCommands();

        ChessBoardConnect chessProtocol = ChessBoardConnect.getInstance();
        chessProtocol.readInput();
    }

}
