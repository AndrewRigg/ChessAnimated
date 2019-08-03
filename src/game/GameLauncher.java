package game;

public class GameLauncher {

	Game game;
	
	public GameLauncher() {
		this(new Game());
	}
	
	public GameLauncher(Game game) {
		this.game = game;
	}	
}
