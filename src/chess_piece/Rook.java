package chess_piece;

import board.Board;
import enums.Colour;
import enums.Type;

public class Rook extends Piece {

	public Rook(Board board, Type type, Colour colour, int number) {
		super(board, type, colour, number);
	}
	
	@Override
	public boolean thisPieceCondition(int i, int j, int k) {
		return (Math.abs(i) != Math.abs(j));
	}
}