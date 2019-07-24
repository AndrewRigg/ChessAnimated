package board;

import chess_piece.Piece;
import enums.Colour;
import enums.Type;

public class Player {

	PieceFactory factory = new PieceFactory();
	
	public Player() {
		
	}
	
	public void initialisePieces(Colour colour) {
		for(Type type : Type.values()){
			for(int number = 1; number <= type.getQuantity(); number++) {
				Piece piece = factory.assignPieces(this, type, colour, number);
				pieces.add(piece);
				group.getChildren().add(piece);
			}
		}
	}
	
}
