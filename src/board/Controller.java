package board;

import java.util.ArrayList;

import chess_piece.*;
import enums.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Controller {
	
	Board board;
	Player player1, player2, currentPlayer;
	ArrayList<Circle> validMoveMarkers;
	ArrayList<Coord> validMoves;
	Piece selectedPiece;
	boolean pieceCurrentlySelected;
	int gridsize = Literals.GRIDSIZE;
	
	public Controller(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		player1.setPlayer(PlayerNumber.PlayerOne);
		player2.setPlayer(PlayerNumber.PlayerTwo);
		player1.setTurn(true);
		if(player1.clockActive) {
			player1.getClock().setRunning(true);
		}
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
		}
	}
		
	public Player getCurrent() {
		return currentPlayer;
	}

	public void setCurrent(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Remove the markers from the board and clear the arrays 
	 * of valid coordinates and markers
	 */
	public void clearValidMoves(){
		for(Circle circle: validMoveMarkers) {
			board.group.getChildren().remove(circle);
		}
		validMoveMarkers.clear();
		validMoves.clear();
	}
	
	/**
	 * Calculate the valid moves which can be made by the currently selected piece
	 * This will generate green circles as a visual indication of valid moves
	 * @param piece
	 */
	private void calculateValidMoves(Piece piece) {
		clearValidMoves();
		for (int i = piece.getMagnitudeMove()*(-1); i <= piece.getMagnitudeMove(); i++) {
			for (int j = piece.getMagnitudeMove()*(-1); j <= piece.getMagnitudeMove(); j++) {
				for (int k = 1; k <= piece.getMaximumMove(); k++) {
					if (piece.movementCondition(i, j, k) && !(i == 0 && j == 0)) {
						Coord coord = piece.validMoveCoord(i, j, k);
						if (coord.onGrid() && !piece.getValidMoves().contains(coord)) {
							Circle circle =  new Circle(gridsize/3.5);
							circle.setFill(Color.GREENYELLOW);
							circle.setCenterX((piece.getX() + i * k) * gridsize + gridsize/2);
							circle.setCenterY((piece.getY() + j * k) * gridsize + gridsize/2);
							validMoveMarkers.add(circle);
							validMoves.add(coord);
						}
					}
				}
			}
		}
	}
	
	public void determineClickType(Coord coord) {
		isPieceClickedOn(currentPlayer, coord);
		if(pieceCurrentlySelected) {
			if(coord == selectedPiece.getCurrent()) {
				clickedOnSelf();
			}
//			else if(){
//				clickedOnSameColour(piece);
//			} else {
//				clickedOnOppositeColour(piece);
//			}
		}else {
			clickedOnEmptySquare(coord);
		}
	}
	
	/**
	 * Determine if a piece has been clicked on
	 * @param player
	 * @param coord
	 */
	private void isPieceClickedOn(Player player, Coord coord) {
		for(Piece piece: player.pieces) {
			if(piece.getCurrent() == coord) {
				
			}
		}
	}

	/**
	 * Action to move piece to an empty square
	 * @param coord
	 */
	public void movePiece(Coord coord) {
		selectedPiece.setX(coord.getX());
		selectedPiece.setY(coord.getY());
	}
	
	/**
	 * Set piece to the one clicked on
	 * @param piece
	 */
	public void selectPiece(Piece piece) {
		selectedPiece = piece;
		//pieceCurrentlySelected = true;	//Do this when the piece is clicked on
		calculateValidMoves(piece);
	}
	
	/**
	 * Action when clicking on current piece
	 */
	public void clickedOnSelf() {
		unselectPiece();
	}
	
	/**
	 * Action when clicking on empty square
	 */
	public void clickedOnEmptySquare(Coord coord) {
		if(pieceCurrentlySelected) {
			if(validSquareSelection(coord)) {
				movePiece(coord);
			}else {
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
			if(piece.getCurrent().getX() == coord.getX() && piece.getCurrent().getY() == coord.getY()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Draw the markers for valid moves to show where the piece can move including 
	 * empty square coordinates and coordinates of pieces which may be captured
	 */
	public void drawCircles() {
		for(Circle circle: validMoveMarkers) {
			board.group.getChildren().add(circle);
		}
	}

	/**
	 * Action to send taken piece to the graveyard (off grid) and move the current
	 * piece into its position
	 * @param piece
	 */
	public void takePiece(Piece piece) {
		Coord current = piece.getCurrent();
		sendTakenPieceOffBoard(piece);
		movePiece(current);
	}
	
	/**
	 * Figure out where in the graveyard of that colour to put the taken piece
	 * @param piece
	 */
	private void sendTakenPieceOffBoard(Piece piece) {
		// TODO Sort this out!
		piece.setX(0);
		piece.setY(0);
	}

	/**
	 * Action to change to another piece when one is already selected
	 * @param piece
	 */
	public void changePiece(Piece piece) {
		unselectPiece();
		selectPiece(piece);
	}
	
	/**
	 * This is for when the click leaves the current state unchanged
	 */
	public void doNothing() {
		return;
	}
	
	/**
	 * Set selectedPiece to false and clear the valid squares
	 */
	public void unselectPiece() {
		pieceCurrentlySelected = false;
		clearValidMoves();
	}
}
