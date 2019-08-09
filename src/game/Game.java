package game;

import board.*;
import enums.*;

public class Game{

	Controller controller;
	Board board;
	Player player1, player2;
	private static int defaultMinutes = 20, defaultSeconds = 00;
	
	/**
	 * Game with no clocks
	 */
	public Game() {
		this(false);
		player1 = new Player(Colour.WHITE);
		player2 = new Player(Colour.BLACK);
	}
	
	/**
	 * Game with both default clocks (or both without)
	 * @param clocks
	 */
	public Game(boolean clocks) {
		this(clocks, clocks);
		player1 = new Player(Colour.WHITE, clocks);
		player2 = new Player(Colour.BLACK, clocks);
	}
	
	/**
	 * 	Game with up to two default clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(boolean clock1, boolean clock2) {
		this(defaultMinutes, defaultSeconds, defaultMinutes, defaultSeconds);
		player1 = new Player(Colour.WHITE, clock1);
		player2 = new Player(Colour.BLACK, clock2);
	}
	
	/**
	 * Game with two custom clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(int clock1Minutes, int clock1Seconds, int clock2Minutes, int clock2Seconds) {
		player1 = new Player(Colour.WHITE, clock1Minutes, clock1Seconds);
		player2 = new Player(Colour.BLACK, clock2Minutes, clock2Seconds);
		controller = new Controller(player1, player2);
		board = new Board(controller);
	}
}
