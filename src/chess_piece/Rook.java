package chess_piece;

import enums.*;

public class Rook extends Piece {

	public Rook(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	
	@Override
	public boolean movementCondition(int xDirection, int yDirection, int magnitude) {
		return (Math.abs(xDirection) != Math.abs(yDirection));
	}
}