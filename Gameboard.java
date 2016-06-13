import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Jordan on 6/12/2016.
 */
public class Gameboard {

    private JFrame frame;
    private JPanel panel;
    private Map <String, Tile> tileMap = new HashMap<String, Tile>();
    private String[] alphaTiles = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};


    public Gameboard(){
        frame = new JFrame();
        frame.setResizable(false);

        panel = new JPanel();
        frame.add(panel);

        this.generateTiles();
        this.placeShips();

        frame.setSize(320,345);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    private void generateTiles(){

        for (int i = 0; i < alphaTiles.length; i++){
            for (int j = 1; j <= 10; j++){
                String tileID = (alphaTiles[i] + Integer.toString(j));
                Tile tile = new Tile(tileID);
                panel.add(tile);
                tileMap.put(tileID, tile);
            }
        }
    }

    private void placeShips(){

        Fleet fleet = new Fleet();

        for (int i=0; i < fleet.getFleetSize(); i++){

            placeShipRandomly(fleet.getShip(i));

        }
    }

    private void placeShipRandomly(Ship s) {
        Random rand = new Random();

        boolean checking = true;

        ArrayList <Tile> possibleTiles = new ArrayList<Tile>();

        while (checking) {

            possibleTiles.clear();

            int xCoord = rand.nextInt(10 - s.getShipSize());
            int yCoord = rand.nextInt(10 - s.getShipSize())+1;

            //TODO: check orientation before setting possible coordinates
//            boolean xOriented = Math.random() < 0.5;
            boolean yOriented = true;


            //TODO: if x oriented
            if (yOriented) {

                int tileSum = 0;
                for (int i = 0; i < s.getShipSize(); i++) {
                    Tile tile = tileMap.get(alphaTiles[xCoord + i] + Integer.toString(yCoord));
                    possibleTiles.add(tile);
                    if (tile.isOccupied()) {
                        tileSum++;
                    }
                }
                if (tileSum == 0){

                    checking = false;

                }
                else{
                    checking = true;
                }

            }

        }

        for (Tile t: possibleTiles){
            for (Component component : panel.getComponents()) {
                Tile tileToCheck = (Tile)component;
                if (t.getTileID() == tileToCheck.getTileID()) {
                    tileToCheck.setOccupied(true);
                    System.out.println(t.getTileID() + "is now occupied");
                }
            }
        }


    }


    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
