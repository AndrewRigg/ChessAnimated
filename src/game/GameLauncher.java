package game;

import board.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class GameLauncher extends Application{

	int gridsize = Literals.GRIDSIZE;
	int rows = Literals.RANKS;
	int cols = Literals.FILES;
	public Scene scene1, scene2, scene3;
	Game game1, game2, game3;
	
	public GameLauncher() {
//		game1 = new Game(0, 10, 1, 40);
		game2 = new Game(false);
//		game3 = new Game(true);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
//		setScene(scene1, stage, game1);
		setScene(scene2, new Stage(), game2);
//		setScene(scene3, new Stage(), game3);
	}
	
	private void setScene(Scene scene, Stage stage, Game game) {
		scene = new Scene(game.board.group, gridsize * (rows + 4), gridsize * (cols + 4));
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
