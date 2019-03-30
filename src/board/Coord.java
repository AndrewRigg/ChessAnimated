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
		return (x > 1 && x < 10 && y > 1 && y < 10);
	}
}
