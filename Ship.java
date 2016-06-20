import java.util.ArrayList;
import java.util.Random;

public class Ship {

	private int x, y, size, type;
	private boolean xOriented;

	public final static int ptBoat = 0;
	public final static int sub = 1;
	public final static int destroyer = 2;
	public final static int battleship = 3;
	public final static int carrier = 4;

	private static final Integer[] typeSizes = { 2, 3, 3, 4, 5 };
	private ArrayList<Integer> hits = new ArrayList<Integer>();

	public Ship(int x, int y, int type, boolean xOriented) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.size = typeSizes[type];
		this.xOriented = xOriented;
	}

	public static Ship createRandom(int type) {
		Random r = new Random();
		int size = typeSizes[type];
		boolean xOriented = r.nextBoolean();
		int x = r.nextInt(10), y = r.nextInt(10);
		if (xOriented) {
			x = r.nextInt(10 - size);
		} else {
			y = r.nextInt(10 - size);
		}

		return new Ship(x, y, type, xOriented);
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

	public int getType() {
		return type;
	}

	public ArrayList<Integer> getHits() {
		return hits;
	}
	public boolean gameOver(){
    	if ( computerHit == 17){
    		// JFrame Victory
    		return true;
    		
    		else if (humanHit == 17){
    		// JFrame Defeat
    			return true;
    		}
    		else if (surrender == true){
    		// JFrame Surrender
    			return true;
    		}
    	}
    	return false;
}
