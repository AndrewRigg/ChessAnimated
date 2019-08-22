package board;

import java.util.ArrayList;

import chess_piece.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.Label;
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
	public boolean pieceSelected, piecesInitialised;
	public Piece currentPiece;
	public Scene scene;
	public Controller controller;
	ArrayList<Node> removable;

	public Board(Controller controller) {
		removable = new ArrayList<Node>();
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
				//if(piecesInitialised) {
					//clearValidMoves();		//Need to clear this in controller somehow
					controller.determineClickType(clickedSquare);							//Use this line
					if(controller.movingPiece) {
						print(""+controller.selectedPiece.getCoord().getX());
						print(""+controller.selectedPiece.getCoord().getY());
						print("" + (controller.selectedPiece.getCoord().getX()*gridsize + gridsize/4));
						print(""+ (controller.selectedPiece.getCoord().getY()*gridsize + gridsize/4));
						moveOnKeyPressed(controller.selectedPiece, 
								controller.selectedPiece.getCoord().getX(), 
								controller.selectedPiece.getCoord().getY());
						 controller.movingPiece = false;
						//controller.selectedPiece.setCoord(new Coord(controller.selectedPiece.getCoord().getX(), controller.selectedPiece.getCoord().getY()));
					}
					if(controller.pieceCurrentlySelected) {
//						clearValidMoves();
						drawCircles();	//	Need to draw these in the controller somehow - pass variables from controller
					}else {
//						clearValidMarkers();
						clearValidMoves();
					}
				//}
			}
		});
	}
	
	/**
	 * Draw the markers for valid moves to show where the piece can move including 
	 * empty square coordinates and coordinates of pieces which may be captured
	 */
	public void drawCircles() {
		for(Circle circle: controller.validMoveMarkers) {
			group.getChildren().add(circle);
		}
	}
	
	/**
	 * Remove the markers from the board and clear the arrays 
	 * of valid coordinates and markers
	 */
	public void clearValidMoves(){
		print("Cleared"+ controller.validMoveMarkers.toString());
		
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
        //This was before restructuring -> not sure if group works instead of scene
        //scene.setOnMousePressed(e -> {
        print("Moving");
        group.setOnMousePressed(e -> {
        	transition.setFromX(piece.getTranslateX());
            transition.setFromY(piece.getTranslateY());
            transition.setToX(x*gridsize - piece.getX() + gridsize/4);
            transition.setToY(y*gridsize - piece.getY() + gridsize/4);
        	transition.playFromStart();
        	print("Did this");
        	//This never gets called on the first key clicked?!
        });
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