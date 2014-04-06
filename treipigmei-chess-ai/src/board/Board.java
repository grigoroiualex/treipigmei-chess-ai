package board;

import helpers.Flags;
import helpers.Flags.Colour;

import java.util.ArrayList;

import brain.Brain;
import piece.*;

/**
 * Class that has the chess board representation. It is initialized with all
 * pieces on it.
 * 
 * @author grigoroiualex
 * 
 */
public class Board {
	private static Board instance = null;
	private Piece[][] field;
	private ArrayList<Piece> whites, blacks;

	/*
	 * Remus Cred ca ar trebui sa pastram instante separate pentur rege pentru a
	 * testa usor daca este in sah sau nu.
	 */

	private Board() {
		field = new Piece[8][8];
		whites = new ArrayList<>();
		blacks = new ArrayList<>();

		setPiece(new int[] { 0, 0 }, new Rook(Colour.BLACK,   new int[] { 0, 0 }));
		setPiece(new int[] { 0, 1 }, new Knight(Colour.BLACK, new int[] { 0, 1 }));
		setPiece(new int[] { 0, 2 }, new Bishop(Colour.BLACK, new int[] { 0, 2 }));
		setPiece(new int[] { 0, 3 }, new Queen(Colour.BLACK,  new int[] { 0, 3 }));
		setPiece(new int[] { 0, 4 }, new King(Colour.BLACK,   new int[] { 0, 4 }));
		setPiece(new int[] { 0, 5 }, new Bishop(Colour.BLACK, new int[] { 0, 5 }));
		setPiece(new int[] { 0, 6 }, new Knight(Colour.BLACK, new int[] { 0, 6 }));
		setPiece(new int[] { 0, 7 }, new Rook(Colour.BLACK,   new int[] { 0, 7 }));

		for (int i = 0; i < 8; i++) {
			setPiece(new int[] { 1, i }, new BlackPawn(Colour.BLACK, new int[] { 1, i }));
			setPiece(new int[] { 6, i }, new WhitePawn(Colour.WHITE, new int[] { 6, i }));

			for (int j = 2; j <= 5; j++) {
				setPiece(new int[] { j, i }, null);
			}
		}

		setPiece(new int[] { 7, 0 }, new Rook(Colour.WHITE,   new int[] { 7, 0 }));
		setPiece(new int[] { 7, 1 }, new Knight(Colour.WHITE, new int[] { 7, 1 }));
		setPiece(new int[] { 7, 2 }, new Bishop(Colour.WHITE, new int[] { 7, 2 }));
		setPiece(new int[] { 7, 3 }, new Queen(Colour.WHITE,  new int[] { 7, 3 }));
		setPiece(new int[] { 7, 4 }, new King(Colour.WHITE,   new int[] { 7, 4 }));
		setPiece(new int[] { 7, 5 }, new Bishop(Colour.WHITE, new int[] { 7, 5 }));
		setPiece(new int[] { 7, 6 }, new Knight(Colour.WHITE, new int[] { 7, 6 }));
		setPiece(new int[] { 7, 7 }, new Rook(Colour.WHITE,   new int[] { 7, 7 }));

		for (int i = 0; i < 8; i++) {
			whites.add(field[7][i]);
			blacks.add(field[0][i]);
		}

		for (int i = 0; i < 8; i++) {
			whites.add(field[6][i]);
			blacks.add(field[1][i]);
		}
		
		Flags.BLACK_KING = (King) getPiece(new int[] { 0, 4 });
		Flags.WHITE_KING = (King) getPiece(new int[] { 7, 4 });
	}
	
