package pieces;

import board.Piece;
import enums.Colour;
import enums.Type;
import board.Literals;

public class Pawn extends Piece{
	
	public Pawn(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation = "";
	}
	
	@Override
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.SEVENTH_ROW : Literals.SECOND_ROW) * gridsize + gridsize/4);
		this.setX((type.getPositionX()+pieceNumber)*gridsize + gridsize/4);
	}
}
