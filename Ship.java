import java.util.ArrayList;
import java.util.Random;

public class Ship {

	private int x, y, size;
	private boolean xOriented;

	private ArrayList<Integer> hits;

	public Ship(int x, int y, int size, boolean xOriented) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.xOriented = xOriented;
	}

	public static Ship createRandom(int size) {
		Random r = new Random();
		boolean xOriented = r.nextBoolean();
		int x = r.nextInt(11), y = r.nextInt(11);
		if (xOriented) {
			x = r.nextInt(11 - size) + 1;
		} else {
			y = r.nextInt(11 - size) + 1;
		}

		return new Ship(x, y, size, xOriented);
	}

	public void hit(int i) {
		if (!hits.contains(i)) {
			hits.add(i);
		}
	}

	public boolean isCordinate(int x, int y) {
		if (xOriented && x >= this.x && x < this.x + size && this.y == y) {
			return true;
		} else if (!xOriented && y >= this.y && y < this.y + size && this.x == x) {
			return true;
		}

		return false;
	}

	public boolean hitCordinate(int x, int y) {
		boolean hit = isCordinate(x, y);

		if (hit) {
			hit(cordinateToShipIndex(x, y));
		}

		return hit;
	}

	public boolean isCordinateHit(int x, int y) {
		return hits.contains(cordinateToShipIndex(x, y));
	}

	private int cordinateToShipIndex(int x, int y) {
		return xOriented ? x - this.x : y - this.y;
	}

	public boolean isSunk() {
		return hits.size() == size;
	}

	public boolean isXOriented() {
		return xOriented;
	}

	public int getSize() {
		return size;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return String.format("X - %s, Y - %s, Size - %s, Orientation - %s", x, y, size,
				xOriented ? "x-oriented" : "y-oriented");
	}
}
