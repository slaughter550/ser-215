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
    //rows:yCoord
    private String[] alphaTiles = {"A","B","C","D","E","F","G","H","I","J"};

//---------------------------------------------------------------Create Game Board
    public Gameboard(){

        frame = new JFrame();

        panel = new JPanel();
        frame.add(panel);

        this.generateTiles();
        this.placeShips();

        //frame specs
        frame.setResizable(false);
        frame.setSize(320,345);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

//--------------------------------------------------------------------------------
    private void generateTiles() {

        for (int i = 0; i < alphaTiles.length; i++) {
            for (int j = 1; j <= 10; j++) {
                String tileID = (alphaTiles[i] + Integer.toString(j));
                Tile tile = new Tile(tileID);
                panel.add(tile);
                tileMap.put(tileID, tile);
            }
        }
    }
//--------------------------------------------------------------------------------
    private void placeShips(){

        Fleet fleet = new Fleet();

        for (int i=0; i < fleet.getFleetSize(); i++){
            placeShipRandomly(fleet.getShip(i));
        }
    }
//--------------------------------------------------this chunk needs a lot of help
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
                //count occupancies
                int tileSum = 0;
                
                for (int i = 0; i < s.getShipSize(); i++) {
                    Tile tile = tileMap.get(alphaTiles[xCoord + i] + Integer.toString(yCoord));
                    possibleTiles.add(tile);
                    if (tile.isOccupied()) {
                        tileSum++;
                    }
                }
                //exit loop
                if (tileSum == 0){
                    checking = false;
                }
                //redo loop
                else{
                    checking = true;
                }
            }
        }
        

        //place ship: set occupied to true
        for (Tile t: possibleTiles){
            for (Component component : panel.getComponents()) {
                Tile tileToCheck = (Tile)component;
                if (t.getTileID() == tileToCheck.getTileID()) {
                    tileToCheck.setOccupied(true);
                    //test print
                    System.out.println(t.getTileID() + "is now occupied");
                }
            }
        }
    }
}
