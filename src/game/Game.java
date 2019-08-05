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
	private static int defaultMinutes = 20, defaultSeconds = 00;
	
	/**
	 * Game with no clocks
	 */
	public Game() {
		this(false);
	}
	
	/**
	 * Game with both default clocks (or both without)
	 * @param clocks
	 */
	public Game(boolean clocks) {
		this(clocks, clocks);
	}
	
	/**
	 * 	Game with up to two default clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(boolean clock1, boolean clock2) {
		this(clock1, defaultMinutes, defaultSeconds, clock2, defaultMinutes, defaultSeconds);
	}
	
	/**
	 * Game with two custom clocks
	 * @param clock1
	 * @param clock2
	 */
	public Game(boolean clock1, int clock1Minutes, int clock1Seconds, boolean clock2, int clock2Minutes, int clock2Seconds) {
		board = new Board();
		gridsize = Literals.GRIDSIZE;
		rows = Literals.RANKS;
		cols = Literals.FILES;
		player1 = clock1 ? new Player(Colour.WHITE, clock1Minutes, clock1Seconds) : new Player(Colour.WHITE);
		player2 = clock2 ? new Player(Colour.BLACK, clock2Minutes, clock2Seconds) : new Player(Colour.BLACK);
		controller = new Controller(board, player1, player2);
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
