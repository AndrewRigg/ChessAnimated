package board;

import java.util.*;

import chess_piece.*;
import enums.*;
import javafx.animation.*;
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
	ArrayList<Circle> validMoveCircles;
	final Group group = new Group();
	public boolean pieceSelected;
	public Piece currentPiece;
	public Scene scene;
	private Player player1, player2;

	public Board() {
		//Game with no clocks
		//player1 = new Player(Colour.WHITE);
		//player2 = new Player(Colour.BLACK);
		//Game with default clocks
		//player1 = new Player(Colour.WHITE, true);
		//player2 = new Player(Colour.BLACK, true);
		//Game with custom clocks
		player1 = new Player(Colour.WHITE, 0, 5);
		player2 = new Player(Colour.BLACK, 1, 8);
		validMoveCircles = new ArrayList<Circle>();
		pieceSelected = false;
	}
	
	/**
	 * Update board every second and every time a move is made
	 */
	public void updateBoard() {
		
	}
	
	public void print(String str) {
		Literals.print(str, Literals.BOARD_DEBUG);
	}
	
	public void addChessClock(Player player) {
		Label clockLabel = player.getClock().getLabel();
		clockLabel.setTextAlignment(TextAlignment.CENTER);
		clockLabel.setTranslateX(player.clockPosition);
		clockLabel.setTranslateY(gridsize);
		group.getChildren().add(clockLabel);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		setUpBoard();
		setPlayer(player1);
		setPlayer(player2);
		setScene(stage);
	}
	
	public void setPlayer(Player player) {
		player.initialise();
		addPieces(player);
		if(player.clockActive) {
			addChessClock(player);
			if(player.player == PlayerNumber.PlayerOne) {
				player.setTurn(true);
				player.getClock().setRunning(true);
			}
		}
	}
	

	private void setUpBoard() {
        drawSquares();
        drawLines();
        drawLabels(); 
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
				Coord coord = new Coord(x, y);
				print("x " +x + " y " + y);
				print("\nPiece Selected: " + pieceSelected);//
				print("validMoveCircles: " + validMoveCircles.toString());
				if(currentPiece != null){
					//print(currentPiece.name);
					//print("currentPiece != null: " + (currentPiece != null));
					//print("validMovesContains: " + currentPiece.validMovesContains(coord));
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
	
	public void addPieces(Player player) {
		for(Piece piece : player.pieces) {
			group.getChildren().add(piece);
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
	
	//MOVED FROM PIECE - CHANGE TO ONLY HAVE SQUARES CLICKED
	@SuppressWarnings("unused")
	private void pieceClickedFromPiece(Piece piece) {
//		setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				piece.thisPieceSelected = !piece.thisPieceSelected;
//				print("Piece: Removing highlights");	
//				removeHighlightedSquares();				
//				if(piece.thisPieceSelected) {
//					pieceSelected = true;
//					print("Piece: board.pieceSelected: " + pieceSelected);
//					setUpBasicMoves(piece);
//					currentPiece = piece;
//					//thisPieceSelected = false;
//				}else {
//					//board.removeHighlightedSquares();
//					//moveOnKeyPressed(piece, (int)(event.getSceneX()/gridsize) * gridsize, (int)(event.getSceneY()/gridsize) * gridsize);
//					print("Unselected " + piece.name);	
//					//thisPieceSelected = true;
//				}
////				piece.setX((int)(event.getSceneX()/gridsize) * gridsize);		//TRY THIS TYPE OF THING
////				piece.setY((int)(event.getSceneY()/gridsize) * gridsize);
//			}
//		});
	}

	//MOVED FROM PIECE
	public void setUpBasicMoves(Piece piece) {
		piece.getValidMoves().clear();
		for (int i = piece.getMagnitudeMove()*(-1); i <= piece.getMagnitudeMove(); i++) {
			for (int j = piece.getMagnitudeMove()*(-1); j <= piece.getMagnitudeMove(); j++) {
				for (int k = 1; k <= piece.getMaximumMove(); k++) {
					if (piece.movementCondition(i, j, k) && !(i == 0 && j == 0)) {
						Coord coord = piece.validMoveCoord(i, j, k);
						if (coord.onGrid() && !piece.getValidMoves().contains(coord)) {
							piece.getValidMoves().add(coord);
						}
					}
				}
			}
		}
		highlightSquares(piece);
	}
	
	//MOVED TO BOARD CLASS
	public void highlightSquares(Piece piece) {
		//validMoveCircles.clear();
		for(Coord coord: piece.getValidMoves()) {
			Circle circle =  new Circle(gridsize/3.5);
			circle.setFill(Color.GREENYELLOW);
			circle.setCenterX(coord.getX() * gridsize + gridsize/2);
			circle.setCenterY(coord.getY() * gridsize + gridsize/2);
			validMoveCircles.add(circle);
		}
		pieceClicked(piece);
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