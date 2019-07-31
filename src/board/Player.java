package board;

import java.util.*;
import chess_piece.*;
import enums.*;

public class Player {

	PieceFactory factory = new PieceFactory();
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	Colour colour;
	ChessClock clock;
	
	public Player(Colour colour) {
		this(colour, false);
	}
	
	public Player(Colour colour, boolean defaultClock) {
		this.colour = colour;
		if(defaultClock) {
			clock = new ChessClock();
		}
	}
	
	public Player(Colour colour, int minutes, int seconds) {
		this.colour = colour;
		clock = new ChessClock(minutes, seconds);
	}
	
	public void initialise() {
		for(Type type : Type.values()){
			for(int number = 1; number <= type.getQuantity(); number++) {
				Piece piece = factory.assignPieces(type, colour, number);
				pieces.add(piece);
			}
		}
	}
	
	public ChessClock getClock() {
		return clock;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
}
