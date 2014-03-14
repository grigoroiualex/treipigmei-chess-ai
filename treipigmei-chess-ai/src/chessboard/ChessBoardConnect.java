package chessboard;

import java.io.InputStreamReader;
import java.util.ArrayList;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;


public class ChessBoardConnect {
  //  private ChessBoard chessBoard;
    private static final ArrayList<String> protocolCommands = new ArrayList<String>();
    private boolean legalMove;
  //  private static Logger logger = Logger.getLogger(ChessBoardConnection.class);

   // static {
   //     PropertyConfigurator.configure("log4j.properties");
    //}

    public ChessBoardConnect() {
        // TODO Auto-generated constructor stub
    }

    private static void setProtocolCommands(){
        protocolCommands.add("xboard");
        protocolCommands.add("new");
        protocolCommands.add("force");
        protocolCommands.add("white");
        protocolCommands.add("black");
        protocolCommands.add("move");
        protocolCommands.add("go");
        protocolCommands.add("quit");
        protocolCommands.add("resign");
    }

    private boolean forceMode = false;

    /**
     * @param args
     */

    private void readInput(){

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
        }
        catch (Exception e) { 
            e.printStackTrace(System.out);
        }		

    }
    private void processInput(String input) {
        input = input.trim();

        if(protocolCommands.contains(input)){

            switch(input){
            case "xboard": 		//chessBoard = new ChessBoard();
                                //System.out.println("Input: xboard");
            break;

            case "new":			//chessBoard = new ChessBoard();
                                //System.out.println("Input: new");
            break;

            case "force":		forceMode = true;
                                //System.out.println("Input: force");
            break;

            case "go":			forceMode = false;
                                //System.out.println("Input: go");
            break;

            case "white":		//System.out.println("Input: white");
                break;

            case "black" :		//System.out.println("Input: black");
                break;

            case "quit" :		System.exit(0);

            case "resign" :		//System.out.println("Input: resign");
                break;
            }
        } else {
            //legalMove = identifyMove(input);
            //if(legalMove)
            //processMove(input);
            //else 
            //System.out.println("a primit mutarea: input");
            processMove(input);
            //System.out.println("Error: Illegal move");
        }
    }

    private void processMove(String input){
        //chessBoard.makeMove(input);
        //System.out.println("Ar trebui sa faca mutarea: input");
        //if(!forceMode) 
        //chessBoard.makeOwnMove();

    }

    public static void main(String[] args) {
        setProtocolCommands();

        ChessBoardConnect chessProtocol = new ChessBoardConnect();
        chessProtocol.readInput();

    }
}
