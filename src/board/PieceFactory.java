package board;

import chess_piece.*;
import enums.*;

public class PieceFactory {
	
	public PieceFactory() {}
	
	public Piece assignPieces(Board board, Type type, Colour colour, int number) {
		Piece piece = new Piece(board, type, colour, number);
		if(type == Type.BISHOP) {
			piece = new Bishop(board, type, colour, number);
		}else if(type == Type.KING) {
			piece = new King(board, type, colour, number);
		}else if(type == Type.KNIGHT) {
			piece = new Knight(board, type, colour, number);
		}else if(type == Type.PAWN) {
			piece = new Pawn(board, type, colour, number);
		}else if(type == Type.QUEEN) {
			piece = new Queen(board, type, colour, number);
		}else if(type == Type.ROOK) {
			piece = new Rook(board, type, colour, number);
		}
		return piece;
	}
}
