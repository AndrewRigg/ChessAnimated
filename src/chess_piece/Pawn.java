package chess_piece;

import board.*;
import enums.*;

public class Pawn extends Piece{
	
	public Pawn(Board board, Type type, Colour colour, int number) {
		super(board, type, colour, number);
		notation = "";
		magnitudeMove = 1;
		maximumMove = 1;
	}
	
	@Override
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.SEVENTH_ROW : Literals.SECOND_ROW) * gridsize + gridsize/4);
		this.setX((type.getPositionX()+pieceNumber)*gridsize + gridsize/4);
	}
	
	//TODO: his needs careful condsideration
//	@Override
//	public boolean thisPieceCondition(int i, int j, int k) {
//		return (Math.abs(j) != Math.abs(i) && !(i == 0 || j == 0));
//	}
}
