package board;

import java.util.*;
import chess_piece.*;
import enums.*;

public class Player {

	int gridsize = Literals.GRIDSIZE;
	PieceFactory factory = new PieceFactory();
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	Colour colour;
	private ChessClock clock;
	int clockPosition;
	boolean turn, clockActive = true;
	PlayerNumber player;
	private static int defaultMinutes = 20, defaultSeconds = 00;
	
	/**
	 * Default constructor
	 * @param colour
	 */
	public Player(Colour colour) {
		this(colour, false);
	}
	
	/**
	 * Overrriding constructor #1
	 * @param colour
	 * @param clockActive
	 */
	public Player(Colour colour, boolean clockActive) {
		this(colour, defaultMinutes, defaultSeconds);
		this.clockActive = clockActive;
	}
	
	/**
	 * Overriding constructor #2
	 * @param colour
	 * @param minutes
	 * @param seconds
	 */
	public Player(Colour colour, int minutes, int seconds) {
		this.colour = colour;
		player = setThisPlayer();
		if(clockActive) {
			clock = new ChessClock(minutes, seconds, player.ordinal() == 0);
		}
	}

	private PlayerNumber setThisPlayer() {
		if(colour == Colour.WHITE) {
			clockPosition = gridsize + 10;
			return PlayerNumber.PlayerOne;
		}else {
			clockPosition = 10*gridsize+10;
			return PlayerNumber.PlayerTwo;
		}
	}
		
	public void initialise() {
		for(Type type : Type.values()){
			for(int number = 1; number <= type.getQuantity(); number++) {
				Piece piece = factory.assignPieces(type, colour, number);
				pieces.add(piece);
			}
		}
	}
	
	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public ChessClock getClock() {
		return clock;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
}
