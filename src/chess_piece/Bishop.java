package chess_piece;

import enums.*;

public class Bishop extends Piece{
	
	public Bishop(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	
	@Override
	public boolean movementCondition(int xDirection, int yDirection, int k) {
		return (Math.abs(xDirection) == Math.abs(yDirection));
	}
}
