package chess_piece;

import enums.*;

public class Knight extends Piece{
	
	public Knight(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation="N";
		setMagnitudeMove(2);
		setMaximumMove(1);
	}
	
	@Override
	public boolean movementCondition(int xDirection, int yDirection, int magnitude) {
		return (Math.abs(yDirection) != Math.abs(xDirection) && !(xDirection == 0 || yDirection == 0));
	}
}