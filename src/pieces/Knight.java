package pieces;

import enums.Colour;
import enums.Type;
import board.Coord;

public class Knight extends Piece{
	
	public Knight(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation="N";
	}
	
	@Override
	public void calculateValidMoves() {
		super.setUpBasicMoves(2, 1);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return (Math.abs(j) != Math.abs(i) && i != 0 && j != 0);
	}
	
	@Override
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return new Coord(current.getX() + j, current.getY() + i);
	}
}