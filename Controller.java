import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class Controller extends JPanel {

	private static final long serialVersionUID = 1L;

	public ShipList computerShips, humanShips;
	public ArrayList<Integer[]> computerMisses, humanMisses;
	public boolean gameRunning = true;

	public Thread timerCron;
	public JLabel timer;
	public JFrame frame;

	private ArrayList<Point> computerGuesses, educatedGuesses;

	public Controller(JFrame frame) {
		super(new BorderLayout());
		this.frame = frame;
		setBackground(new Color(0x0E1F2C));

		initializeShips();

		initTimer();
		startTimer();
		addFleetStatus();

		computerMisses = new ArrayList<Integer[]>();
		humanMisses = new ArrayList<Integer[]>();

		educatedGuesses = new ArrayList<Point>();
		computerGuesses = new ArrayList<Point>();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Battleship");
				Controller c = new Controller(frame);

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

		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.lightGray);
		g.drawLine(0, getHeight(), getWidth(), getHeight());
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

	public void addFleetStatus() {
		JPanel compFleet = new JPanel();
		compFleet.setLayout(new GridLayout(3, 2));
		compFleet.setBackground(Color.darkGray);
		JPanel playerFleet = new JPanel();
		playerFleet.setLayout(new GridLayout(3, 2));
		playerFleet.setBackground(Color.darkGray);

		JTextArea player = new JTextArea(" Your Fleet ");
		JTextArea comp = new JTextArea(" Enemy Fleet ");
		comp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		player.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		Font labelFont = new Font("Helvetica", Font.BOLD, 24);
		player.setForeground(Color.WHITE);
		comp.setForeground(Color.WHITE);
		player.setFont(labelFont);
		comp.setFont(labelFont);
		comp.setBackground(Color.darkGray);
		player.setBackground(Color.darkGray);

		playerFleet.setBorder(BorderFactory.createRaisedBevelBorder());
		compFleet.setBorder(BorderFactory.createRaisedBevelBorder());

		compFleet.add(comp);
		playerFleet.add(player);

		for (Ship ship : computerShips) {
			compFleet.add(ship);
		}

		add(compFleet, BorderLayout.EAST);

		for (Ship ship : humanShips) {
			playerFleet.add(ship);
		}

		add(playerFleet, BorderLayout.WEST);
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

	public void gameOver(Graphics2D g) {
		gameOver(g, false);
	}

	public void gameOver(Graphics2D g, boolean surrenderd) {

		String path = null;

		if (surrenderd) {
			path = "images/surrender.png";
		} else if (computerShips.isSunk()) {
			path = "images/victory.png";
		} else if (humanShips.isSunk()) {
			path = "images/defeat.png";
		}

		if (path != null) {
			frame.remove(this);
			ImageIcon img = new ImageIcon(path);
			int scaledHeight = 0;
			int scaledWidth = 0;

			while (scaledHeight < frame.getHeight()) {
				scaledHeight++;
				scaledWidth = (int) (scaledHeight * ((double) img.getIconWidth() / (double) img.getIconHeight()));
			}

			while (scaledWidth < frame.getWidth()) {
				scaledWidth++;
				scaledHeight = (int) (scaledWidth * ((double) img.getIconHeight() / (double) img.getIconWidth()));
			}

			g.drawImage(img.getImage(), 0, 0, scaledWidth, scaledHeight, null);
		}
	}

	public void computerMove() {
		Point guess = null;
		Random r = new Random();
		int x, y;

		// make educated guess
		if (!educatedGuesses.isEmpty()) {
			// choose random point from smart moves list
			int index = r.nextInt(educatedGuesses.size());
			guess = educatedGuesses.get(index);

			educatedGuesses.remove(index);
			computerGuesses.add(guess);

			x = (int) guess.getX();
			y = (int) guess.getY();
		} else {
			while (true) {
				x = r.nextInt(10);
				y = r.nextInt(10);

				guess = new Point(x, y);

				if (!computerGuesses.contains(guess)) {
					computerGuesses.add(guess);
					break;
				}
			}

			x = (int) guess.getX();
			y = (int) guess.getY();
		}
		System.out.println("Computer Guess: " + guess);
		// use coordinate
		Ship s = humanShips.shipAtCordinates(x, y);
		// hit
		if (s != null) {
			s.hitCordinate(x, y);
			// get possible smart moves based on hit coordinate
			// check x and y values are in range
			if (x < 10) {
				addEducatedGuess(new Point((x + 1), y));
			}
			if (x > 0) {
				addEducatedGuess(new Point((x - 1), y));
			}
			if (y < 10) {
				addEducatedGuess(new Point(x, (y + 1)));
			}
			if (y > 0) {
				addEducatedGuess(new Point(x, (y - 1)));
			}

			System.out.println("Educated Guesses");
			for (Point p : educatedGuesses) {
				System.out.println(p);
			}
			// if hit - second turn
			computerMove();
		} else {
			Integer[] mp = { x, y };
			computerMisses.add(mp);
		}
	}

	public void addEducatedGuess(Point p) {
		if (!computerGuesses.contains(p)) {
			educatedGuesses.add(p);
		}
	}
}
