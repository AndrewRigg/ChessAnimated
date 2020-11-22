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
	
	int rows = Utils.RANKS;
	int cols = Utils.FILES;
	int gridsize = Utils.GRIDSIZE;
	int asciiCaps = Utils.ASCII_CAPS;
	ArrayList<Circle> validMoveCircles;
	public final Group group;
	public boolean pieceSelected, piecesInitialised, pieceHighlighted, flip=true;
	public Piece currentPiece;
	public Scene scene;
	public Controller controller;
	Rectangle selectedSquare = new Rectangle (0,0,gridsize-2, gridsize-2);
	Rectangle selectedSquare2 = new Rectangle (0,0,gridsize-2, gridsize-2);

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
		group.getChildren().add(selectedSquare);
		group.getChildren().add(selectedSquare2);
		selectedSquare.setFill(Color.rgb(0, 0, 0, 0));
		selectedSquare2.setFill(Color.rgb(0, 0, 0, 0));
	}

	public void print(String str) {
		Utils.print(str, Utils.BOARD_DEBUG);
	}

	private void drawSquares() {
		for(int i = 0; i < Utils.FILES; i++) {
			for(int j = 0; j < Utils.RANKS; j++) {
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
				Coord clickedSquare = new Coord(x, y);
				Coord square = new Coord((int)(selectedSquare.getX()/gridsize), (int)(selectedSquare.getY()/gridsize));
				if(controller.compareCoords(clickedSquare, square)) {
					flip = !flip;
					print("flip is false: square clicked twice");
				}else {
					flip = true;
				}
				if(clickedSquare.onGrid()) {
					selectedSquare.setX(gridsize*x+1);
					selectedSquare.setY(gridsize*y+1);
					selectedSquare2.setX(gridsize*x+1);
					selectedSquare2.setY(gridsize*y+1);
				}
					controller.determineClickType(clickedSquare);	
					if(flip && controller.pieceCurrentlySelected) {
						selectedSquare.setFill(Color.rgb(255, 255, 255, 1));
						selectedSquare2.setFill(Color.rgb(0, 200, 100, 0.3));
					}else if(flip){
						selectedSquare.setFill(Color.rgb(255, 255, 255, 1));
						selectedSquare2.setFill(Color.rgb(200, 100, 100, 0.3));
					}
					else if(!flip){
						selectedSquare.setFill(Color.rgb(0, 0, 0, 0));
						selectedSquare2.setFill(Color.rgb(0, 0, 0, 0));
					}
					if(controller.pieceCurrentlySelected) {
						currentPiece = controller.selectedPiece;
						//currentPiece.toFront();
						currentPiece.setWidth(Utils.HIGHLIGHTED_SIZE);
						currentPiece.setHeight(Utils.HIGHLIGHTED_SIZE);
						currentPiece.setX(currentPiece.getX() - 5);
						currentPiece.setY(currentPiece.getY() - 5);
					}
					if(controller.movingPiece) {
						selectedSquare.setFill(Color.rgb(0, 0, 0, 0));
						selectedSquare2.setFill(Color.rgb(0, 0, 0, 0));
						checkForPromotion();
						moveOnKeyPressed(controller.selectedPiece, controller.selectedPiece.getCoord().getX(), controller.selectedPiece.getCoord().getY());
						controller.movingPiece = false;
						pieceHighlighted = false;
					}else if(controller.taking) {
						selectedSquare.setFill(Color.rgb(0, 0, 0, 0));
						selectedSquare2.setFill(Color.rgb(0, 0, 0, 0));
						checkForPromotion();
						controller.clickedPiece.setCoord(new Coord(controller.opponent.getTakenZone(controller.opponent.getTakenPieces()).getX(), controller.opponent.getTakenZone(controller.opponent.getTakenPieces()).getY()));
						moveOnKeyPressed(controller.clickedPiece, controller.clickedPiece.getCoord().getX(), controller.clickedPiece.getCoord().getY());
						moveOnKeyPressed(controller.selectedPiece, controller.selectedPiece.getCoord().getX(), controller.selectedPiece.getCoord().getY());
						controller.taking = false;
						controller.opponent.addTakenPiece();
						pieceHighlighted = false;
					}
					else if(controller.pieceCurrentlySelected) {
						//if same colour piece selected and piece already selected prior		
						print("Selected when piece already highlighted");
						pieceHighlighted = true;
						if(!controller.validMoves.isEmpty()) {
							drawCircles();
						}
					}else {
						print("No valid move selected");
						//Not sure about these two lines:
						pieceHighlighted = false;
					}
			}
		});
	}
	
	public void checkForPromotion(){
		if(controller.checkForPromotion()) {
			group.getChildren().add(controller.selectedPiece);
		}
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
        final TranslateTransition transition = new TranslateTransition(Utils.TRANSLATE_DURATION, piece);
        	transition.setFromX(piece.getTranslateX());
            transition.setFromY(piece.getTranslateY());
            transition.setToX(x*gridsize - piece.getX() + gridsize/4);
            transition.setToY(y*gridsize - piece.getY() + gridsize/4);
        	transition.playFromStart();
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
		Label clockLabel = player.getClock().getCountdown();
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