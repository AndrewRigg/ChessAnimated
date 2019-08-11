package board;

import java.util.ArrayList;

import chess_piece.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Controller {
	
	Player player1, player2, currentPlayer, opponent;
	ArrayList<Circle> validMoveMarkers;
	ArrayList<Coord> validMoves;
	Piece selectedPiece;
	boolean pieceCurrentlySelected;
	int gridsize = Literals.GRIDSIZE;
	
	public Controller(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer = player1;
		opponent = player2;
		player1.setTurn(true);
		if(player1.clockActive) {
			player1.getClock().setRunning(true);
		}
		validMoves = new ArrayList<>();
		validMoveMarkers = new ArrayList<>();
	}
	
	public void print(String str) {
		Literals.print(str, Literals.CONTROLLER_DEBUG);
	}
	
	private void changeTurns() {
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
		}else {
			setOpponent(player);
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
	private void calculateValidMoves(Piece piece) {
		for (int i = piece.getMagnitudeMove()*(-1); i <= piece.getMagnitudeMove(); i++) {
			for (int j = piece.getMagnitudeMove()*(-1); j <= piece.getMagnitudeMove(); j++) {
				for (int k = 1; k <= piece.getMaximumMove(); k++) {
					if (piece.movementCondition(i, j, k) && !(i == 0 && j == 0)) {
						Coord coord = piece.validMoveCoord(i, j, k);
						if (coord.onGrid() && !piece.getValidMoves().contains(coord)) {
							Circle circle =  new Circle(gridsize/3.5);
							circle.setFill(Color.GREENYELLOW);
							circle.setCenterX(coord.getX() * gridsize + gridsize/2);
							circle.setCenterY(coord.getY() * gridsize + gridsize/2);
							//print(circle.getCenterX() + " " + circle.getCenterY());
							validMoveMarkers.add(circle);
							validMoves.add(coord);
						}
					}
				}
			}
		}
		//print(validMoveMarkers.toString());
	}
	
	/**
	 * This is the method to determine which type of click has occurred
	 * and what to do with it
	 * @param coord
	 */
	public void determineClickType(Coord coord) {
		print("Clicked on Coord: (" + coord.getX() + ", " + coord.getY() + ")");

		
			//A same coloured piece has been clicked on
			if(selectedPiece != null) {
				if(compareCoords(coord, selectedPiece.getCoord())) {
					print("Clicked on Self");
					clickedOnSelf();
				}
			}
			else if(checkCoordForPiece(currentPlayer, coord)) {
				Piece piece = getPieceClickedOn(currentPlayer, coord);
				print("Clicked on Same Coloured Piece");
				clickedOnSameColour(piece);
				
			}else if(checkCoordForPiece(opponent, coord)) {
				Piece piece = getPieceClickedOn(opponent, coord);
				print("Clicked on Opposite Coloured Piece");
				clickedOnOppositeColour(piece);
			}else {
				print("Clicked on Empty Square");
				clickedOnEmptySquare(coord);
			}
				//The piece clicked on was the one already selected (self)
//				if(compareCoords(coord, selectedPiece.getCoord())) {
//					clickedOnSelf();
//				}
//				//The piece clicked on was of the same colour
//				else{
//					Piece piece = getPieceClickedOn(currentPlayer, coord);
//					clickedOnSameColour(piece);
//				} //A different coloured piece has been clicked
//			}else if(checkCoordForPiece(opponent, coord)) {
//				//Is this a valid move?
//				if(isValidMove(coord)) {
//					Piece piece = getPieceClickedOn(opponent, coord);
//					clickedOnOppositeColour(piece);
//				}
//				else {
//					//If invalid move, do nothing
//					doNothing();
//				}
//			}else {
//				//An empty square has been clicked on
//				if(isValidMove(coord)) {
//					movePiece(coord);
//				}else {
//					doNothing();
//				}
//			}
//			//An empty square has been clicked on
//			
//		}else {
//			if(checkCoordForPiece(currentPlayer, coord)) {
//				if(compareCoords(coord, selectedPiece.getCoord())) {
//					selectPiece(selectedPiece);
//				}else {
//					
//				} 
//			}else {
//				clickedOnEmptySquare(coord);
//			}
//		}
	}

	private Piece getPieceClickedOn(Player player, Coord coord) {
		Piece thisPiece = null;
		for(Piece piece: player.pieces) {
			if (compareCoords(piece.getCoord(), coord)){
				print("Piece: " + piece.getName() + " Coord: (" + piece.getCoord().getX() + ", " + piece.getCoord().getY()+ ")");
				thisPiece = piece;
			}
		}
		return thisPiece;
	}

	private boolean checkPieces(Player player, Coord coord) {
		for(Piece piece: player.pieces) {
			if(compareCoords(piece.getCoord(), coord) && !piece.equals(selectedPiece)) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidMove(Coord coord) {
		for(Coord move: validMoves) {
			return compareCoords(move, coord);
		}
		return false;
	}

	/**
	 * Determine if a piece has been clicked on
	 * @param player
	 * @param coord
	 * @return
	 */
	private boolean checkCoordForPiece(Player player, Coord coord) {
		for(Piece piece: player.pieces) {
			if (compareCoords(piece.getCoord(), coord) && piece != selectedPiece){
				//print("Piece: " + piece.getName() + " Coord: (" + piece.getCoord().getX() + ", " + piece.getCoord().getY()+ ")");
				//selectedPiece = piece;
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
		print("Move Piece...");
		selectedPiece.setX(coord.getX());
		selectedPiece.setY(coord.getY());
		changeTurns();
	}
	
	/**
	 * Set piece to the one clicked on
	 * @param piece
	 */
	public void selectPiece(Piece piece) {
		print("Selected Piece " + piece.getName() + "...");
		selectedPiece = piece;
		pieceCurrentlySelected = true;	//Do this when the piece is clicked on
		calculateValidMoves(piece);
	}
	
	/**
	 * Action when clicking on current piece
	 */
	public void clickedOnSelf() {
		print("Clicked on Self...");
		unselectPiece();
	}
	
	/**
	 * Action when clicking on empty square
	 */
	public void clickedOnEmptySquare(Coord coord) {
		print("Clicked on Empty Square...");
		if(pieceCurrentlySelected) {
			if(validSquareSelection(coord)) {
				movePiece(coord);
			}else {
				//Maybe do nothing here
				//doNothing();
				unselectPiece();
			}
		}else {
			doNothing();
		}
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
	public void clickedOnSameColour(Piece piece) {
		print("Clicked on Same Colour...");
		if(validPieceSelection(piece)) {
			if(pieceCurrentlySelected) {
				changePiece(piece);
			}else {
				selectPiece(piece);
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
	public void clickedOnOppositeColour(Piece piece) {
		print("Clicked on Opposite Colour...");
		if(pieceCurrentlySelected && validPieceCapture(piece)) {
			takePiece(piece);
		}else {
			doNothing();
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
		print("Taking Piece...");
		Coord current = piece.getCoord();
		sendTakenPieceOffBoard(piece);
		movePiece(current);
	}
	
	/**
	 * Figure out where in the graveyard of that colour to put the taken piece
	 * @param piece
	 */
	private void sendTakenPieceOffBoard(Piece piece) {
		// TODO Sort this out!
		//movePiece(piece)
		print("Send Piece Off Board...");
		piece.setX(0);
		piece.setY(0);
	}

	/**
	 * Action to change to another piece when one is already selected
	 * @param piece
	 */
	public void changePiece(Piece piece) {
		print("Change Piece...");
		unselectPiece();
		selectPiece(piece);
	}
	
	/**
	 * This is for when the click leaves the current state unchanged
	 */
	public void doNothing() {
		print("Do Nothing...");
		return;
	}
	
	/**
	 * Set selectedPiece to false and clear the valid squares
	 */
	public void unselectPiece() {
		print("Unselect Piece...");
		pieceCurrentlySelected = false;
	}
}
