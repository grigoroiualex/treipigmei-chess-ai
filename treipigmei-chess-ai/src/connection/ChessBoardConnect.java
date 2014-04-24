package connection;

import helpers.*;

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
    private ArrayList<String> protocolCommands;
    
    private Board chessBoard;
    private boolean legalMove, forceMode;
    private boolean onTurn;
    private Flags.Colour chessEngineColour;
    String cachedMove;

    private ChessBoardConnect() {
        legalMove = true;
        forceMode = false;
        protocolCommands = new ArrayList<String>();
        setProtocolCommands();
        cachedMove = null;
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
     * @return instance The current instance
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

    // Takes an input from the input stream
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
     * @param input The string that must be processed by the chess engine
     */
    private void processInput(String input) {
        input = input.trim();

        // check if the input is a recognized command
        if(protocolCommands.contains(input)) {
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
                if(onTurn) {
                    String move = Brain.think();
    
                    if (chessBoard.moveMyPiece(new Move(move))) {
                        Functions.output("move " + move);
                        onTurn = false;
                    } else {
                        Functions.output("resign");
                    }
                } else {
                    if(cachedMove != null) {
                        if (chessBoard.movePiece(new Move(cachedMove))) {
                            legalMove = true;
                        } else {
                            Functions.output("Illegal move: " + cachedMove);
                            legalMove = false;
                        }
                        
                        String move = Brain.think();
        
                        if (chessBoard.moveMyPiece(new Move(move))) {
                            Functions.output("move " + move);
                            onTurn = false;
                        } else {
                            Functions.output("resign");
                        }
                    }
                }
                
                break;

            case "white":
                chessEngineColour = Flags.Colour.WHITE;
                onTurn = true;
                break;

            case "black":
                chessEngineColour = Flags.Colour.BLACK;
                onTurn = false;
                break;

            case "quit":
                System.exit(0);

            case "resign":
                if(chessEngineColour == Flags.Colour.BLACK) {
                    Functions.output("0 - 1 {White resigns");
                } else {
                    Functions.output("1 - 0 {Black resigns");
                }
                break;
            }
        }
        
        
        // If the engine is in not force mode and the input is not a recognized
        // command, than it must be a move
        if(!forceMode) {
            if(input.matches("[a-h][1-8][a-h][1-8][q]*")) {
                onTurn = true;
                
                // Apply the received move on the board,
                if (chessBoard.movePiece(new Move(input))) {
                    legalMove = true;
                } else {
                    Functions.output("Illegal move: " + input);
                    legalMove = false;
                }

                if (legalMove) {
                    // generate response move
                    //String move = Brain.think();
                    
                    Brain.negaMax(chessBoard.newClone(), 2);
                    String move = Brain.bestMove.toString();
                    // and then apply it
                    if (chessBoard.moveMyPiece(new Move(move))) {
                        Functions.output("move " + move);
                        onTurn = false;
                    } else {
                        Functions.output("resign");
                    }
                }
            }
        } else {
            // If the engine is in force mode XBoard sends the move before
            // the go command which means that the move needs to be saved
            if(input.matches("[a-h][1-8][a-h][1-8][q]*")) {
                cachedMove = new String(input);
            }
        }
    }
}
