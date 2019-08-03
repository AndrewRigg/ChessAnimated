package game;

import java.io.*;

public class PGNReader {

	
	File pgnFile;
	BufferedReader bufferedReader;
	FileReader fileReader;
	
	PGNReader(File pgnFile){
		try {
			fileReader = new FileReader(pgnFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferedReader = new BufferedReader(fileReader);
	}
}
