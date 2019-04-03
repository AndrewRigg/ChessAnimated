package chess_piece;

import board.Board;
import enums.*;

public class Bishop extends Piece{
	
	public Bishop(Board board, Type type, Colour colour, int number) {
		super(board, type, colour, number);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return (Math.abs(i) == Math.abs(j));
	}
}
