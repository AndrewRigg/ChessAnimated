package game;

import java.io.File;

public class PGNToGameConverter {

	File pgnFile;
	Game game;
	
	PGNToGameConverter(File pgnFile){
		this.pgnFile = pgnFile;
		game = convertPGNFileToGame(pgnFile);
	}

	private Game convertPGNFileToGame(File pgnFile) {
		
		return null;
	}
}
