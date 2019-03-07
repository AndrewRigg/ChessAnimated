package board;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Board extends Application {

	private int length = Literals.GRIDSIZE/2;
	int rows = Literals.RANKS;
	int cols = Literals.FILES;
	int gridsize = Literals.GRIDSIZE;
	int i = 0;
	
	ArrayList<Image> images = new ArrayList<Image>();
	//ArrayList<Image> imagesStrings = new ArrayList<Image>();
	String [] imageStrings = {"bishop", "king", "knight", "pawn","queen", "rook"};
	//Pieces Images
	Image blackBishopImage = new Image("res/black_bishop.png");
	Image blackKingImage = new Image("res/black_king.png");
	Image blackKnightImage = new Image("res/black_knight.png");
	Image blackPawnImage = new Image("res/black_pawn.png");
	Image blackQueenImage = new Image("res/black_queen.png");
	Image blackRookImage = new Image("res/black_rook.png");
	Image whiteBishopImage = new Image("res/white_bishop.png");
	Image whiteKingImage = new Image("res/white_king.png");
	Image whiteKnightImage = new Image("res/white_knight.png");
	Image whitePawnImage = new Image("res/white_pawn.png");
	Image whiteQueenImage = new Image("res/white_queen.png");
	Image whiteRookImage = new Image("res/white_rook.png");
	
	public void setUpImages(String colour, String imageString){
		Image image = new Image("res/" + colour + "_" + imageString + ".png");
		images.add(image);
	};
	
	public Rectangle setUpRectangles(Color colour) {
		return new Rectangle(length, length, colour);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//Set up pieces
		final Rectangle blackBishop1 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackBishop2 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackKing = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackKnight1 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackKnight2 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn1 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn2 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn3 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn4 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn5 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn6 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn7 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackPawn8 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackQueen = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackRook1 = new Rectangle(length, length, Color.BLACK);
		final Rectangle blackRook2 = new Rectangle(length, length, Color.BLACK);
		final Rectangle whiteBishop1 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whiteBishop2 = new Rectangle(length, length, Color.BLACK);
		final Rectangle whiteKing = new Rectangle(length, length, Color.WHITE);
		final Rectangle whiteKnight1 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whiteKnight2 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn1 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn2 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn3 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn4 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn5 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn6 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn7 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whitePawn8 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whiteQueen = new Rectangle(length, length, Color.WHITE);
		final Rectangle whiteRook1 = new Rectangle(length, length, Color.WHITE);
		final Rectangle whiteRook2 = new Rectangle(length, length, Color.WHITE);
		blackBishop1.setFill(new ImagePattern(blackBishopImage));
		blackBishop2.setFill(new ImagePattern(blackBishopImage));
		blackKing.setFill(new ImagePattern(blackKingImage));
		blackKnight1.setFill(new ImagePattern(blackKnightImage));
		blackKnight2.setFill(new ImagePattern(blackKnightImage));
		blackPawn1.setFill(new ImagePattern(blackPawnImage));
		blackPawn2.setFill(new ImagePattern(blackPawnImage));
		blackPawn3.setFill(new ImagePattern(blackPawnImage));
		blackPawn4.setFill(new ImagePattern(blackPawnImage));
		blackPawn5.setFill(new ImagePattern(blackPawnImage));
		blackPawn6.setFill(new ImagePattern(blackPawnImage));
		blackPawn7.setFill(new ImagePattern(blackPawnImage));
		blackPawn8.setFill(new ImagePattern(blackPawnImage));
		blackQueen.setFill(new ImagePattern(blackQueenImage));
		blackRook1.setFill(new ImagePattern(blackRookImage));
		blackRook2.setFill(new ImagePattern(blackRookImage));
		whiteBishop1.setFill(new ImagePattern(whiteBishopImage));
		whiteBishop2.setFill(new ImagePattern(whiteBishopImage));
		whiteKing.setFill(new ImagePattern(whiteKingImage));
		whiteKnight1.setFill(new ImagePattern(whiteKnightImage));
		whiteKnight2.setFill(new ImagePattern(whiteKnightImage));
		whitePawn1.setFill(new ImagePattern(whitePawnImage));
		whitePawn2.setFill(new ImagePattern(whitePawnImage));
		whitePawn3.setFill(new ImagePattern(whitePawnImage));
		whitePawn4.setFill(new ImagePattern(whitePawnImage));
		whitePawn5.setFill(new ImagePattern(whitePawnImage));
		whitePawn6.setFill(new ImagePattern(whitePawnImage));
		whitePawn7.setFill(new ImagePattern(whitePawnImage));
		whitePawn8.setFill(new ImagePattern(whitePawnImage));
		whiteQueen.setFill(new ImagePattern(whiteQueenImage));
		whiteRook1.setFill(new ImagePattern(whiteRookImage));
		whiteRook2.setFill(new ImagePattern(whiteRookImage));
		final Group group = new Group(blackBishop1, whiteBishop2, blackKing, whiteQueen);
		setUpLines(group);
		final TranslateTransition transition = createTranslateTransition(blackBishop1);
		final TranslateTransition transition2 = createTranslateTransition(whiteBishop2);
		final Scene scene = new Scene(group, gridsize * (rows + 4), gridsize * (cols + 4));
		blackBishop1.setX(250);
		blackBishop1.setY(140);
		whiteBishop2.setX(250);
		whiteBishop2.setY(560);
		whiteQueen.setX(400);
		whiteQueen.setY(560);
		blackKing.setX(320);
		blackKing.setY(140);
		movePieceOnMousePress(scene, blackBishop1, transition);
		movePieceOnMousePress(scene, whiteBishop2, transition2);

		stage.setScene(scene);
		stage.show();
	}
	
	private void setUpLines(Group group) {
		for(int i = 0; i < rows+1; i++) {
			Line hLine = new Line();
			Line vLine = new Line();
			hLine.setStartX(gridsize *2);
			hLine.setStartY(gridsize *2 + gridsize * i);
			hLine.setEndX(gridsize * (rows + 2));
			hLine.setEndY(gridsize *2 + gridsize * i);
			vLine.setStartX(gridsize *2 + gridsize * i);
			vLine.setStartY(gridsize *2);
			vLine.setEndX(gridsize *2 + gridsize * i);
			vLine.setEndY(gridsize * (rows + 2));
			group.getChildren().add(vLine);
			group.getChildren().add(hLine);
		}
	}

	private TranslateTransition createTranslateTransition(final Rectangle piece) {
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
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				transition.setToX((int)(event.getSceneX()/gridsize) * gridsize + gridsize/4 - piece.getX());
				transition.setToY((int)(event.getSceneY()/gridsize) * gridsize + gridsize/4 - piece.getY());
				transition.playFromStart();
			}
		});
	}
}