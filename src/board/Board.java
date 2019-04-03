package board;

import java.util.ArrayList;

import chess_piece.*;
import enums.*;
import javafx.animation.TranslateTransition;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
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
	ArrayList<Circle> validMoveCircles;
	final Group group = new Group();
	public boolean pieceSelected = true;
	public Piece currentPiece;
	public Scene scene;
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	PieceFactory factory = new PieceFactory();
	
	public Board(){
		this.validMoveCircles = new ArrayList<Circle>();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setUpBoard();
		initialisePieces();
		setScene(stage);
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
				rectangle.setFill((i + j) % 2 != 0 ? Color.LIGHTGRAY : Color.WHITE);
				group.getChildren().add(rectangle);
			}	
		}
		group.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int x = (int)(event.getSceneX()/gridsize);
				int y = (int)(event.getSceneY()/gridsize);
				System.out.println("x " +x + " y " + y);
				Coord coord = new Coord(x, y);
				if(!pieceSelected) {
					removeHighlightedSquares();
				}else if(currentPiece != null && currentPiece.validMovesContains(coord)){
					System.out.println("Moved");
					moveOnKeyPressed(currentPiece, y, x);
				}
			}
		});
	}
	
	private void moveOnKeyPressed(Piece piece, int x, int y)
    {
        final TranslateTransition transition = new TranslateTransition(Literals.TRANSLATE_DURATION, piece);
        scene.setOnMousePressed(e -> {
            if(piece.thisPieceSelected) {
            	transition.setFromX(piece.getTranslateX());
                transition.setFromY(piece.getTranslateY());
                transition.setToX((int)(e.getSceneX()/gridsize) * gridsize - x);
                transition.setToY((int)(e.getSceneY()/gridsize) * gridsize - y);
            	transition.playFromStart();
            }
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
		//removeHighlightedSquares();
		//pieceSelected = !pieceSelected;
		if(pieceSelected) {
			validMoveCircles.clear();
			validMoveCircles.addAll(piece.getCircles());
			drawCircles();
		}
	}
	
	public void removeHighlightedSquares() {
		//This is not a great solution as more circles keep getting added to the group (could lead to memory overflow and slowdown)
		//System.out.println("Before: " + group.getChildren().size());
		System.out.println("Clear circles");
		for(Circle circle: validMoveCircles) {
			circle.setFill(Color.rgb(255, 0, 0, 0.00));
		}
		//group.getChildren().removeAll(validMoveCircles);	//Old method which doesn't always work for some reason
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
	}
}