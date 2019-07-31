package board;

import chess_piece.*;
import enums.*;

public class PieceFactory {
	
	public Piece assignPieces(Type type, Colour colour, int number) {
		Piece piece = new Piece(type, colour, number);
		if(type == Type.BISHOP) {
			piece = new Bishop(type, colour, number);
		}else if(type == Type.KING) {
			piece = new King(type, colour, number);
		}else if(type == Type.KNIGHT) {
			piece = new Knight(type, colour, number);
		}else if(type == Type.PAWN) {
			piece = new Pawn(type, colour, number);
		}else if(type == Type.QUEEN) {
			piece = new Queen(type, colour, number);
		}else if(type == Type.ROOK) {
			piece = new Rook(type, colour, number);
		}
		return piece;
	}
}
