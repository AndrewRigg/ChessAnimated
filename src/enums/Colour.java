package enums;


import javafx.scene.paint.Color;
public enum Colour {
	
	WHITE(Color.WHITE), BLACK(Color.BLACK);
	
	private final Color colour;
	
	Colour(Color colour){
		this.colour = colour;
	}
	
	public Color getColour() {
		return colour;
	}
}
