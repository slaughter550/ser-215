import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ship extends JLabel {

	private int xPosition;
	private int yPosition;
	private int shipLength;
	private int shipType;
	private boolean xOriented;

	public final static int ptBoat = 0;
	public final static int sub = 1;
	public final static int destroyer = 2;
	public final static int battleship = 3;
	public final static int carrier = 4;

	private static final Integer[] typeSizes = { 2, 3, 3, 4, 5 };
	private ArrayList<Integer> hits = new ArrayList<Integer>();

	public Ship(int x, int y, int type, boolean xOriented) {
		super(getImage(type, 80, 25));

		xPosition = x;
		yPosition = y;
		shipType = type;
		shipLength = typeSizes[type];
		this.xOriented = xOriented;
	}

	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		setBackground(new Color(0x0E1F2C));

		Graphics2D g = (Graphics2D) g1;
		Color c = g.getColor();
		g.setColor(new Color(0x9C4141));

		for (int i = 0; i < hits.size(); i++) {
			if (getType() == ptBoat) {
				Ellipse2D ellipse = new Ellipse2D.Double(10 * i * (3 / getLength() + getLength() * 2) + 40, 18, 10, 10);
				g.fill(ellipse);
			}

			else if (getType() == sub || getType() == destroyer) {
				Ellipse2D ellipse = new Ellipse2D.Double(10 * i * (2 / getLength() + getLength()) + 40, 18, 10, 10);
				g.fill(ellipse);
			} else if (getType() == battleship) {
				Ellipse2D ellipse = new Ellipse2D.Double(10 * i * (2 / getLength() + 2) + 40, 18, 10, 10);
				g.fill(ellipse);
			} else if (getType() == carrier) {
				Ellipse2D ellipse = new Ellipse2D.Double(10 * i * (4 / getLength() + 1.6) + 40, 18, 10, 10);
				g.fill(ellipse);
			}

		}
		g.setColor(c);
	}

	public static ImageIcon getImage(int type, int width, int height) {
		ImageIcon img = new ImageIcon("images/" + type + "-h.png");
		Image scale = img.getImage();

		Image newImg = scale.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(newImg);
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
		if (xOriented && x >= xPosition && x < xPosition + getLength() && yPosition == y) {
			return true;
		} else if (!xOriented && y >= yPosition && y < yPosition + getLength() && xPosition == x) {
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
		return xOriented ? x - xPosition : y - yPosition;
	}

	public boolean isSunk() {
		return hits.size() == getLength();
	}

	public boolean isXOriented() {
		return xOriented;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public int getLength() {
		return shipLength;
	}

	public String toString() {
		return String.format("X - %s, Y - %s, Size - %s, Orientation - %s", xPosition, yPosition, getLength(),
				xOriented ? "x-oriented" : "y-oriented");
	}

	public int getType() {
		return shipType;
	}

	public ArrayList<Integer> getHits() {
		return hits;
	}
}
