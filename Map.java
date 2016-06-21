import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	Controller controller;
	float width, height, middleX, middleY, squareWidth, squareHeight;
	final float offset = 30;
	final Color bgColor = new Color(0x0E1F2C);

	public Map(Controller c) {
		controller = c;
		addMouseListener(this);
	}

	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}

	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		setBackground(bgColor);
		setVars();

		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.lightGray);
		drawBoard(g);
		drawShips(g);
		drawHits(g);
		drawMisses(g);
		controller.gameOver(g);
	}

	public void drawMisses(Graphics2D g) {
		for (Integer[] point : controller.humanMisses) {
			float squareX = middleX + (point[0] * squareWidth) + 2;
			float squareY = (point[1] * squareHeight) + offset + 2;

			Ellipse2D ellipse = new Ellipse2D.Double(squareX, squareY, squareWidth - 4, squareHeight - 4);
			Color c = g.getColor();
			g.setColor(new Color(0x31B275));
			g.fill(ellipse);
			g.setColor(c);
		}
	}

	public void drawHits(Graphics2D g) {
		for (Ship ship : controller.computerShips) {
			ship.getHits().forEach((i) -> {
				float shipX = middleX + (ship.getXPosition() * squareWidth);
				float shipY = (ship.getYPosition() * squareHeight) + offset;

				if (ship.isXOriented()) {
					shipX += i * squareWidth;
				} else {
					shipY += i * squareHeight;
				}

				Rectangle2D rect = new Rectangle2D.Double(shipX, shipY, squareWidth, squareHeight);
				Color c = g.getColor();
				g.setColor(new Color(0x560809));
				g.fill(rect);
				g.setColor(c);
			});
		}
	}

	public void drawShips(Graphics2D g) {
		for (Ship ship : controller.humanShips) {
			float shipWidth = (ship.isXOriented() ? squareWidth * ship.getLength() : squareWidth) - 10;
			float shipHeight = (ship.isXOriented() ? squareHeight : squareHeight * ship.getLength()) - 10;

			float shipX = (ship.getXPosition() * squareWidth) + offset + 5;
			float shipY = (ship.getYPosition() * squareHeight) + offset + 5;

			try {
				BufferedImage img = ImageIO.read(new File("images/" + ship.getType() + (ship.isXOriented() ? "-h" : "") + ".png"));
				Rectangle2D rect = new Rectangle2D.Double(shipX, shipY, shipWidth, shipHeight);
				TexturePaint imagep = new TexturePaint(img, rect);

				Paint p = g.getPaint();
				g.setPaint(imagep);
				g.fill(rect);
				g.setPaint(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setVars() {
		width = getWidth();
		height = getHeight();
		middleX = (width / 2) + (offset / 2);
		middleY = (height / 2) + (offset / 2);

		squareWidth = (width - offset) / 20;
		squareHeight = (height - offset) / 10;
	}

	public void drawBoard(Graphics2D g) {

		// Separator Line
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(10));
		g.draw(new Line2D.Double(middleX, 0, middleX, height));
		g.setStroke(oldStroke);

		char[] alphabet = "abcdefghij".toCharArray();

		for (float i = 0, x, y; i < 10; i++) {
			x = i * squareWidth + offset;
			y = i * squareHeight + offset;

			// Computer Columns
			g.drawString(String.valueOf((int) i + 1), (squareWidth / 2) + x - 4, 20);
			g.draw(new Rectangle2D.Double(x, 0, squareWidth, offset));

			// Human Columns
			g.drawString(String.valueOf((int) i + 1), (middleX - offset) + (squareWidth / 2) + x - 4, 20);
			g.draw(new Rectangle2D.Double((middleX - offset) + x, 0, squareWidth, offset));

			// Computer Rows
			float letterColumnY = (squareHeight / 2) + y + 4;
			g.draw(new Rectangle2D.Double(0, y, offset, squareHeight));
			g.drawString(String.valueOf(alphabet[(int) i]), 10, letterColumnY);
		}

		// Draw Grid
		for (float i = 0, x, y; i < 20; i++) {
			for (float j = 0; j < 10; j++) {
				x = i * squareWidth + offset;
				y = j * squareHeight + offset;

				g.draw(new Rectangle2D.Double(x, y, squareWidth, squareHeight));
			}
		}
	}

	public void translateClickToSquare(Point p) {
		if (p.getY() <= offset || p.getY() <= offset) {
			// Click off the map in the gutter
		} else if (p.getX() >= middleX) {
			double clickXCord = p.getX() - middleX;
			double clickYCord = p.getY() - offset;

			int clickX = (int) Math.ceil(clickXCord / squareWidth) - 1;
			int clickY = (int) Math.ceil(clickYCord / squareHeight) - 1;

			Ship s = controller.computerShips.shipAtCordinates(clickX, clickY);
			if (s != null) {
				s.hitCordinate(clickX, clickY);
			} else {
				Integer[] mp = { clickX, clickY };
				controller.humanMisses.add(mp);
			}

			repaint();
			controller.repaint();
		} else {
			// Clicked on their own side
		}
	}

	public void mouseClicked(MouseEvent e) {
		translateClickToSquare(e.getPoint());
	}

	public void mousePressed(MouseEvent e) {
		//
	}

	public void mouseReleased(MouseEvent e) {
		//
	}

	public void mouseEntered(MouseEvent e) {
		//
	}

	public void mouseExited(MouseEvent e) {
		//
	}
}