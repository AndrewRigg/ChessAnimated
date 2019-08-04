package game;

import board.*;
import enums.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class Game extends Application{

	Controller controller;
	public Scene scene;
	Board board;
	Player player1, player2;
	int gridsize, rows, cols;
	
	public Game() {
		board = new Board();
		//	Game with no clocks
		player1 = new Player(Colour.WHITE);
		player2 = new Player(Colour.BLACK);
		//	Game with default clocks
		//player1 = new Player(Colour.WHITE, true);
		//player2 = new Player(Colour.BLACK, true);
		//	Game with custom clocks
		//player1 = new Player(Colour.WHITE, 0, 5);
		//player2 = new Player(Colour.BLACK, 1, 8);
		controller = new Controller(board, player1, player2);
		gridsize = Literals.GRIDSIZE;
		rows = Literals.RANKS;
		cols = Literals.FILES;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setScene(stage);
	}
	
	private void setScene(Stage stage) {
		scene = new Scene(board.group, gridsize * (rows + 4), gridsize * (cols + 4));
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
