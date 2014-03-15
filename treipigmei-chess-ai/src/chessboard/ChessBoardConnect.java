package chessboard;

import java.io.InputStreamReader;


import java.util.ArrayList;

import board.Board;
import board.Move;
import brain.Brain;

/**
 * Class ChessBoardConnect implements the comunication protocol between XBoard
 * and the chess engine
 * 
 * @author mey
 * 
 */
public class ChessBoardConnect {

    private static ChessBoardConnect instance = null;

    private ChessBoardConnect() {

    }

    public static ChessBoardConnect getInstance() {
        if (instance == null) {
            instance = new ChessBoardConnect();
        }
        return instance;
    }

    private Board chessBoard;
    private static final ArrayList<String> protocolCommands = new ArrayList<String>();
    private boolean legalMove = true;
    private boolean whiteOnTurn = false;
    private boolean blackOnTurn = false;
    private boolean forceMode = false;

    /**
     * The method below set all recognizible protocol commands
     */
    public static void setProtocolCommands() {
        protocolCommands.add("xboard");
        protocolCommands.add("new");
        protocolCommands.add("force");
        protocolCommands.add("white");
        protocolCommands.add("black");
        protocolCommands.add("move");
        protocolCommands.add("go");
        protocolCommands.add("quit");
        protocolCommands.add("resign");
        protocolCommands.add("protover 2");
    }

    public boolean getWhiteOnTurn() {
        return this.whiteOnTurn;
    }

    /**
     * Takes an input from the input stream
     */

    public void readInput() {

        int character;
        String input = "";

        try {
            InputStreamReader inputReader = new InputStreamReader(System.in);

            while (true) {
                character = inputReader.read();
                if (character != -1) {
                    input += (char) character;
                }
                if (character == '\n') {
                    processInput(input);
                    input = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Recive a string and interpret it
     * 
     * @param input
     *            - the string that must be processed by the chess engine
     */
    private void processInput(String input) {
        input = input.trim();
        System.out.println("Inputul este: " + input);

        // check if the input is a command or a move
        if (protocolCommands.contains(input)) {

            switch (input) {
            case "xboard":
                System.out.println("S-a realizat comunicarea cu Xboard");
                break;

            case "protover 2":
                System.out.println("feature myname = TreiPigMei");
                System.out.println("feature usermove = 0");
                break;

            case "new":
                // chessBoard = new ChessBoard();
                System.out
                        .println("Se creeaza un nou joc; jucatorul alb este primul care va muta");
                whiteOnTurn = true;
                blackOnTurn = false;
                break;

            case "force":
                forceMode = true;
                System.out
                        .println("Masina intra in modul fortat; va tine locul ambilor jucatori");
                break;

            case "go":
                forceMode = false;
                System.out
                        .println("Masina paraseste modul fortat si va juca doar pentru cel care e la rand");

                break;

            case "white":
                System.out
                        .println("Randul este dat juacatorului cu alb; masina ma juca cu negru");
                whiteOnTurn = true;
                blackOnTurn = false;
                break;

            case "black":
                blackOnTurn = true;
                whiteOnTurn = false;
                System.out
                        .println("Masina va juca cu negru; jucatorul cu ab va face primul mutarea");
                break;

            case "quit":
                System.out.println("Jocul se termina");
                System.exit(0);

            case "resign":
                System.out.println("Masina renunta");
                if (whiteOnTurn)
                    System.out.println("0 - 1 {White resigns");
                else
                    System.out.println("1 - 0 {Black resigns");

                break;
            }

            /*
             * If the input is not a command then it must be a move. If it is a
             * legal one, the Chess engine will apply it.
             */
        } else if (input.matches("[a-h][1-8][a-h][1-8]")) {
            System.out.println("se potriveste cu regex-ul");
            System.out.println("inputul este: " + input);

            if (chessBoard.movePiece(new Move(input))) {
                System.out.println("a trecut de nullPointerExcep.");
            } else {
                System.out.println("Error: Illegal move!");
                return;
            }

            if (!forceMode) {
                System.out
                        .println("The engine is not in force mode, so it must make a move");
                String move = Brain.think();
                if (chessBoard.movePiece(new Move(move))) {
                    System.out.println("move");
                } else {
                    System.exit(0);
                }
            }
        }
    }
}
