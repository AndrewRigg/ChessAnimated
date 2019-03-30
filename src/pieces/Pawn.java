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
	}
	
	@Override
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.SEVENTH_ROW : Literals.SECOND_ROW) * gridsize + gridsize/4);
		this.setX((type.getPositionX()+pieceNumber)*gridsize + gridsize/4);
	}

	@Override
	public void calculateValidMoves() {
		super.setUpBasicMoves(0, state == State.INITIAL ? 2 : 1);
		//setUpEnPassantMoves(col, row);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		//return board.getSquare(current.getX() + (isWhite ? -k : k), current.getY()).thisSquareEmpty;
		return true;
	}
	
	@Override
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return new Coord(current.getX(), current.getY() + (isWhite ? -k : k));
	}
}
