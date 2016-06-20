import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controller extends JPanel {

	private static final long serialVersionUID = 1L;

	public ShipList computerShips, humanShips;
	public ArrayList<Integer[]> computerMisses, humanMisses;

	public Controller() {
		initializeShips();
		
		computerMisses = new ArrayList<Integer[]>();
		humanMisses = new ArrayList<Integer[]>();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Controller c = new Controller();

				JFrame frame = new JFrame("Battleship");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(c, BorderLayout.NORTH);
				frame.add(new Map(c), BorderLayout.CENTER);
				frame.pack();
				frame.setBackground(new Color(0x0E1F2C));
				frame.setLocationByPlatform(true);
				frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				frame.setVisible(true);
			}
		});
	}

	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), 150);
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

	public void initializeShips() {
		computerShips = new ShipList();
		humanShips = new ShipList();
		Ship ship;

		Integer[] types = { Ship.ptBoat, Ship.sub, Ship.carrier, Ship.battleship, Ship.destroyer };
		for (int i : types) {
			do {
				ship = Ship.createRandom(i);
			} while (computerShips.shipAlreadyExists(ship));
			computerShips.add(ship);

			do {
				ship = Ship.createRandom(i);
			} while (humanShips.shipAlreadyExists(ship));
			humanShips.add(ship);
		}
		
		System.out.println(computerShips);
	}
	public boolean gameOver(){
    	if ( computerHit == 17){
    		JFrame F = new JFrame("Victory");
    		
    		try{
    			F.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Victory.png")))));
    		}catch(IOException e)
    		{
    			System.out.println("Image Doesn't exist");
    		}
    		F.setResizable(false);
    		F.pack();
    		F.setVisible(true);
    		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	}
    		return true;
    		
    		else if (humanHit == 17){
    			JFrame F = new JFrame("Defeat");
    			
    			try{
    				F.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Defeat.png")))));
    			}catch(IOException e)
    			{
    				System.out.println("Image Doesn't exist");
    			}
    			F.setResizable(false);
    			F.pack();
    			F.setVisible(true);
    			F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		}
    			return true;
    		}
    		else if (surrender == true){
    			JFrame F = new JFrame("Surrender");
    			
    			try{
    				F.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Surrender.png")))));
    			}catch(IOException e)
    			{
    				System.out.println("Image Doesn't exist");
    			}
    			F.setResizable(false);
    			F.pack();
    			F.setVisible(true);
    			F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		}
    			return true;
    		}
    	}
    	return false;
}
