import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller extends JPanel {

	private static final long serialVersionUID = 1L;

	private static ArrayList<Ship> computerShips, humanShips;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Battle Ship");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new Controller(), BorderLayout.NORTH);
				frame.add(new Map(), BorderLayout.CENTER);
				frame.pack();
				frame.setBackground(new Color(0x0E1F2C));
				frame.setLocationByPlatform(true);
				frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				frame.setVisible(true);
			}
		});

		initializeShips();
	}

	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), 100);
	}

	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		setBackground(new Color(0x0E1F2C));

		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.lightGray);
		g.drawLine(0, getHeight(), getWidth(), getHeight());

		g.drawString("Human Player", getWidth() * (float) .25, getHeight() - 20);
		g.drawString("Computer Player", getWidth() * (float) .75, getHeight() - 20);
	}

	public static void initializeShips() {

	}
}
