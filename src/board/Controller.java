package board;

import chess_piece.Piece;
import enums.PlayerNumber;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class Controller {
	
	Board board;
	Player player1, player2;
	int gridsize = Literals.GRIDSIZE;
	
	public Controller(Board board, Player player1, Player player2) {
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		setPlayer(player1);
		setPlayer(player2);
	}
	
	private void changeTurns() {
		changePlayerTurn(player1);
		changePlayerTurn(player2);
	}
	
	private void changePlayerTurn(Player player) {
		player.setTurn(!player.isTurn());
		player.getClock().setRunning(player.isTurn());
	}
	
	public void setPlayer(Player player) {
		player.initialise();
		addPieces(player);
		if(player.clockActive) {
			addChessClock(player);
			if(player.player == PlayerNumber.PlayerOne) {
				player.setTurn(true);
				player.getClock().setRunning(true);
			}
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
	
}
