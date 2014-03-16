
import connection.*;

public class Main {
    public static void main(String[] args) {
        ChessBoardConnect.setProtocolCommands();

        ChessBoardConnect chessProtocol = ChessBoardConnect.getInstance();
        chessProtocol.readInput();
    }

}
