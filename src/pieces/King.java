package pieces;

import enums.Colour;
import enums.Type;
import board.Coord;

public class King extends Piece{
	
	public King(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	@Override
	public void calculateValidMoves() {
		super.setUpBasicMoves(1, 1);
		//getCastlingMoves(col, row);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return !(i == 0 && j == 0);
	}
	
	@Override
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return new Coord(current.getX() + i, current.getY() + j);
	}
	
}