package board;

import java.util.ArrayList;

import chess_piece.*;
import enums.*;
import javafx.animation.TranslateTransition;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Board extends Application {
	
	int rows = Literals.RANKS;
	int cols = Literals.FILES;
	int gridsize = Literals.GRIDSIZE;
	int asciiCaps = Literals.ASCII_CAPS;
	Label clock1Label, clock2Label;
	ChessClock clock1, clock2;
	static ArrayList<Circle> validMoveCircles;
	final Group group = new Group();
	static public boolean pieceSelected;
	public Piece currentPiece;
	public Scene scene;
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	PieceFactory factory = new PieceFactory();
	
	public Board(){
		Board.validMoveCircles = new ArrayList<Circle>();
		pieceSelected = false;
	}
	
	public void print(String str) {
		Literals.print(str, Literals.BOARD_DEBUG);
	}
	
	public void addChessClock(boolean showClock) {
		if(showClock) {
			clock1 = new ChessClock();
			clock2 = new ChessClock();
//			clock1 = new Label();
//			clock2 = new Label();
			clock1Label = clock1.getLabel();
			clock2Label = clock2.getLabel();
//			clock1.setup();
//			clock1.setText("Clock1");
//			clock2.setText("Clock2");
			clock1Label.setTextAlignment(TextAlignment.RIGHT);
			clock2Label.setTextAlignment(TextAlignment.CENTER);
			clock1Label.setTranslateX(gridsize + 10);
			clock1Label.setTranslateY(gridsize);
			clock2Label.setTranslateX(10*gridsize+10);
			clock2Label.setTranslateY(gridsize);
			group.getChildren().add(clock1Label);
			group.getChildren().add(clock2Label);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		setUpBoard();
		initialisePieces();
		setScene(stage);
		addChessClock(true);
	}

	private void setUpBoard() {
        drawSquares();
        drawLines();
        drawLabels(); 
	}

	private void drawSquares() {
		for(int i = 0; i < Literals.FILES; i++) {
			for(int j = 0; j < Literals.RANKS; j++) {
				Rectangle rectangle= new Rectangle(120 + i * gridsize, 120 + j * gridsize, 60, 60);
				rectangle.setFill((i + j) % 2 != 0 ? Color.grayRgb(180) : Color.WHITE);
				group.getChildren().add(rectangle);
			}	
		}
		group.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int x = (int)(event.getSceneX()/gridsize);
				int y = (int)(event.getSceneY()/gridsize);
				Coord coord = new Coord(x, y);
				print("x " +x + " y " + y);
				print("\nPiece Selected: " + pieceSelected);//
				print("validMoveCircles: " + validMoveCircles.toString());
				if(currentPiece != null){
					print(currentPiece.name);
					//System.out.println("currentPiece != null: " + (currentPiece != null));
					System.out.print("validMovesContains: " + currentPiece.validMovesContains(coord));
				}
				
				if(!pieceSelected) {
					print("Board: Removing highlights");
					//validMoveCircles.clear();
					print("validMoveCircles: " + validMoveCircles.toString());
					removeHighlightedSquares();
					print("validMoveCircles: " + validMoveCircles.toString());
					
				}
				if(pieceSelected && currentPiece != null && currentPiece.validMovesContains(coord)){
					print("Moved");
					moveOnKeyPressed(currentPiece, x, y);
					pieceSelected = false;
				}
				//
			}
		});
	}
	
	private void moveOnKeyPressed(Piece piece, int x, int y)
    {
        final TranslateTransition transition = new TranslateTransition(Literals.TRANSLATE_DURATION, piece);
        
        scene.setOnMousePressed(e -> {
        	transition.setFromX(piece.getTranslateX());
            transition.setFromY(piece.getTranslateY());
            transition.setToX(x*gridsize - piece.getX() + gridsize/4);
            transition.setToY(y*gridsize - piece.getY() + gridsize/4);
        	transition.playFromStart();
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
	
	public void initialisePieces() {
		for(Colour colour: Colour.values()) {
			if(colour.ordinal() < 2) {
				for(Type type : Type.values()){
					for(int number = 1; number <= type.getQuantity(); number++) {
						Piece piece = factory.assignPieces(this, type, colour, number);
						pieces.add(piece);
						group.getChildren().add(piece);
					}
				}
			}
		}
	}
	
	/**
	 * This is called from the piece class to allow the board to define the valid moves in order to clear 
	 * moves from other pieces (which pieces cannot access)
	 * @param piece
	 */
	public void pieceClicked(Piece piece) {
		print("Board: Piece clicked");
		removeHighlightedSquares();
		//pieceSelected = !pieceSelected;
		if(pieceSelected) {
			//validMoveCircles.clear();
			validMoveCircles.addAll(piece.getCircles());
			drawCircles();
		}
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
	
	public void drawCircles() {
		for(Circle circle: validMoveCircles) {
			group.getChildren().add(circle);
		}
	}
	
	private void setScene(Stage stage) {
		scene = new Scene(group, gridsize * (rows + 4), gridsize * (cols + 4));
		stage.setScene(scene);
		stage.setTitle("Animated Chess");
		stage.getIcons().add(new Image("res/chess_icon.jpg"));
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
}