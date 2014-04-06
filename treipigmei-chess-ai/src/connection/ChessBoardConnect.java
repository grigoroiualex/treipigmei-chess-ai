package connection;

import helpers.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import debugging.DebugToFile;
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
    private ArrayList<String> protocolCommands;
    
    private Board chessBoard;
    private boolean legalMove;
    private boolean forceMode;
    private Flags.Colour chessEngineColour;
    DebugToFile debugger;

    private ChessBoardConnect() {
        legalMove = true;
        forceMode = false;
        debugger = DebugToFile.getInstance();
        protocolCommands = new ArrayList<String>();
        setProtocolCommands();
    }
    
    /**
     * The method below sets all recognizable protocol commands
     */
    public void setProtocolCommands() {
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
     * Gets the current instance of the singleton class
     * 
     * @return class instance
     */
    public static ChessBoardConnect getInstance() {
        if (instance == null) {
            instance = new ChessBoardConnect();
        }
        return instance;
    }

    /**
     * Tells the engine's colour
     * 
     * @return chessEngineColour
     */
    public Flags.Colour getChessEngineColour() {
        return this.chessEngineColour;
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
     * @param the string that must be processed by the chess engine
     */
    private void processInput(String input) {
        input = input.trim();
        Board board = Board.getInstance();

        // check if the input is a command or a move and if in force mode
        if ((protocolCommands.contains(input) && !forceMode) || (forceMode &&
                (!input.equals("xboard") || !input.equals("new") || 
                !input.equals("protover 2")))) {
            
            switch (input) {
            case "xboard":
                break;

            case "protover 2":
                Functions.output("feature myname=\"TreiPigMei\" sigterm=0 sigint=0 san=0 colors=1 done=1");
                break;

            case "new":
                chessBoard = Board.getNewInstance();
                chessEngineColour = Flags.Colour.BLACK;
                forceMode = false;
                break;

            case "force":
                forceMode = true;
                break;

            case "go":
                forceMode = false;
                
                String move = Brain.think();

                if (chessBoard.moveMyPiece(new Move(move))) {
                    Functions.output("move " + move);
                } else {
                    Functions.output("resign");
                }
                
                break;

            case "white":
                chessEngineColour = Flags.Colour.WHITE;
                break;

            case "black":
                chessEngineColour = Flags.Colour.BLACK;
                break;

            case "quit":
                debugger.close();
                System.exit(0);

            case "resign":
                if(chessEngineColour == Flags.Colour.BLACK) {
                    Functions.output("0 - 1 {White resigns");
                } else {
                    Functions.output("1 - 0 {Black resigns");
                }
                break;
            }

    /*
     * If the input is not a command then it must be a move. If it is a
     * legal one, the Chess engine will apply it.
     */
        } else {
            if (input.matches("[a-h][1-8][a-h][1-8][q]*")) {
                debugger.output("Received move: " + input);
                
                debugger.output("Table looks like: \n" + board.printBoard());
            	//TODO verifica daca este rocada sau promovarea pionului
                if (chessBoard.movePiece(new Move(input))) {
                    legalMove = true;
                } else {
                    Functions.output("Illegal move: " + input);
                    legalMove = false;
                }

                if (legalMove) {
                    String move = Brain.think();
                    debugger.output("Brain generated move: " + move);
                    if (chessBoard.moveMyPiece(new Move(move))) {
                        Functions.output("move " + move);
                    } else {
                        Functions.output("resign");
                    }
                }
            }
        }
    }
}
