package board;

import java.util.ArrayList;

import chess_piece.Piece;
import enums.PlayerNumber;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class Controller {
	
	Board board;
	Player player1, player2;
	PlayerNumber currentPlayer;
	ArrayList<Circle> validMoveMarkers;
	ArrayList<Coord> validMoves;
	Piece selectedPiece;
	boolean pieceCurrentlySelected;
	int gridsize = Literals.GRIDSIZE;
	
	public Controller(Board board, Player player1, Player player2) {
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		setPlayer(player1);
		setPlayer(player2);
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
			setCurrentPlayer(player.getPlayer());
		}
	}
	
	public void setPlayer(Player player) {
		player.initialise();
		addPieces(player);
		if(player.clockActive) {
			addChessClock(player);
		}
	}

	public void addPieces(Player player) {
		for(Piece piece : player.pieces) {
			board.group.getChildren().add(piece);
		}
	}
	
	public void addChessClock(Player player) {
		Label clockLabel = player.getClock().getLabel();
		clockLabel.setTextAlignment(TextAlignment.CENTER);
		clockLabel.setTranslateX(player.clockPosition);
		clockLabel.setTranslateY(board.gridsize);
		board.group.getChildren().add(clockLabel);
	}
	
	public void movePiece(Piece piece) {
		//piece.setX();
		//piece.setY();
	}
	
	public void selectPiece(Piece piece) {
		selectedPiece = piece;
		calculateValidMoves(piece);
	}

	public void clearValidMoves(){
		for(Circle circle: validMoveMarkers) {
			board.group.getChildren().remove(circle);
		}
		validMoves.clear();
	}
	
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

	public void takePiece(Piece piece) {
		
	}
	
	public void changePiece(Piece piece) {
		unselectPiece();
		selectPiece(piece);
	}
	
	public void nothingChanged() {
		
	}
	
	public void unselectPiece() {
		
	}
	
	public void clickedOnOppositeColour() {
		
	}
	
	public void clickedOnSameColour(Piece piece) {
		if(validPieceSelection(piece)) {
			if(pieceCurrentlySelected) {
				changePiece(piece);
			}else {
				selectPiece(piece);
			}
		}else {
			nothingChanged();
		}
	}
	
	private boolean validPieceSelection(Piece piece) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clickedOnSelf() {
		pieceCurrentlySelected = false;
		unselectPiece();
	}
	
	public void clickedOnEmptySquare() {
		
	}
	
	public PlayerNumber getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(PlayerNumber player) {
		currentPlayer = player;
	}
}
