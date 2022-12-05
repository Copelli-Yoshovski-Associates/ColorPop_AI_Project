package application.model;

public class Point {

	final int x;
	final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return "(X: " + x + ", Y: " + y + ")";
	}


}
