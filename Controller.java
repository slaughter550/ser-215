import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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

	public Controller() {
		super(new BorderLayout());
		initializeShips();

		initTimer();
		startTimer();
		addFleetStatus();

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
}