	/**
	 * Creates an instance of the class (if there wasn't one created else it
	 * uses the same) and returns it.
	 * 
	 * @return instance of the class
	 */
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}

		return instance;
	}

	/**
	 * Destroys current instance
	 */
	public static void initialize() {
		instance = null;
	}

	/**
	 * Creates a new instance of the class and returns it.
	 * 
	 * @return new instance of the class
	 */
	public static Board getNewInstance() {
		Board.initialize();
		return getInstance();
	}

	/**
	 * Put <i>piece</i> at position <i>pos</i>.
	 * 
	 * @param position
	 *            wanted
	 * @param piece
	 *            that is at position <i>pos</i>
	 */
	public void setPiece(int[] pos, Piece piece) {
		int i = pos[0];
		int j = pos[1];
		field[i][j] = piece;
	}

	/**
	 * Get <i>piece</i> from position <i>pos</i>.
	 * 
	 * @param position
	 *            on the board
	 * @return the piece that is at position pos
	 */
	public Piece getPiece(int[] pos) {
		int i = pos[0];
		int j = pos[1];
		return field[i][j];
	}

	/**
	 * Moves a piece on the chess board if possible
	 * 
	 * @param the
	 *            move to execute
	 * @return true if the move is executed, false otherwise
	 */
	public boolean movePiece(Move move) {
		// aici o sa verificam daca mutarea primita e valida

		
		//TODO de verificat daca este rocada, regele este mutat 2 pozitii
		
		applyPieceMove(move);
		return true;
	}

	/**
	 * Moves one of our pieces
	 * 
	 * @param the
	 *            move we want to apply
	 * @return true if the move is executed, false otherwise
	 */
	public boolean moveMyPiece(Move move) {
		/*
		ChessBoardConnect chessBoardConnect = ChessBoardConnect.getInstance();
		
		 * if (chessBoardConnect.getChessEngineColour() == Flags.Colour.BLACK) {
		 * 
		 * verifica daca piesa pe care o mut e pion si daca are coloarea
		 * corespunzatoare si daca unde vreau sa mut e vreo piesa
		 * 
		 * if (!(getPiece(move.getFrom()) instanceof BlackPawn) ||
		 * getPiece(move.getTo()) != null) { return false; } } else {
		 * 
		 * if (!(getPiece(move.getFrom()) instanceof WhitePawn) ||
		 * getPiece(move.getTo()) != null) { return false; } }
		 */

		int x = move.getTo()[0];
		int y = move.getTo()[1];
		
		//daca mutare iese din tabla, am generat cazul asta cand regele nu are mutari valide.
		if(!Piece.isValid(x, y)) {
			return false;
		}
		applyPieceMove(move);
		return true;
	}

	/**
	 * Applies a move on the chess board without verifying if it is valid
	 * 
	 * @param the
	 *            move to be executes
	 */
	public void applyPieceMove(Move move) {

		/*
		 * TODO  
		 * Cazul in care se face rocada trebuie interpretata mutarea, noi inca
		 * nu facem rocada.
		 */
		Board board = Board.getInstance();
		Piece posWhere = getPiece(move.getTo());
		Piece currentPiece = board.getPiece(move.getFrom());
		
		
		/*
		 * daca se face promovarea pionului il elimin din lista de piese si pun 
		 * o regina in locul lui, ca mai apoi sa se execute mutarea
		 */
		if(Flags.PROMOTION) {
			if (currentPiece.getColor() == Flags.Colour.WHITE) {
				for (int i = 0; i < whites.size(); i++) {
					if (currentPiece.equals(whites.get(i))) {
						whites.remove(i);
						currentPiece = new Queen(Colour.WHITE, move.getFrom());
						whites.add(currentPiece);
						break;
					}
				}
			} else {
				for (int i = 0; i < blacks.size(); i++) {
					if (currentPiece.equals(blacks.get(i))) {
						blacks.remove(i);
						currentPiece = new Queen(Colour.BLACK, move.getFrom());
						blacks.add(currentPiece);
						break;
					}
				}
			}
		
		}

		// daca este luata vreo piesa
		if (posWhere != null) {

			// daca piesa este alba o caut in tabloul pieselor albe s-o elimin
			if (posWhere.getColor() == Flags.Colour.WHITE) {
				for (int i = 0; i < whites.size(); i++) {
					if (posWhere.equals(whites.get(i))) {
						whites.remove(i);
						break;
					}
				}
			} else {
				for (int i = 0; i < blacks.size(); i++) {
					if (posWhere.equals(blacks.get(i))) {
						blacks.remove(i);
						break;
					}
				}
			}
		}
		
		currentPiece.setPosition(move.getTo());
		setPiece(move.getTo(), getPiece(move.getFrom()));
		setPiece(move.getFrom(), null);
	}

	/**
	 * @param pieceToMove
	 * @return a list of Integers with all pseudo-valid positions where this
	 *         piece can be moved. The position in the matrix can be obtained
	 *         like this: row = number / 8 column = number % 8
	 */
	
	//Linia din matrice este modificata de vectorul y din piese
	
	public ArrayList<Integer> getValidMoves(Piece pieceToMove) {

		int row, column, nextRow, nextColumn;

		row = pieceToMove.getPosition()[0];
		column = pieceToMove.getPosition()[1];

		ArrayList<Integer> array = new ArrayList<Integer>();
		Board board = Board.getInstance();

		// daca piesa este pion
		if (pieceToMove instanceof BlackPawn
				|| pieceToMove instanceof WhitePawn) {
			// daca poate ataca
			for (int i = 1; i < 3; i++) {
				nextRow =   (row + pieceToMove.getY()[i]);
				nextColumn =   (column + pieceToMove.getX()[i]);

				// daca nu ies din matrice
				if (Piece.isValid(nextRow, nextColumn)) {

					Piece posWhere = board.getPiece(new int[] { nextRow,
							nextColumn });
					// daca am piese pe pozitia unde vreau sa mut
					if (posWhere != null) {
						/*
						 * daca am piesa de aceeasi culoare ma opresc altfel
						 * adaug pozitia ca mutare valida si apoi ma opresc
						 */
						if (posWhere.getColor() != pieceToMove.getColor()) {
							array.add(nextRow * 8 + nextColumn);
						}
					}
				}
			}

			// daca nu poate ataca pionul testez daca poate inainta
			nextRow = (row + pieceToMove.getY()[0]);
			nextColumn = (column + pieceToMove.getX()[0]);
			Piece posWhere = board.getPiece(new int[] { nextRow, nextColumn });

			if (Piece.isValid(nextRow, nextColumn) && posWhere == null) {
				array.add(nextRow * 8 + nextColumn);
			}

		} else {

			for (int i = 0; i < pieceToMove.getX().length; i++) {

				/*
				 * daca piesa este tura, nebun sau regina iau un for de la 1 la
				 * 7 si generez toate mutarile posibile
				 */
				if (pieceToMove instanceof Rook
						|| pieceToMove instanceof Bishop
						|| pieceToMove instanceof Queen) {
					for (int j = 1; j < 8; j++) {
						nextRow = (row + pieceToMove.getY()[i] * j);
						nextColumn = (column + pieceToMove.getX()[i] * j);

						// daca nu ies din matrice
						if (Piece.isValid(nextRow, nextColumn)) {

							Piece posWhere = board.getPiece(new int[] {
									nextRow, nextColumn });
							// daca am piese pe pozitia unde vreau sa mut
							if (posWhere != null) {
								/*
								 * daca am piesa de aceeasi culoare ma opresc
								 * altfel adaug pozitia ca mutare valida si apoi
								 * ma opresc
								 */
								if (posWhere.getColor() == pieceToMove
										.getColor()) {
									break;
								} else {
									array.add(nextRow * 8 + nextColumn);
									break;
								}

								// daca nu e piesa machez pozitia ca mutare
								// valida
							} else {
								array.add(nextRow * 8 + nextColumn);
							}
						} else {
							break;
						}
					}
				} else {

					nextRow =   (row + pieceToMove.getY()[i]);
					nextColumn =   (column + pieceToMove.getX()[i]);
	
					if (Piece.isValid(nextRow, nextColumn)) {
						Piece posWhere = board.getPiece(new int[] {
								nextRow, nextColumn });
						// daca am piese pe pozitia unde vreau sa mut
						if (posWhere != null) {
							/*
							 * daca am piesa de aceeasi culoare ma opresc
							 * altfel adaug pozitia ca mutare valida si apoi
							 * ma opresc
							 */
							if (posWhere.getColor() == pieceToMove
									.getColor()) {
								break;
							} else {
								array.add(nextRow * 8 + nextColumn);
								break;
							}

						// daca nu e piesa machez pozitia ca mutare valida
						} else {
							array.add(nextRow * 8 + nextColumn);
						}
					} else {
						break;
					}
				}
			}
		}

		if (array.size() > 0) {
			return array;
		}

		return null;

	}

	/**
	 * 
	 * @return one instance of a random white piece
	 */
	public Piece getWhitePiece() {

		int x = (int) ((Math.random() * 100) % whites.size());
		return whites.get(x);
	}

	/**
	 * 
	 * @return one instance of a random black piece
	 */
	public Piece getBlackPiece() {

		int x = (int) ((Math.random() * 100) % blacks.size());
		return blacks.get(x);
	}
	
	public String printBoard() {
		String q = new String();
		Piece p;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
			    p = getPiece(new int[] {i, j});
			    if(p != null) {
			        q = q.concat(p + " ");
			    } else {
			        q = q.concat(". ");
			    }
			}
			q = q.concat("\n");
			
		}
		return q;
	}
	
	
}
