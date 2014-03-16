package connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import board.Board;
import board.Move;
import brain.Brain;

/**
 * Class ChessBoardConnect implements the communication protocol between XBoard
 * and the chess engine
 * 
 * @author mey
 * 
 */
public class ChessBoardConnect {

    private static ChessBoardConnect instance = null;

    private ChessBoardConnect() {

    }

    /**
     * Gets the current instance of the singleton class
     * 
     * @return - class instance
     */
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
    private boolean ourColour;

    /**
     * The method below sets all recognizable protocol commands
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

    /**
     * Tells if the white is on turn
     * 
     * @return - true if the white is on turn, false otherwise
     */
    public boolean getWhiteOnTurn() {
        return this.whiteOnTurn;
    }

    /**
     * Takes an input from the input stream
     */
    public void readInput() {

        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    System.in));
            String line;

            while (true) {
                line = buffer.readLine();
                processInput(line);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Receive a string and interpret it
     * 
     * @param input - the string that must be processed by the chess engine
     */
    private void processInput(String input) {
        input = input.trim();

        // check if the input is a command or a move and if in force mode
        if ((protocolCommands.contains(input) && !forceMode)
                || input.equals("go")) {
            switch (input) {
            case "xboard":
                break;

            case "protover 2":
                output("feature myname=\"TreiPigMei\" sigterm=0 sigint=0");
                break;

            case "new":
                chessBoard = Board.getNewInstance();
                whiteOnTurn = true;
                blackOnTurn = false;
                break;

            case "force":
                forceMode = true;
                break;

            case "go":
                forceMode = false;
                break;

            case "white":
                whiteOnTurn = true;
                blackOnTurn = false;
                break;

            case "black":
                blackOnTurn = true;
                whiteOnTurn = false;
                break;

            case "quit":
                System.exit(0);

            case "resign":
                if (whiteOnTurn) {
                    output("0 - 1 {White resigns");
                } else {
                    output("1 - 0 {Black resigns");
                }
                break;
            }

    /*
     * If the input is not a command then it must be a move. If it is a
     * legal one, the Chess engine will apply it.
     */
        } else {
            if (input.matches("[a-h][1-8][a-h][1-8]")) {

                if (chessBoard.movePiece(new Move(input))) {
                    legalMove = true;
                } else {
                    output("Error: Illegal move!");
                    legalMove = false;
                }

                if (!forceMode && legalMove) {
                    String move = Brain.think();

                    if (chessBoard.movePiece(new Move(move))) {
                        output("move " + move);
                    } else {
                        output("resign");
                    }
                }
            }
        }
    }

    /**
     * Prints the desired string and makes a flush afterwards
     *  
     * @param output - the desired string to be printed
     */
    private void output(String output) {
        System.out.println(output);
        System.out.flush();
    }
}
