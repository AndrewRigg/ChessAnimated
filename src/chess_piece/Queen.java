package chess_piece;

import board.Coord;
import enums.*;

public class Queen extends Piece{
	
	public Queen(Type type, Colour colour, int number) {
		super(type, colour, number);
	}
	
	public Queen(Type type, Colour colour, int number, Coord coord) {
		super(type, colour, number, coord);
	}
}