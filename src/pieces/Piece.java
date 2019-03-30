package pieces;

import board.Literals;
import enums.Colour;
import enums.State;
import enums.Type;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import board.Coord;

public class Piece extends Rectangle{

	public static int gridsize = Literals.GRIDSIZE;
	public String name, notation;
	public Image image;
	public Colour colour;
	public State state;
	public Type type;
	public int pieceNumber, yOffset;
	public boolean thisPieceSelected, isWhite, thisPieceCondition;
	public ArrayList<Coord> validMoves;
	public Coord current;
	
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
		this.current = getInitialCoords(type, colour, pieceNumber);
		getInitialCoords();
		this.yOffset = 0;
		this.thisPieceSelected = false;
		this.validMoves = new ArrayList<Coord>();
		this.setFill(new ImagePattern(image));
//		this.previousPositions = new Stack<Coord>();
//		this.futurePositions = new Stack<Coord>();
	}	
	
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.EIGHTH_ROW : Literals.FIRST_ROW) * gridsize + gridsize/4);
		this.setX((pieceNumber == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1)*gridsize + gridsize/4);
	}
	
	
	public Coord getInitialCoords(Type type, Colour colour, int number) {
		Coord initial = new Coord(0, 0);
		initial.setY(isWhite ? Literals.EIGHTH_ROW : Literals.FIRST_ROW);
		initial.setX(number == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1);
		return initial;
	}
	
	public void setUpBasicMoves(int mag, int max) {
		for (int i = mag*(-1); i <= mag; i++) {
			for (int j = mag*(-1); j <= mag; j++) {
				for (int k = 1; k <= max; k++) {
					if (thisPieceCondition(i, j, k)) {
						Coord coord = thisPieceConditionCoord(i, j, k);
						if (coord.onGrid() && !validMoves.contains(coord)) {
							validMoves.add(coord);
						}
					}
				}
			}
		}
	}
	
	public void highlightSquares(Group group) {
		for(Coord coord: validMoves) {
			Circle circle =  new Circle(Literals.GRIDSIZE/3.5);
			circle.setFill(Color.GREENYELLOW);
			circle.setCenterX(coord.getX() * gridsize + gridsize/2);
			circle.setCenterY(coord.getY() * gridsize + gridsize/2);
			group.getChildren().add(circle);
		}
	}
	
	public void removeHighlightedSquares(Group group) {
		for(Node circle: group.getChildren()) {
			group.getChildren().removeAll();
		}
	}
	
	public boolean thisPieceCondition(int i, int j, int k) {
		return false;
	}
	
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return null;
	}

	public void calculateValidMoves() {	}
	
}
