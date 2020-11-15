package game;

import board.*;
import enums.*;

public class Game{
	
	Board board;
	
	/**
	 * Game with no clocks
	 */
	public Game() {
		setBoardController(new Player(Colour.WHITE), new Player(Colour.BLACK));
	}
	
	/**
	 * Game with both default clocks (or both without)
	 * @param clocks
	 */
	public Game(boolean clocks) {
		setBoardController(new Player(Colour.WHITE, clocks), new Player(Colour.BLACK, clocks));
	}
	
	/**
	 * 	Game with up to two default clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(boolean clock1, boolean clock2) {
		setBoardController(new Player(Colour.WHITE, clock1), new Player(Colour.BLACK, clock2));
	}
	
	/**
	 * Game with two custom clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(int clock1Minutes, int clock1Seconds, int clock2Minutes, int clock2Seconds) {
		setBoardController(new Player(Colour.WHITE, clock1Minutes, clock1Seconds), new Player(Colour.BLACK, clock2Minutes, clock2Seconds));
	}
	
	/**
	 * Set up the Controller and the Board
	 * @param player1
	 * @param player2
	 */
	public void setBoardController(Player player1, Player player2) {
		board = new Board(new Controller(player1, player2));
	}
}
