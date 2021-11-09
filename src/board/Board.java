package board;

import java.util.*;
import chess_piece.*;
import enums.Type;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.Duration;

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
	
	private ArrayList<Coord> calculateTraversal(Piece piece, int toX, int toY) {
		ArrayList<Coord> traversals = new ArrayList<Coord>();
		System.out.println("Piece: currentX:" + (int)(piece.getX()/gridsize) + ", currentY: " + (int)(piece.getY()/gridsize) + ", toX: " + toX + ", toY: " + toY);
		int xDiff = (int) Math.abs(toX - (int)(piece.getX()/gridsize));
		int yDiff = (int) Math.abs(toY - (int)(piece.getY()/gridsize));
		int xDir = (int) ((toX - (int)(piece.getX()/gridsize))/xDiff);
		int yDir = (int) ((toY - (int)(piece.getY()/gridsize))/yDiff);
		System.out.println("Xdiff: " + xDiff + " yDiff: " + yDiff + " xDir: " + xDir + " yDir: " + yDir);
		traversals.add(new Coord(piece.getCoord().getX()+(xDir < 0 ? -1 : 0), piece.getCoord().getY()+(yDir > 0 ? 1 : 0)));
		for(int i = 1; i <= xDiff; i++) {
			Coord temp = new Coord((int)(piece.getX()/gridsize)+i*xDir, (int)(piece.getY()/gridsize));
			traversals.add(temp);
		}
		for (int j = 1; j <= yDiff; j++) {
			traversals.add(new Coord((int)(piece.getX()/gridsize)+xDir*xDiff, (int)(piece.getY()/gridsize)+j*yDir));
		}
		return traversals;
	}
	
	private TranslateTransition animate(Duration duration, Piece piece, double toX, double toY) {
		TranslateTransition transition = new TranslateTransition(Utils.TRANSLATION_PARTIAL_DURATION(0.5), piece);
    	transition.setFromX(piece.getTranslateX());
        transition.setFromY(piece.getTranslateY());
        transition.setToX(toX);
        transition.setToY(toY);
		piece.setTranslateX(toX);
        piece.setTranslateY(toY);
        return transition;
	}
	
	private void moveOnKeyPressed(Piece piece, int x, int y)
    {
		if(Utils.PHYSICAL_BOARD_REPRESENTATION && piece.getType() == Type.KNIGHT) {
			ArrayList<Coord> traversals  = new ArrayList<Coord>();
			traversals = calculateTraversal(piece, x, y);
			SequentialTransition sequence = new SequentialTransition(piece);
			for(int i = 1; i < traversals.size(); i++) {
				System.out.println(traversals.get(i).toString());
				double toX = traversals.get(i).getX()*gridsize - piece.getX() + (i==traversals.size()-1 ? gridsize/4 : - gridsize/4);
				double toY = traversals.get(i).getY()*gridsize - piece.getY() - (i==traversals.size()-1 ? gridsize/4 : - gridsize/4);
				TranslateTransition transition = animate(Utils.TRANSLATION_PARTIAL_DURATION(0.5), piece, toX, toY);
	            sequence.getChildren().add(transition);
			}
            sequence.play();
		}else{
			double toX = x*gridsize - piece.getX() + gridsize/4;
			double toY = y*gridsize - piece.getY() + gridsize/4;
			TranslateTransition transition = animate(Utils.TRANSLATE_DURATION, piece, toX, toY);
        	transition.playFromStart();
		}
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