package pieces;

import enums.Colour;
import enums.Type;

public class Knight extends Piece{
	
	public Knight(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation="N";
	}
}