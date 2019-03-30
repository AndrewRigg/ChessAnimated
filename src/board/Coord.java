package board;
//Is this class needed?
public class Coord {

	@Override
	public String toString() {
		return ("Coords: (" + x + ", " + y + ")");
	}

	private int x, y;
	private double realX, realY;
	int gridx;
	char gridy; 
	
	public Coord(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setRealX(x * Literals.GRIDSIZE);
		this.setRealY(y * Literals.GRIDSIZE);
		this.gridx = Literals.EIGHTH_ROW - x + 1;
		this.gridy = (char)(y - 1 + Literals.ASCII_CAPS);
	}

	public void setRealY(double realY) {
		this.realY = realY;
	}

	public void setRealX(double realX) {
		this.realX = realX;
	}
	
	public double getRealX() {
		return realX;
	}

	public double getRealY() {
		return realY;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.gridy = (char)(y - 1 + Literals.ASCII_CAPS);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.gridx = Literals.EIGHTH_ROW - x + 1;
	}
	
	public boolean equals(Coord coord) {
		return (this.getX() == coord.getX() && this.getY() == coord.getY());
	}
	
	public boolean onGrid() {
		//System.out.println("X is " + x + " Y is " + y);
		return (x > 1 && x < 10 && y > 1 && y < 10);
	}
}
