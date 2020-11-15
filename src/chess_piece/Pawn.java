package chess_piece;

import board.*;
import enums.*;

public class Pawn extends Piece{
	
	public Pawn(Type type, Colour colour, int number) {
		super(type, colour, number);
		notation = "";
		setMagnitudeMove(1);
		setMaximumMove(1);
	}
	
	@Override
	public void getInitialCoords() {
		this.setY((isWhite ? Utils.SEVENTH_ROW : Utils.SECOND_ROW) * gridsize + gridsize/4);
		this.setX((type.getPositionX()+number)*gridsize + gridsize/4);
	}
		
	//TODO: this needs careful consideration
//	@Override
//	public boolean movementCondition(int xDirection, int yDirection, int magnitude) {
//		return (Math.abs(yDirection) != Math.abs(xDirection) && !(xDirection == 0 || yDirection == 0));
//	}
}
