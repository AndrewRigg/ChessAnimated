package board;

import java.util.ArrayList;

import chess_piece.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {
	
	Player player1, player2, currentPlayer, opponent;
	ArrayList<Circle> validMoveMarkers;
	ArrayList<Coord> validMoves;
	Piece selectedPiece, clickedPiece;
	boolean pieceCurrentlySelected, movingPiece, taking, pieceHighlighted, startingMove = true;
	int gridsize = Utils.GRIDSIZE;
	public ArrayList<Coord> whiteTakenPieces, blackTakenPieces;
	
	public Controller(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer = player1;
		opponent = player2;
		initialiseMoves(player1, player2);
		initialiseMoves(player2, player1);
		//Do this next section only when first player is selected:
		player1.setTurn(true);
		validMoves = new ArrayList<>();
		validMoveMarkers = new ArrayList<>();
	}	
	
	public void initialiseMoves(Player player, Player opponent) {
		for(Piece piece: player.pieces) {
			piece.calculateValidMoves(player, opponent);
		}
	}
	
	public void print(String str) {
		Utils.print(str, Utils.CONTROLLER_DEBUG);
	}
	
	private void changeTurns() {
		print("Changing Turns\n__________Current Turn: " + (player1.isTurn() ? "BLACK" : "WHITE") + "__________");
		//Here record the move played
		//recordingMove();
		pieceCurrentlySelected = false;
		//selectedPiece = null;
		//clickedPiece = null;
		changePlayerTurn(player1);
		changePlayerTurn(player2);
	}
	
	private void changePlayerTurn(Player player) {
		player.setTurn(!player.isTurn());
		if(player.clockActive) {
			player.getClock().setRunning(player.isTurn());
		}
		if(player.isTurn()){
			setCurrent(player);
			player.getClock().countdown.setTextFill(Utils.run);
		}else {
			setOpponent(player);
			player.getClock().countdown.setTextFill(Utils.wait);
		}
	}
		
	public Player getCurrent() {
		return currentPlayer;
	}

	public void setCurrent(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * Calculate the valid moves which can be made by the currently selected piece
	 * This will generate green circles as a visual indication of valid moves
	 * @param piece
	 */
	private void calculateValidMoves(Player player, Player opponent, Piece piece) {
		validMoves.clear();
		piece.calculateValidMoves(currentPlayer, opponent);
		validMoves.addAll(piece.getValidMoves());
		for(Coord coord: validMoves) {
			Circle circle =  new Circle(gridsize/3.5);
			circle.setFill(Color.GREENYELLOW);
			circle.setCenterX(coord.getX() * gridsize + gridsize/2);
			circle.setCenterY(coord.getY() * gridsize + gridsize/2);
			validMoveMarkers.add(circle);
		}
	}
	
	private void removeValidMoves() {
		for(Circle circle: validMoveMarkers) {
			circle.setFill(Color.color(0, 0, 0, 0));
		}
		validMoveMarkers.clear();
	}
	
	/**
	 * This is the method to determine which type of click has occurred
	 * and what to do with it
	 * This only cares about whether a square is empty, has own colour or
	 * different colour
	 * @param coord
	 */
	public void determineClickType(Coord coord) {
		print(".........MOUSE CLICK REGISTERED.........");
		removeValidMoves();
		if(checkCoordForPiece(currentPlayer, coord)) {
			if(startingMove) {
				if(player1.clockActive) {
					player1.getClock().setRunning(true);
					player1.getClock().countdown.setTextFill(Utils.run);
					startingMove = false;
				}
			}
			clickedOnOwnColour();
		}else if(checkCoordForPiece(opponent, coord)) {
			clickedOnOppositeColour();
		}else {
			clickedOnEmptySquare(coord);
		}
	}

	/**
	 * Determine if a piece has been clicked on
	 * @param player
	 * @param coord
	 * @return
	 */
	private boolean checkCoordForPiece(Player player, Coord coord) {
		for(Piece piece: player.pieces) {
			if (compareCoords(piece.getCoord(), coord)){
				clickedPiece = piece;
				return true;
			}
		}
		return false;
	}

	public boolean compareCoords(Coord coord1, Coord coord2) {
		return (coord1.getX() == coord2.getX() && coord1.getY() == coord2.getY());
	}
	
	/**
	 * Action to move piece to an empty square
	 * @param coord
	 */
	public void movePiece(Coord coord) {
		defaultSizes();
		print("Moving Piece");
		movingPiece = true;
		selectedPiece.setCoord(new Coord(coord.getX(), coord.getY()));
	}
	
	/**
	 * Action to move piece to an empty square
	 * @param coord
	 */
	public void takingPiece(Coord coord) {
		print("Taking Piece");
		taking = true;
		selectedPiece.setCoord(new Coord(coord.getX(), coord.getY()));
	}
	
	/**
	 * Set piece to the one clicked on
	 * @param piece
	 */
	public void selectPiece(Piece piece) {
		defaultSizes();
		print("Selected Piece " + piece.getName());
		selectedPiece = piece;
		pieceCurrentlySelected = true;	//Do this when the piece is clicked on
		calculateValidMoves(currentPlayer, opponent, piece);
	}
	
	/**
	 * Action when clicking on current piece
	 */
	public void clickedOnSelf() {
		print("Clicked on Self");
		unselectPiece();
	}
	
	/**
	 * Action when clicking on empty square
	 */
	public void clickedOnEmptySquare(Coord coord) {
		print("Clicked on Empty Square");
		if(pieceCurrentlySelected) {
			if(validSquareSelection(coord)) {
				movePiece(coord);
				changeTurns();
			}else {
				unselectPiece();
			}
		}else {
			doNothing();
		}
		pieceCurrentlySelected = false;
	}
	
	/**
	 * Check if the selected square is a valid move
	 * @param square
	 * @return
	 */
	private boolean validSquareSelection(Coord square) {
		for(Coord coord: validMoves) {
			if(square.getX() == coord.getX() && square.getY() == coord.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Deal with action for when clicking on the same piece
	 * @param piece
	 */
	public void clickedOnOwnColour() {
		print("Clicked on Own Colour");
		if(validPieceSelection(clickedPiece)) {
			if(pieceCurrentlySelected) {
				if(compareCoords(clickedPiece.getCoord(), selectedPiece.getCoord())) {
					clickedOnSelf();
				}else {
					changePiece(clickedPiece);
				}
			}else {
				selectPiece(clickedPiece);
			}
		}else {
			doNothing();
		}
	}
	
	/**
	 * Check to determine if the piece can move
	 * @param piece
	 * @return
	 */
	private boolean validPieceSelection(Piece piece) {
		return !piece.getValidMoves().isEmpty();
	}
	
	/**
	 * Deal with action for when clicking on opposite piece
	 * @param piece
	 */
	public void clickedOnOppositeColour() {
		print("Clicked on Opposite Colour");
		if(pieceCurrentlySelected && validPieceCapture(clickedPiece)) {
			takePiece(clickedPiece);
		}else {
			unselectPiece();
		}
	}
	/**
	 * Check the piece can be captured
	 * @param piece
	 * @return
	 */
	public boolean validPieceCapture(Piece piece) {
		for(Coord coord: validMoves) {
			if(piece.getCoord().getX() == coord.getX() && piece.getCoord().getY() == coord.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Action to send taken piece to the graveyard (off grid) and move the current
	 * piece into its position
	 * @param piece
	 */
	public void takePiece(Piece piece) {
		defaultSizes();
		print("Taking Piece");
		Coord current = piece.getCoord();
		sendTakenPieceOffBoard(piece);
		takingPiece(current);
		changeTurns();
	}
	
	/**
	 * Figure out where in the graveyard of that colour to put the taken piece
	 * @param piece
	 */
	private void sendTakenPieceOffBoard(Piece piece) {
		print("Sending Piece Off the Board");
		Coord taken = opponent.getTakenZone(opponent.getTakenPieces());
		//opponent.addTakenPiece();
	}

	/**
	 * Action to change to another piece when one is already selected
	 * @param piece
	 */
	public void changePiece(Piece piece) {
		print("Changing Piece");
		unselectPiece();
		selectPiece(piece);
	}
	
	/**
	 * This is for when the click leaves the current state unchanged
	 */
	public void doNothing() {
		defaultSizes();
		print("Doing Nothing");
		return;
	}
	
	public void defaultSizes() {
		if(pieceCurrentlySelected) {
			selectedPiece.setWidth(Utils.DEFAULT_SIZE);
			selectedPiece.setHeight(Utils.DEFAULT_SIZE);
			selectedPiece.setX((int)(selectedPiece.getX()/gridsize)*gridsize + gridsize/4);
			selectedPiece.setY((int)(selectedPiece.getY()/gridsize)*gridsize + gridsize/4);
		}
	}
	
	/**
	 * Set selectedPiece to false and clear the valid squares
	 */
	public void unselectPiece() {
		defaultSizes();
		print("Unselecting Piece");
		pieceCurrentlySelected = false;
		selectedPiece = null;
		removeValidMoves();
	}
}
