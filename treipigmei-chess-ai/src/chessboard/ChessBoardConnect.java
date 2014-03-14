package chessboard;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChessBoardConnect {
    //  private ChessBoard chessBoard;
    private static final ArrayList<String> protocolCommands = new ArrayList<String>();
    private boolean legalMove = false;
    private boolean whiteOnTurn = false;
    private boolean blackOnTurn = false;

    private boolean forceMode = false;


    public ChessBoardConnect() {
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     */
    public static void setProtocolCommands(){
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

    /**
     * @param args
     */

    public void readInput(){

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
            case "xboard": 		
                System.out.println("S-a realizat comunicarea cu Xboard");
                break;

            case "new":			
               // chessBoard = new ChessBoard();
                System.out.println("Se creeaza un nou joc; jucatorul alb este primul care va muta");
                whiteOnTurn = true;
                blackOnTurn = false;
                break;

            case "force":		
                forceMode = true;
                System.out.println("Masina intra in modul fortat; va tine locul ambilor jucatori");
                break;

            case "go":			
                forceMode = false;
                System.out.println("Masina paraseste modul fortat si va juca doar pentru cel care e la rand");

                break;

            case "white":	
                System.out.println("Randul este dat juacatorului cu alb; masina ma juca cu negru");
                whiteOnTurn	= true;
                blackOnTurn = false;
                break;

            case "black" :		
                blackOnTurn = true;
                whiteOnTurn = false;
                System.out.println("Randul este dat jucatorului cu negru; masina va juca cu alb");
                break;

            case "quit" :		
                System.out.println("Jocul se termina");
                System.exit(0);

            case "resign" :		
                 System.out.println("Masina renunta");
                 if(whiteOnTurn)
                     System.out.println("0 - 1 {White resigns");
                 else
                     System.out.println("1 - 0 {Black resigns");
                 
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
}
