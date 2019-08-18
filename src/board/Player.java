package board;

import java.util.*;
import chess_piece.*;
import enums.*;

public class Player {

	PieceFactory factory = new PieceFactory();
	ArrayList<Piece> pieces;
	Colour colour;
	boolean turn, clockActive = true;
	private ChessClock clock;
	private static int defaultMinutes = 20, defaultSeconds = 00;
	int clockPosition, gridsize = Literals.GRIDSIZE;
	
	/**
	 * Default constructor
	 * @param colour
	 */
	public Player(Colour colour) {
		this(colour, false);
	}
	
	/**
	 * Player with an active clock (or inactive)
	 * @param colour
	 * @param clockActive
	 */
	public Player(Colour colour, boolean clockActive) {
		this(colour, defaultMinutes, defaultSeconds);
		this.clockActive = clockActive;
	}
	
	/**
	 * Player with a custom clock
	 * @param colour
	 * @param minutes
	 * @param seconds
	 */
	public Player(Colour colour, int minutes, int seconds) {
		this.colour = colour;
		setClockPosition();
		pieces = new ArrayList<Piece>();
		if(clockActive) {
			clock = new ChessClock(minutes, seconds, colour == Colour.WHITE);
		}
		initialise();
	}

	public static final void print(String str) {
		Literals.print(str, Literals.PLAYER_DEBUG);
	}
	
	private void setClockPosition() {
		clockPosition = (colour == Colour.WHITE) ? gridsize + 10 : 10 * gridsize + 10;
	}
		
	public void initialise() {
		for(Type type : Type.values()){
			for(int number = 1; number <= type.getQuantity(); number++) {
				Piece piece = factory.assignPieces(type, colour, number);
				piece.calculateValidMoves();
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
	
	public void setClock(ChessClock clock) {
		this.clock = clock;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
}
