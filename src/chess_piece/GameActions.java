package chess_piece;

import board.Coord;

public interface GameActions {

	public void takePiece(Piece yieldingPiece);
	
	public void pieceClicked(Piece actionPiece);
	
	public void movePiece(Piece movingPiece, Coord destination);
	
}
