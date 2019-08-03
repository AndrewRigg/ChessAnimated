package board;

import javafx.util.Duration;

/**
 * @author andrew.rigg
 */
public class Literals {

	/**
	 * 
	 */
	public static final int GRIDSIZE = 60,
							RANKS = 8,
							FILES = 8,
							ASCII_CAPS = 64,
							ASCII_SMALLS = 96,
							FIRST_ROW = 2,
							SECOND_ROW = 3,
							THIRD_ROW = 4,
							FOURTH_ROW = 5,
							FIFTH_ROM = 6,
							SIXTH_ROW = 7,
							SEVENTH_ROW = 8,
							EIGHTH_ROW = 9,
							BOARD_END = 11,
							BOARD_OFFSET_X = 2,
							BOARD_OFFSET_Y = 2;
	
	/**
	 * Below are a list of commonly used points systems which can be set for the type enums
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
	
	public static final Duration TRANSLATE_DURATION = Duration.seconds(0.5); 
	
	public static final boolean BOARD_DEBUG 	= false,
								PIECE_DEBUG 	= false,
								PAWN_DEBUG  	= false,
								KING_DEBUG  	= false,
								PLAYER_DEBUG 	= false,
								COORD_DEBUG 	= false,
								CLOCK_DEBUG		= true;
	
	public static final void print(String str, boolean bool) {
		if(bool) {
			System.out.println(str);
		}
	}
	
}
