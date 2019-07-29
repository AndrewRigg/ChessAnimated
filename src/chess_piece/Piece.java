package chess_piece;

import java.util.ArrayList;

import board.*;
import enums.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Piece extends Rectangle{

	static int gridsize = Literals.GRIDSIZE;
	public String name, notation;
	Image image;
	Colour colour;
	State state;
	Type type;
	int pieceNumber, yOffset;
	private int magnitudeMove, maximumMove;
	public boolean thisPieceSelected, isWhite, thisPieceCondition;
	private ArrayList<Coord> validMoves;
	ArrayList<Circle> validMoveCircles;
	Coord current;
	
	
	public Piece(Type type, Colour colour, int pieceNumber) {
		super(gridsize/2, gridsize/2);
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
		this.setMagnitudeMove(1);
		this.setMaximumMove(7);
		this.setValidMoves(new ArrayList<Coord>());
		this.validMoveCircles = new ArrayList<Circle>();
		this.setFill(new ImagePattern(image));
	}	

	/**
	 * Debugging function, set PIECE_DEBUG to true to turn on
	 * @param str
	 */
	public void print(String str) {
		Literals.print(str, Literals.PIECE_DEBUG);
	}

	public ArrayList<Circle> getCircles(){
		return validMoveCircles;
	}
	
	public void getInitialCoords() {
		this.setY((isWhite ? Literals.EIGHTH_ROW : Literals.FIRST_ROW) * gridsize + gridsize/4);
		this.setX((pieceNumber == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1)*gridsize + gridsize/4);
	}
	
	public Coord getInitialCoords(Type type, Colour colour, int number) {
		//Coord initial = new Coord(0, 0);
		//initial.setY(isWhite ? Literals.EIGHTH_ROW : Literals.FIRST_ROW);
		//initial.setX(number == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1);
		//return initial;
		return new Coord(number == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1, isWhite ? Literals.EIGHTH_ROW : Literals.FIRST_ROW);
	}
		
	/**
	 * This is the base function which is overridden by each individual piece
	 * and is for determining the direction and magnitude of any potential moves
	 * @param xDirection
	 * @param yDirection
	 * @param magnitude
	 * @return
	 */
	public boolean movementCondition(int xDirection, int yDirection, int magnitude) {
		return true;
	}
	
	public Coord thisPieceConditionCoord(int i, int j, int k) {
		return new Coord(current.getX() + i * k, current.getY() + j * k);
	}

	public void calculateValidMoves() {}

	public boolean validMovesContains(Coord coord) {
		print("Coord X: " + coord.getX() + " Coord Y: " + coord.getY());
		for(Coord co : getValidMoves()) {
			print("X: " + co.getX() + " Y: " + co.getY());
			if(co.getX() == coord.getX() && co.getY() == coord.getY()) {
				print("Contains square!");
				return true;
			}
		}
		print("valid move?: " + getValidMoves().contains(coord));
		return false;
	}

	public ArrayList<Coord> getValidMoves() {
		return validMoves;
	}

	public void setValidMoves(ArrayList<Coord> validMoves) {
		this.validMoves = validMoves;
	}

	public int getMagnitudeMove() {
		return magnitudeMove;
	}

	public void setMagnitudeMove(int magnitudeMove) {
		this.magnitudeMove = magnitudeMove;
	}

	public int getMaximumMove() {
		return maximumMove;
	}

	public void setMaximumMove(int maximumMove) {
		this.maximumMove = maximumMove;
	}
	
}
