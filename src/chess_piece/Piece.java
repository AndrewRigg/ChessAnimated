package chess_piece;

import java.util.ArrayList;

import board.*;
import enums.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Piece extends Rectangle{

	static int gridsize = Literals.GRIDSIZE;
	String name, notation;
	Image image;
	Colour colour;
	State state;
	Type type;
	int number;
	private int magnitudeMove, maximumMove;
	public boolean thisPieceSelected, isWhite, thisPieceCondition;
	private ArrayList<Coord> validMoves;
	ArrayList<Circle> validMoveCircles;
	Coord coord;
	PieceActions actions;
	
	public Piece(Type type, Colour colour, int number) {
		super(gridsize/2, gridsize/2);
		this.type = type; 
		this.colour = colour; 
		this.number = number; 
		this.isWhite = (colour == Colour.WHITE);
		this.state = State.INITIAL; 
		this.notation = type.toString().substring(0, 1); 
		this.name = colour.toString().toLowerCase() + "_" + type.toString().toLowerCase();
		this.image = new Image("res/"+name+".png");
		this.getInitialCoords();
		this.coord = getInitialCoords(type, number);
		this.thisPieceSelected = false;
		this.setMagnitudeMove(1);
		this.setMaximumMove(7);
		this.setValidMoves(new ArrayList<Coord>());
		this.validMoveCircles = new ArrayList<Circle>();
		this.setFill(new ImagePattern(image));
		actions = new PieceActions(this);
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
		this.setX((number == 1 ? type.getPositionX()+1 : Literals.BOARD_END - type.getPositionX()-1)*gridsize + gridsize/4);
	}
	
	public Coord getInitialCoords(Type type, int number) {
		return new Coord((int)this.getX()/gridsize, (int)this.getY()/gridsize);
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
	
	public Coord validMoveCoord(int i, int j, int k) {
		return new Coord(coord.getX() + i * k, coord.getY() + j * k);
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}
}
