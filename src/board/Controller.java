package board;

import chess_piece.Piece;
import enums.PlayerNumber;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class Controller {
	
	Board board;
	Player player1, player2;
	PlayerNumber currentPlayer;
	Piece selectedPiece;
	boolean pieceCurrentlySelected;
	int gridsize = Literals.GRIDSIZE;
	
	public Controller(Board board, Player player1, Player player2) {
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		setPlayer(player1);
		setPlayer(player2);
		player1.setThisPlayer(PlayerNumber.PlayerOne);
		player2.setThisPlayer(PlayerNumber.PlayerTwo);
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
			setCurrentPlayer(player.getThisPlayer());
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
		
	}
	
	public void selectPiece(Piece piece) {
		selectedPiece = piece;
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
