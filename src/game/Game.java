package game;

import board.*;
import enums.*;

public class Game{

	Controller controller;
	Board board;
	Player player1, player2;
	
	/**
	 * Game with no clocks
	 */
	public Game() {
		player1 = new Player(Colour.WHITE);
		player2 = new Player(Colour.BLACK);
		setBoardController(player1, player2);
	}
	
	/**
	 * Game with both default clocks (or both without)
	 * @param clocks
	 */
	public Game(boolean clocks) {
		player1 = new Player(Colour.WHITE, clocks);
		player2 = new Player(Colour.BLACK, clocks);
		setBoardController(player1, player2);
	}
	
	/**
	 * 	Game with up to two default clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(boolean clock1, boolean clock2) {
		player1 = new Player(Colour.WHITE, clock1);
		player2 = new Player(Colour.BLACK, clock2);
		setBoardController(player1, player2);
	}
	
	/**
	 * Game with two custom clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(int clock1Minutes, int clock1Seconds, int clock2Minutes, int clock2Seconds) {
		player1 = new Player(Colour.WHITE, clock1Minutes, clock1Seconds);
		player2 = new Player(Colour.BLACK, clock2Minutes, clock2Seconds);
		setBoardController(player1, player2);
	}
	
	/**
	 * Set up the Controller and the Board
	 * @param player1
	 * @param player2
	 */
	public void setBoardController(Player player1, Player player2) {
		controller = new Controller(player1, player2);
		board = new Board(controller);
	}
}
