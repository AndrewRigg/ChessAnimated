package chess_piece;

import board.Coord;
import enums.*;

public class Rook extends Piece {

	public Rook(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	
	public Rook(Type type, Colour colour, int number, Coord coord) {
		super(type, colour, number, coord);
	}
	
	@Override
	public boolean movementCondition(int xDirection, int yDirection, int magnitude) {
		return (Math.abs(xDirection) != Math.abs(yDirection));
	}
}