package board;

import java.util.*;
import chess_piece.*;
import enums.*;

public class Player {

	PieceFactory factory = new PieceFactory();
	public ArrayList<Piece> pieces;
	int capturedPieces = 0;
	Colour colour;
	boolean turn, clockActive = true;
	private PlayerTimer clock;
	int clockPosition, gridsize = Utils.GRIDSIZE;
	public Coord [] takenZone = new Coord[18];
	
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
		this(colour, Utils.DEFAULT_MINUTES, Utils.DEFAULT_SECONDS);
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
		setTakenZones();
		if(clockActive) {
			clock = new PlayerTimer(minutes, seconds, colour == Colour.WHITE);
		}
		initialise();
	}
	
	private void setTakenZones() {
		int horizontal, vertical;
		for(horizontal = 1; horizontal <= 10; horizontal++) {
			takenZone[horizontal - 1] = new Coord((colour == Colour.WHITE) ? (10 - horizontal) : horizontal+1, (colour == Colour.WHITE) ? 11 : 0);
		}
		for(vertical = 2; vertical <= 9; vertical++) {
			takenZone[horizontal + vertical - 3] = new Coord((colour == Colour.WHITE) ? 0 : 11, (colour == Colour.WHITE) ? (12 - vertical) : vertical-1 );
		}
	}

	public static final void print(String str) {
		Utils.print(str, Utils.PLAYER_DEBUG);
	}
	
	private void setClockPosition() {
		clockPosition = (colour == Colour.WHITE) ? gridsize + 10 : 10 * gridsize + 10;
	}
		
	public void initialise() {
		for(Type type : Type.values()){
			for(int number = 1; number <= type.getQuantity(); number++) {
				Piece piece = factory.assignPieces(type, colour, number);
//				piece.calculateValidMoves(this, );
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
	
	public PlayerTimer getClock() {
		return clock;
	}
	
	public void setClock(PlayerTimer clock) {
		this.clock = clock;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}

	public void addTakenPiece() {
		capturedPieces++;
	}
	
	public int getTakenPieces() {
		return capturedPieces;
	}

	public Coord getTakenZone(int takenPieces) {
		return takenZone[takenPieces];
	}
}
