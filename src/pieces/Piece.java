package pieces;

import board.Literals;
import enums.Colour;
import enums.State;
import enums.Type;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import board.Coord;

public class Piece extends Rectangle{

	public static int gridsize = Literals.GRIDSIZE;
	public String name, notation;
	public Image image;
	public Colour colour;
	public State state;
	public Type type;
	public int pieceNumber, yOffset;
	public boolean thisPieceSelected, isWhite;
//	public Coord current;
//	public Stack<Coord> previousPositions, futurePositions;
	
	public Piece(Type type, Colour colour, int pieceNumber) {
		super(gridsize/2, gridsize/2);
		this.isWhite = (colour == Colour.WHITE);
		this.type = type; 
		this.colour = colour; 
		this.pieceNumber = pieceNumber; 
		this.state = State.INITIAL; 
		this.notation = type.toString().substring(0, 1); 
		this.name = colour.toString().toLowerCase() + "_" + type.toString().toLowerCase();
		this.image = new Image("res/"+name+".png");
		getInitialCoords();
		this.yOffset = 0;
		this.thisPieceSelected = false;
		this.setFill(new ImagePattern(image));
//		this.previousPositions = new Stack<Coord>();
//		this.futurePositions = new Stack<Coord>();
	}	
	
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.EIGHTH_ROW : Literals.FIRST_ROW) * gridsize + gridsize/4);
		this.setX((pieceNumber == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1)*gridsize + gridsize/4);
	}
	
//	public void setUpBasicMoves(int mag, int max) {
//		for (int i = mag*(-1); i <= mag; i++) {
//			direction: 
//			for (int j = mag*(-1); j <= mag; j++) {
//				for (int k = 1; k <= max; k++) {
//					if (thisPieceCondition(i, j, k)) {
//						Coord coord = thisPieceConditionCoord(i, j, k);
//						if (coord.onGrid() && !validMoves.contains(coord)) {
//							if (!board.getSquare(coord).thisSquareEmpty) {
//								if (board.getSquare(coord).occupyingPiece.colour != this.colour) {
//									validMoves.add(coord);
//								}
//								continue direction;
//							} else {
//								validMoves.add(coord);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//	
	public boolean thisPieceCondition(int i, int j, int k) {
		return false;
	}
	
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return null;
	}
	
	public void calculateValidMoves(int col, int row) {}
	
}
