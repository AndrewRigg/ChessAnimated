package chess_piece;

import java.util.ArrayList;

import board.*;
import enums.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Piece extends Rectangle{

	static int gridsize = Literals.GRIDSIZE;
	public String name, notation;
	Image image;
	Colour colour;
	State state;
	Type type;
	Board board;
	int pieceNumber, yOffset, magnitudeMove, maximumMove;
	public boolean thisPieceSelected, isWhite, thisPieceCondition;
	ArrayList<Coord> validMoves;
	ArrayList<Circle> validMoveCircles;
	Coord current;
	
	
	public Piece(Board board, Type type, Colour colour, int pieceNumber) {
		super(gridsize/2, gridsize/2);
		this.board = board;
		this.type = type; 
		this.colour = colour; 
		this.pieceNumber = pieceNumber; 
		this.isWhite = (colour == Colour.WHITE);
		this.state = State.INITIAL; 
		this.notation = type.toString().substring(0, 1); 
		this.name = colour.toString().toLowerCase() + "_" + type.toString().toLowerCase();
		this.image = new Image("res/"+name+".png");
		this.current = getInitialCoords(type, colour, pieceNumber);
		this.getInitialCoords();
		this.yOffset = 0;
		this.thisPieceSelected = false;
		this.magnitudeMove = 1;
		this.maximumMove = 7;
		this.validMoves = new ArrayList<Coord>();
		this.validMoveCircles = new ArrayList<Circle>();
		this.setFill(new ImagePattern(image));
		this.pieceClicked(this);
	}	

	private void pieceClicked(Piece piece) {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				thisPieceSelected = !thisPieceSelected;
				System.out.println("Piece: Removing highlights");	
				board.removeHighlightedSquares();				
				if(thisPieceSelected) {
					Board.pieceSelected = true;
					System.out.println("Piece: board.pieceSelected: " + Board.pieceSelected);
					setUpBasicMoves();
					board.currentPiece = piece;
					//thisPieceSelected = false;
				}else {
					//board.removeHighlightedSquares();
					//moveOnKeyPressed(piece, (int)(event.getSceneX()/gridsize) * gridsize, (int)(event.getSceneY()/gridsize) * gridsize);
					System.out.println("Unselected " + name);	
					//thisPieceSelected = true;
				}
//				piece.setX((int)(event.getSceneX()/gridsize) * gridsize);		//TRY THIS TYPE OF THING
//				piece.setY((int)(event.getSceneY()/gridsize) * gridsize);
			}
		});
	}

	public ArrayList<Circle> getCircles(){
		return validMoveCircles;
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
	
	public void setUpBasicMoves() {
		validMoves.clear();
		for (int i = magnitudeMove*(-1); i <= magnitudeMove; i++) {
			for (int j = magnitudeMove*(-1); j <= magnitudeMove; j++) {
				for (int k = 1; k <= maximumMove; k++) {
					if (thisPieceCondition(i, j, k) && !(i == 0 && j == 0)) {
						Coord coord = thisPieceConditionCoord(i, j, k);
						if (coord.onGrid() && !validMoves.contains(coord)) {
							validMoves.add(coord);
						}
					}
				}
			}
		}
		highlightSquares();
	}
	
	
	//MOVE THIS TO BOARD CLASS
	public void highlightSquares() {
		//validMoveCircles.clear();
		for(Coord coord: validMoves) {
			Circle circle =  new Circle(gridsize/3.5);
			circle.setFill(Color.GREENYELLOW);
			circle.setCenterX(coord.getX() * gridsize + gridsize/2);
			circle.setCenterY(coord.getY() * gridsize + gridsize/2);
			validMoveCircles.add(circle);
		}
		board.pieceClicked(this);
	}
	
	public boolean thisPieceCondition(int i, int j, int k) {
		return true;
	}
	
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return new Coord(current.getX() + i * k, current.getY() + j * k);
	}

	public void calculateValidMoves() {}

	public boolean validMovesContains(Coord coord) {
		//System.out.println("Coord X: " + coord.getX() + " Coord Y: " + coord.getY());
		for(Coord co : validMoves) {
			//System.out.println("X: " + co.getX() + " Y: " + co.getY());
			if(co.getX() == coord.getX() && co.getY() == coord.getY()) {
				System.out.println("Contains square!");
				return true;
			}
		}
		//System.out.println("valid move?: " + validMoves.contains(coord));
		return false;
	}
	
}
