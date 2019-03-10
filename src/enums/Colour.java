package enums;


import javafx.scene.paint.Color;
public enum Colour {
	
	WHITE(Color.WHITE), BLACK(Color.BLACK), 
	GREY(Color.GREY), GREEN(Color.GREY), 
	BLUE(Color.GREY), RED(Color.GREY), 
	YELLOW(Color.GREY), ORANGE(Color.GREY), 
	PURPLE(Color.GREY), DARK_GREY(Color.DARKGRAY),
	LIGHT_GREY(Color.LIGHTGREY);
	
	private final Color colour;
	
	Colour(Color colour){
		this.colour = colour;
	}
	
	public Color getColour() {
		return colour;
	}
}
