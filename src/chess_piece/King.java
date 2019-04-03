package chess_piece;

import board.Board;
import enums.Colour;
import enums.Type;

public class King extends Piece{
	
	public King(Board board, Type type, Colour colour, int number) {
		super(board, type, colour, number);
		maximumMove = 1;
	}
}