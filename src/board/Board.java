package board;

import java.util.ArrayList;

import enums.Colour;
import enums.Type;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Board extends Application {
	
	int rows = Literals.RANKS;
	int cols = Literals.FILES;
	int gridsize = Literals.GRIDSIZE;
	int asciiCaps = Literals.ASCII_CAPS;
	final Group group = new Group();
	Scene scene;
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	PieceFactory factory = new PieceFactory();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setUpBoard();
		initialisePieces();
		setScene(stage);
		setUpMovements();
	}

	private void setUpBoard() {
        drawSquares();
        drawLines();
        drawLabels();
	}

	private void drawSquares() {
		for(int i = 0; i < Literals.FILES; i++) {
			for(int j = 0; j < Literals.RANKS; j++) {
				if((i + j) % 2 != 0) {
					Rectangle rectangle= new Rectangle(120 + i * gridsize, 120 + j * gridsize, 60, 60);
					rectangle.setFill(Color.LIGHTGRAY);
					group.getChildren().add(rectangle);
				}	
			}	
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
	
	public void initialisePieces() {
		for(Colour colour: Colour.values()) {
			if(colour.ordinal() < 2) {
				for(Type type : Type.values()){
					for(int number = 1; number <= type.getQuantity(); number++) {
						Piece piece = factory.assignPieces(type, colour, number);
					
						piece.setOnMousePressed(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								System.out.println("CLICKED THIS PIECE " + piece.name);
								TranslateTransition transition = createTranslateTransition(piece);
								transition.setToX((int)(event.getSceneX()/gridsize) * gridsize + gridsize/4 - piece.getX());
								transition.setToY((int)(event.getSceneY()/gridsize) * gridsize + gridsize/4 - piece.getY());
								transition.playFromStart();
							}
							//piece.setFill(new ImagePattern(new Image("res/chess_icon.jpg")));
						});
						pieces.add(piece);
						group.getChildren().add(piece);
					}
				}
			}
		}
	}
	
	private void setScene(Stage stage) {
		scene = new Scene(group, gridsize * (rows + 4), gridsize * (cols + 4));
		stage.setScene(scene);
		stage.setTitle("Animated Chess");
		stage.getIcons().add(new Image("res/chess_icon.jpg"));
		stage.show();
	}
	
	private void setUpMovements() {
		
		
//		TranslateTransition transition = createTranslateTransition(pieces.get(5));
//		movePieceOnMousePress(scene, pieces.get(5), transition);
	}
	
	private TranslateTransition createTranslateTransition(final Piece piece) {
		final TranslateTransition transition = new TranslateTransition(Literals.TRANSLATE_DURATION, piece);
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				piece.setX(piece.getTranslateX() + piece.getX());
				piece.setY(piece.getTranslateY() + piece.getY());
				piece.setTranslateX(0);
				piece.setTranslateY(0);
			}
		});
		return transition;
	}
	
	private void movePieceOnMousePress(Scene scene, final Rectangle piece, final TranslateTransition transition) {
//		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				transition.setToX((int)(event.getSceneX()/gridsize) * gridsize + gridsize/4 - piece.getX());
//				transition.setToY((int)(event.getSceneY()/gridsize) * gridsize + gridsize/4 - piece.getY());
//				transition.playFromStart();
//			}
//		});
	}
}