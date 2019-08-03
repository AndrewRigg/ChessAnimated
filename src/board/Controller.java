package board;

public class Controller {
	
	Board board;
	Player player1, player2;
	
	public Controller(Board board, Player player1, Player player2) {
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	private void changeTurns() {
		player1.setTurn(!player1.isTurn());
		player2.setTurn(!player2.isTurn());
		player1.getClock().setRunning(player1.isTurn());
		player2.getClock().setRunning(player2.isTurn());
	}
}
