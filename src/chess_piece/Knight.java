package chess_piece;

import board.Coord;
import enums.*;

public class Knight extends Piece{
	
	public Knight(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation="N";
		setMagnitudeMove(2);
		setMaximumMove(1);
	}
	
	public Knight(Type type, Colour colour, int number, Coord coord) {
		super(type, colour, number, coord);
		notation="N";
		setMagnitudeMove(2);
		setMaximumMove(1);
	}
	
	@Override
	public boolean movementCondition(int xDirection, int yDirection, int magnitude) {
		return ((Math.abs(yDirection) != Math.abs(xDirection)) && !(xDirection == 0 || yDirection == 0));
	}
}