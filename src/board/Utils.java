package board;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * @author andrew.rigg
 */
public class Utils {

	/**
	 * The following values are the row and column numbers (which are all offset) 
	 * and other useful numerical parameters
	 */
	public static final int GRIDSIZE = 60,
							RANKS = 8,
							FILES = RANKS,
							ASCII_CAPS = 64,
							ASCII_SMALLS = 96,
							BOARD_OFFSET_X = 2,
							BOARD_OFFSET_Y = 2,
							DEFAULT_MINUTES = 20,
							DEFAULT_SECONDS = 0,
							FIRST_ROW = BOARD_OFFSET_X,
							SECOND_ROW = FIRST_ROW + 1,
							THIRD_ROW = SECOND_ROW + 1,
							FOURTH_ROW = THIRD_ROW + 1,
							FIFTH_ROM = FOURTH_ROW + 1,
							SIXTH_ROW = FIFTH_ROM + 1,
							SEVENTH_ROW = SIXTH_ROW + 1,
							EIGHTH_ROW = SEVENTH_ROW + 1,
							FIRST_COLUMN = BOARD_OFFSET_Y,
							SECOND_COLUMN = FIRST_COLUMN + 1,
							THIRD_COLUMN = SECOND_COLUMN + 1,
							FOURTH_COLUMN = THIRD_COLUMN + 1,
							FIFTH_COLUMN = FOURTH_COLUMN + 1,
							SIXTH_COLUMN = FIFTH_COLUMN + 1,
							SEVENTH_COLUMN = SIXTH_COLUMN + 1,
							EIGHTH_COLUMN = SEVENTH_COLUMN + 1,
							BOARD_END = EIGHTH_COLUMN + BOARD_OFFSET_Y;
	
	/**
	 * Below are a list of commonly used points systems which can be set for the 'Type' enums
	 */
	public static final double [] 	STANDARD 	= {3, 3, 5, 9, 1000000, 1},
									SARRATT 	= {3.1, 3.3, 5, 7.9, 2.2, 1},
									PHILIDOR	= {3.05, 3.5, 5.48, 9.94, 1000000, 1},
									PRATT 		= {3, 3, 5, 10, 1000000, 1},
									BILGUER = {3.5, 3.5, 5.7, 10.3, 1000000, 1},
									EUWE = {3.5, 3.5, 5.5, 10, 1000000, 1},
									LASKER = {3.5, 3.5, 5, 8.5, 4, 1},
									HOROWITZ = {3, 3.001, 5, 9, 1000000, 1},
									EVANS = {3.5, 3.5, 5, 10, 4, 1},
									STYEKLOV = {3.5, 3.5, 5, 9.5, 1000000, 1},
									FISCHER = {3, 3.25, 5, 9, 1000000, 1},
									EUROPEAN = {3, 3, 4.5, 8.5, 1000000, 1},
									KASPAROV = {3, 3, 4.5, 9, 1000000, 1},
									SOVIET = {3, 3, 5, 10, 1000000, 1},
									KAUFMAN = {3.5, 3.5, 5.25, 10, 1000000, 1},
									BERLINER = {3.2, 3.33, 5.1, 8.8, 1000000, 1},
									KURZDORFER = {3.5, 3.5, 5, 9, 1000000, 1},
									HOOPER = {4, 3.5, 7, 3.5, 4, 1},
									GIK = {2.4, 4.0, 6.4, 10.4, 3.0, 1},
									MEDIAN = {3.075, 3.315, 5, 9.5, 4, 1},
									AVERAGE = {3.2, 3.3, 5.2, 9.6, 3.5, 1};		
	
	public static final String EMPTY = "";
	
	public static final Duration TRANSLATE_DURATION = Duration.millis(500); 
	
	public static final boolean BOARD_DEBUG 		= true,
								PIECE_DEBUG 		= true,
								PAWN_DEBUG  		= false,
								KING_DEBUG  		= false,
								PLAYER_DEBUG 		= false,
								COORD_DEBUG 		= false,
								CLOCK_DEBUG			= false, 
								CONTROLLER_DEBUG	= true;
	
	public static final Font clockFont = Font.font("Century Gothic", FontWeight.MEDIUM, FontPosture.REGULAR, 20);
	public static final Color run = Color.web("#00CC00"),
								wait = Color.web("#000000"),
								losing = Color.web("#CC7700"),
								lost = Color.web("FF0000");
	
	
	public static final void print(String str, boolean bool) {
		if(bool) {
			System.out.println(str);
		}
	}
	
}
