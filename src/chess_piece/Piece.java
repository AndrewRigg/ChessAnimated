package chess_piece;

import java.util.ArrayList;

import board.*;
import enums.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Piece extends Rectangle{

	static int gridsize = Utils.GRIDSIZE;
	String name, notation;
	public Image image;
	Colour colour;
	State state;
	Type type;
	int number;
	private int magnitudeMove, maximumMove;
	public boolean thisPieceSelected, isWhite, thisPieceCondition, firstMove = true, revealCheck;
	private ArrayList<Coord> validMoves;
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
		this.thisPieceSelected = false;
		this.setMagnitudeMove(1);
		this.setMaximumMove(7);
		this.setValidMoves(new ArrayList<Coord>());
		this.setFill(new ImagePattern(image));
		setCoord(getInitialCoords(type, number));
		actions = new PieceActions(this);
	}	
	
	public Piece(Type type, Colour colour, int number, Coord coord) {
		super(gridsize/2, gridsize/2);
		this.type = type; 
		this.colour = colour; 
		this.number = number; 
		this.isWhite = (colour == Colour.WHITE);
		this.state = State.NORMAL; 
		this.notation = type.toString().substring(0, 1); 
		this.name = colour.toString().toLowerCase() + "_" + type.toString().toLowerCase();
		this.image = new Image("res/"+name+".png");
		this.thisPieceSelected = false;
		this.coord = coord;
		this.setX(coord.getX()* gridsize + gridsize/4);
		this.setY(coord.getY()* gridsize + gridsize/4);
		this.setMagnitudeMove(1);
		this.setMaximumMove(7);
		this.setValidMoves(new ArrayList<Coord>());
		this.setFill(new ImagePattern(image));
		actions = new PieceActions(this);
		print(name + number);
	}	

	/**
	 * Debugging function, set PIECE_DEBUG to true to turn on
	 * @param str
	 */
	public void print(String str) {
		Utils.print(str, Utils.PIECE_DEBUG);
	}
	
	public void getInitialCoords() {
		this.setY((isWhite ? Utils.EIGHTH_ROW : Utils.FIRST_ROW) * gridsize + gridsize/4);
		this.setX((number == 1 ? type.getPositionX()+1 : Utils.BOARD_END - type.getPositionX()-1)*gridsize + gridsize/4);
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
	
	public void setRevealCheck(Player opponent) {
		
	}
	
	/**
	 * calculate the valid moves for this piece 
	 * (movementCondition is overridden by individual pieces)
	 */
	public void calculateValidMoves(Player player, Player opponent) {
		validMoves.clear();
		for (int i = getMagnitudeMove()*(-1); i <= getMagnitudeMove(); i++) {
			for (int j = getMagnitudeMove()*(-1); j <= getMagnitudeMove(); j++) {
				magnitudeLoop:
				for (int k = 1; k <= getMaximumMove(); k++) {
					if (movementCondition(i, j, k) && !(i == 0 && j == 0)) {
						Coord coord = validMoveCoord(i, j, k);
						if (coord.onGrid() && !getValidMoves().contains(coord)) {
							//Need something like this to block pieces
							//if (board.getSquare(coord).occupyingPiece.colour != this.colour) {
							for(Piece piece: player.pieces) {
								if(compareCoords(piece.getCoord(), coord)) {
									break magnitudeLoop;
								}
							}
							for(Piece oppoPiece: opponent.pieces) {
								if(compareCoords(oppoPiece.getCoord(), coord)) {
									validMoves.add(coord);
									break magnitudeLoop;
								}
							}
							validMoves.add(coord);
						}
					}
				}
			}
		}
	}
	
	public void setPositionBasedOnCoord(Coord coord) {
		this.setX(coord.getX()* gridsize + gridsize/4);
		this.setY(coord.getX()* gridsize + gridsize/4);
	}
	
	public boolean compareCoords(Coord coord1, Coord coord2) {
		return (coord1.getX() == coord2.getX() && coord1.getY() == coord2.getY());
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
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
