package game;

import board.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class GameLauncher extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setScene(
		new Game());
//		new Game());
//		new Game(false));
//		new Game(0, 13, 1, 40));
	}
	
	private void setScene(Game game) {
		Scene scene = new Scene(game.board.group, 
				Utils.GRIDSIZE * (Utils.RANKS + 4), 
				Utils.GRIDSIZE * (Utils.FILES + 4));
		Stage stage = new Stage();
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
