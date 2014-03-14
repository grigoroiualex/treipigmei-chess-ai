
import chessboard.*;

public class Main {
    public static void main(String[] args) {
        ChessBoardConnect.setProtocolCommands();

        ChessBoardConnect chessProtocol = new ChessBoardConnect();
        chessProtocol.readInput();
    }

}
