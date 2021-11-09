package board;

public class Coord {

	private int x, y;
	
	public Coord(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public boolean onGrid() {
		return (x >= Utils.FIRST_ROW && x <= Utils.EIGHTH_ROW && 
				y >= Utils.FIRST_COLUMN && y <= Utils.EIGHTH_COLUMN);
	}
	
	public String toString() {
		return "Coordinate: x=" + x + ", y=" + y; 
	}
}
