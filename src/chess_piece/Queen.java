package chess_piece;

import board.Board;
import enums.Colour;
import enums.Type;

public class Queen extends Piece{
	
	public Queen(Board board, Type type, Colour colour, int number) {
		super(board, type, colour, number);
	}
}