package pieces;

import enums.Colour;
import enums.State;
import enums.Type;
import board.Coord;
import board.Literals;

public class Pawn extends Piece{
	
	public Pawn(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation = "";
		magnitudeMove = (state == State.INITIAL ? 2 : 1);
		maximumMove = 1;
	}
	
	@Override
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.SEVENTH_ROW : Literals.SECOND_ROW) * gridsize + gridsize/4);
		this.setX((type.getPositionX()+pieceNumber)*gridsize + gridsize/4);
	}
	
	@Override
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		System.out.println("x: " + current.getX() + " y: " + current.getY());
		return new Coord(current.getY(), current.getX()+ (isWhite ? -k : k));
	}
}
