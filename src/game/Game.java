package game;

import board.*;
import enums.Colour;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game extends Application{

	Controller controller;
	public Scene scene;
	int gridsize = Literals.GRIDSIZE;
	int rows = Literals.RANKS;
	int cols = Literals.FILES;
	//final Group group = new Group();
	Board board = new Board();
	
	public Game() {
		controller = new Controller(board, new Player(Colour.WHITE), new Player(Colour.BLACK));
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		
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
