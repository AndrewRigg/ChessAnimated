package pieces;

import enums.Colour;
import enums.Type;

public class Bishop extends Piece{
	
	public Bishop(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return (Math.abs(i) == Math.abs(j));
	}
}
