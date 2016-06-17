import java.util.ArrayList;

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

	public void hit(int i) {
		if (!hits.contains(i)) {
			hits.add(i);
		}
	}

	public boolean hitCordinate(int x, int y) {
		boolean hit = false;
		if (xOriented && this.x >= x && this.x + size < x) {
			hit = true;
		} else if (!xOriented && this.y >= y && this.y + size < y) {
			hit = true;
		}

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
}
