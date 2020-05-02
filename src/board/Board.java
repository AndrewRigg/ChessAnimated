package board;

import java.util.*;
import chess_piece.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class Board{
	
	int rows = Literals.RANKS;
	int cols = Literals.FILES;
	int gridsize = Literals.GRIDSIZE;
	int asciiCaps = Literals.ASCII_CAPS;
	ArrayList<Circle> validMoveCircles;
	public final Group group;
	public boolean pieceSelected, piecesInitialised, pieceHighlighted;
	public Piece currentPiece;
	public Scene scene;
	public Controller controller;


	public Board(Controller controller) {
		this.controller = controller;
		group = new Group();
        drawSquares();
        drawLines();
        drawLabels(); 
		setPlayer(controller.player1);
		setPlayer(controller.player2);
		piecesInitialised = true;
		validMoveCircles = new ArrayList<Circle>();
		pieceSelected = false;
	}

	public void print(String str) {
		Literals.print(str, Literals.BOARD_DEBUG);
	}

	private void drawSquares() {
		for(int i = 0; i < Literals.FILES; i++) {
			for(int j = 0; j < Literals.RANKS; j++) {
				Rectangle rectangle= new Rectangle(gridsize*2 + i * gridsize, gridsize*2 + j * gridsize, gridsize, gridsize);
				rectangle.setFill((i + j) % 2 != 0 ? Color.grayRgb(180) : Color.WHITE);
				group.getChildren().add(rectangle);
			}	
		}
		group.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int x = (int)(event.getSceneX()/gridsize);
				int y = (int)(event.getSceneY()/gridsize);
//				Coord coord = new Coord(x, y);
				print("x " +x + " y " + y);
				Coord clickedSquare = new Coord(x, y);
					controller.determineClickType(clickedSquare);	
					if(!controller.pieceCurrentlySelected || pieceHighlighted) {
						clearValidMoves();//Use this line
					}
					if(controller.movingPiece) {
						moveOnKeyPressed(controller.selectedPiece, controller.selectedPiece.getCoord().getX(), controller.selectedPiece.getCoord().getY());
						controller.movingPiece = false;
						pieceHighlighted = false;
						//controller.selectedPiece.setCoord(new Coord(controller.selectedPiece.getCoord().getX(), controller.selectedPiece.getCoord().getY()));
					}else if(controller.pieceCurrentlySelected && pieceHighlighted) {
						//if same colour piece selected and piece already selected prior						
						print("Selected when piece already highlighted");
						pieceHighlighted = true;
						drawCircles();
					}
					else if(controller.pieceCurrentlySelected) {
						//if no piece selected prior
						print("Selected first time");
						drawCircles();	//	Need to draw these in the controller somehow - pass variables from controller
						pieceHighlighted = true;
					}else {
						print("In the else condition...");
						pieceHighlighted = false;
					}
			}
		});
	}
	
	/**
	 * Draw the markers for valid moves to show where the piece can move including 
	 * empty square coordinates and coordinates of pieces which may be captured
	 */
	public void drawCircles() {
		group.getChildren().addAll(controller.validMoveMarkers);
	}
	
	/**
	 * Remove the markers from the board and clear the arrays 
	 * of valid coordinates and markers
	 */
	public void clearValidMoves(){
		print("Cleared"+ controller.validMoveMarkers.toString());
		//This needs to happen before the moves are calculated
		if(!controller.validMoveMarkers.isEmpty()) {
			group.getChildren().removeAll(controller.validMoveMarkers);
		}
		controller.validMoveMarkers.clear();
		controller.validMoves.clear();
	}
	
//	public void clearValidMarkers() {
//		controller.validMoveMarkers.clear();
//		controller.validMoves.clear();
//	}
	
	private void moveOnKeyPressed(Piece piece, int x, int y)
    {
        final TranslateTransition transition = new TranslateTransition(Literals.TRANSLATE_DURATION, piece);
        print("Moving");
        	transition.setFromX(piece.getTranslateX());
            transition.setFromY(piece.getTranslateY());
            transition.setToX(x*gridsize - piece.getX() + gridsize/4);
            transition.setToY(y*gridsize - piece.getY() + gridsize/4);
        	transition.playFromStart();
        	print("Did this");
    }

	private void drawLines() {
		for(int i = 0; i < rows+1; i++) {
			setUpLine(group, gridsize *2, gridsize *2 + gridsize * i, gridsize * (rows + 2), gridsize *2 + gridsize * i);
			setUpLine(group, gridsize *2 + gridsize * i, gridsize *2, gridsize *2 + gridsize * i, gridsize * (rows + 2));
		}
	}	
	
	private void setUpLine(Group group, int startX, int startY, int endX, int endY) {
			Line line = new Line();
			line.setStartX(startX);
			line.setStartY(startY);
			line.setEndX(endX);
			line.setEndY(endY);
			group.getChildren().add(line);
	}
	
	private void drawLabels() {
		for(int i = 1; i <= cols; i++) {
			setText("" + (char)(i + asciiCaps), gridsize + gridsize*(i) + gridsize/2, 10*gridsize + gridsize/2);
			setText("" + (9 - i), gridsize + gridsize/2, gridsize + gridsize*(i) + gridsize/2);
		}
	}
	
	public void setText(String str, int x, int y) {
		Text text = new Text(str);
		text.setFont(Font.font("verdana", 15));
    	text.setX(x - text.getFont().getSize()/2);
    	text.setY(y + text.getFont().getSize()/2);
    	group.getChildren().add(text);
	}
	
	public void setPlayer(Player player) {
		addPieces(player);
		if(player.clockActive) {
			addChessClock(player);
		}
	}

	public void addPieces(Player player) {
		for(Piece piece : player.pieces) {
			group.getChildren().add(piece);
		}
	}
	
	public void addChessClock(Player player) {
		Label clockLabel = player.getClock().getLabel();
		clockLabel.setTextAlignment(TextAlignment.CENTER);
		clockLabel.setTranslateX(player.clockPosition);
		clockLabel.setTranslateY(gridsize);
		group.getChildren().add(clockLabel);
	}
	
	public void removeHighlightedSquares() {
		//This is not a great solution as more circles keep getting added to the group (could lead to memory overflow and slowdown)
		print("Before: " + group.getChildren().size());
		print("Clear circles");
		for(Circle circle: validMoveCircles) {
			circle.setFill(Color.rgb(255, 0, 0, 0.00));
		}
		group.getChildren().removeAll(validMoveCircles);	//Old method which doesn't always work for some reason
		validMoveCircles.clear();
	}
}