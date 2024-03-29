package connection;

import helpers.*;

import helpers.Flags.Colour;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import piece.Piece;
import board.Board;
import board.Clone;
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
     * @return chessEngineColour The chess engine colour
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
                Flags.resetParams();
                forceMode = false;
                break;

            case "force":
                forceMode = true;
                break;

            case "go":
                forceMode = false;
                Clone clone = chessBoard.newClone();
                String move = null;
                Brain.bestMove = null;
                int x, y;
                Piece king = (chessEngineColour == Colour.WHITE) ? Flags.WHITE_KING : Flags.BLACK_KING; 
                x = king.getPosition()[0];
                y = king.getPosition()[1];
                    
                if(onTurn) {
                    if(clone.isPositionAttacked(new int[]{x, y})) {
                        move = clone.getKingOutOfCheck(chessEngineColour);
                    } else {
                        /* Choose one of the algorithms by uncommenting it and commenting the others
                         *------------------------------------------------------ */
                        // Brain.negaMax(clone, Flags.NEGAMAX_DEPTH);
                        // Brain.alfaBeta(clone, Flags.NEGAMAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        Brain.negaScout(clone, Flags.NEGAMAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        /*------------------------------------------------------ */
                        if(Brain.bestMove != null) {
                            move = Brain.bestMove.toString();
                        }
                    }

                    if(move == null) {
                        Functions.output("resign");
                    } else if (chessBoard.moveMyPiece(new Move(move))) {
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

                        if(clone.isPositionAttacked(new int[]{x, y})) {
                            move = clone.getKingOutOfCheck(chessEngineColour);
                        } else {
                            /* Choose one of the algorithms by uncommenting it and commenting the others
                             *------------------------------------------------------ */
                            // Brain.negaMax(clone, Flags.NEGAMAX_DEPTH);
                            // Brain.alfaBeta(clone, Flags.NEGAMAX_DEPTH, Integer.MIN_VALUE, Integer.MIN_VALUE);
                            Brain.negaScout(clone, Flags.NEGAMAX_DEPTH, Integer.MIN_VALUE, Integer.MIN_VALUE);
                            /*------------------------------------------------------ */
                            if(Brain.bestMove != null) {
                                move = Brain.bestMove.toString();
                            }
                        }

                        if(move == null) {
                            Functions.output("resign");
                        } else if (chessBoard.moveMyPiece(new Move(move))) {
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

                if(input.length() == 5){
                    Flags.PROMOTION = true;
                }
                // Apply the received move on the board,
                if (chessBoard.movePiece(new Move(input))) {
                    legalMove = true;
                } else {
                    Functions.output("Illegal move: " + input);
                    legalMove = false;
                }

                if (legalMove) {
                    String move = null;
                    Brain.bestMove = null;
                    Clone clone = chessBoard.newClone();
                    int x, y;
                    Piece king = (chessEngineColour == Colour.WHITE) ? Flags.WHITE_KING : Flags.BLACK_KING; 
                    x = king.getPosition()[0];
                    y = king.getPosition()[1];

                    if(clone.isPositionAttacked(new int[]{x, y})) {
                        move = clone.getKingOutOfCheck(chessEngineColour);
                    } else {
                        /* Choose one of the algorithms by uncommenting it and commenting the others
                         *------------------------------------------------------ */
                        // Brain.negaMax(clone, Flags.NEGAMAX_DEPTH);
                        // Brain.alfaBeta(clone, Flags.NEGAMAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        Brain.negaScout(clone, Flags.NEGAMAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        /*------------------------------------------------------ */
                        if(Brain.bestMove != null) {
                            move = Brain.bestMove.toString();
                        }
                    }

                    if (move == null) {
                        Functions.output("resign");
                    } else if (chessBoard.moveMyPiece(new Move(move))) {
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
