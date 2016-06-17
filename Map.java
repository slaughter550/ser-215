import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;
	float width, height, middleX, middleY;
	final int offset = 30;

	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}

	public void paintComponent(Graphics g1) {
		setVars();

		Graphics2D g = (Graphics2D) g1;
		drawBoard(g);
	}

	public void setVars() {
		width = getWidth();
		height = getHeight();
		middleX = (width / 2) + (offset / 2);
		middleY = (height / 2) + (offset / 2);
	}

	public void drawBoard(Graphics2D g) {

		//Separator Line
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(10));
		g.draw(new Line2D.Double(middleX, 0, middleX, height));
		g.setStroke(oldStroke);

		float squareWidth = (width - offset) / 20;
		float squareHeight = (height - offset) / 10;

		float squareStretch = 30;

		char[] alphabet = "abcdefghij".toCharArray();

		for (float i = 0, x, y; i < 10; i++) {
			x = i * squareWidth + offset;
			y = i * squareHeight + offset;

			//Computer Columns
			g.drawString(String.valueOf((int) i + 1), (squareWidth / 2) + x - 4, 20);
			g.draw(new Rectangle2D.Double(x, 0, squareWidth, squareStretch));
			
			//Human Columns
			g.drawString(String.valueOf((int) i + 1), (middleX - offset) + (squareWidth / 2) + x - 4, 20);
			g.draw(new Rectangle2D.Double((middleX - offset) + x, 0, squareWidth, squareStretch));
			
			//Computer Rows
			float letterColumnY = (squareHeight / 2) + y + 4;
			g.draw(new Rectangle2D.Double(0, y, squareStretch, squareHeight));
			g.drawString(String.valueOf(alphabet[(int) i]), 10, letterColumnY);
		}
		
		//Draw Grid
		for(float i = 0, x, y; i < 20; i++) {
			for(float j = 0; j < 10; j++) {
				x = i * squareWidth + offset;
				y = j * squareHeight + offset;
				
				g.draw(new Rectangle2D.Double(x, y, squareWidth, squareHeight));
			}
		}
	}
}