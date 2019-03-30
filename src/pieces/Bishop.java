package pieces;

import enums.Colour;
import enums.Type;
import board.Coord;

public class Bishop extends Piece{
	
	public Bishop(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	
	@Override
	public void calculateValidMoves() {
		super.setUpBasicMoves(1, 7);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return (Math.abs(i) == Math.abs(j) && !(i == 0 && j == 0));
	}
	
	@Override
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return new Coord(current.getX() + i * k, current.getY() + j * k);
	}
}
