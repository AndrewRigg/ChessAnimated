package board;

import java.util.ArrayList;

import chess_piece.*;
import enums.*;

public class Player {

	PieceFactory factory = new PieceFactory();
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	Colour colour;
	
	public Player(Colour colour) {
		this.colour = colour;
	}
	
	public void initialise() {
		for(Type type : Type.values()){
			for(int number = 1; number <= type.getQuantity(); number++) {
				Piece piece = factory.assignPieces(type, colour, number);
				pieces.add(piece);
			}
		}
	}	
}
