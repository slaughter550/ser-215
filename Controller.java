<<<<<<< HEAD
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
    		frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Victory.png")))));
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
    		return true;
    	}
    		
    	else if (humanHit == 17){
    		frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Defeat.png")))));
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			
    		return true;
    		}
    	else if (surrender == true){
    		frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Surrender.png"))))); 			
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    		return true;
    		}
    	return false;
	}
}
=======
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Controller extends JPanel {

	private static final long serialVersionUID = 1L;

	public ShipList computerShips, humanShips;
	public ArrayList<Integer[]> computerMisses, humanMisses;
	public boolean gameRunning = true;

	public Thread timerCron;
	public JLabel timer;

	public Controller() {
		super(new BorderLayout());
		initializeShips();

		initTimer();
		startTimer();

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

	public void initTimer() {
		timer = new JLabel();
		timer.setForeground(new Color(0x69E692));
		timer.setFont(new Font("Helvetica", Font.BOLD, 42));
		timer.setHorizontalAlignment(SwingConstants.CENTER);

		add(timer, BorderLayout.CENTER);
	}

	public void startTimer() {
		timerCron = (new Thread() {
			public void run() {
				long startTime = System.currentTimeMillis();
				while (gameRunning) {
					long miliseconds = System.currentTimeMillis() - startTime;
					long seconds = (miliseconds / 1000) % 60;
					long minutes = (int) (miliseconds / 1000 / 60);

					timer.setText(String.format("%02d:%02d", minutes, seconds));

					try {
						sleep(500);
					} catch (Exception e1) {
					}
				}
			}
		});
		timerCron.start();
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
}
>>>>>>> refs/remotes/origin/master
