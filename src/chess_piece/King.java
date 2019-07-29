package chess_piece;

import enums.*;

public class King extends Piece{
	
	public King(Type type, Colour colour, int number) {
		super(type, colour, number);
		setMaximumMove(1);
	}
}