package board;

import chess_piece.*;
import enums.*;

public class PieceFactory {
	
	public Piece assignPieces(Type type, Colour colour, int number) {
		if(type == Type.KING) {
			return (new King(type, colour, number));
		}else if(type == Type.QUEEN) {
			return (new Queen(type, colour, number));
		}else if(type == Type.BISHOP) {
			return (new Bishop(type, colour, number));
		}else if(type == Type.KNIGHT) {
			return (new Knight(type, colour, number));
		}else if(type == Type.ROOK) {
			return (new Rook(type, colour, number));
		}else {	
			return (new Pawn(type, colour, number));
		}
	}
}
