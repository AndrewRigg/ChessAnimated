package chess_piece;

import board.Board;
import enums.Colour;
import enums.Type;

public class Knight extends Piece{
	
	public Knight(Board board, Type type, Colour colour, int number) {
		super(board, type, colour, number);
		notation="N";
		magnitudeMove = 2;
		maximumMove = 1;
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return (Math.abs(j) != Math.abs(i) && !(i == 0 || j == 0));
	}
}