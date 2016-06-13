import javax.swing.*;
import java.awt.*;

/**
 * Created by Jordan on 6/12/2016.
 */
public class Tile extends JButton {

    private String tileID;
    private boolean occupied = false;

//    ImageIcon water = createImageIcon("resources/oceanpic.jpg");


    public Tile(String id) {
        this.setTileID(id);
        this.setPreferredSize(new Dimension(25, 25));
        this.addActionListener(new TileListener());
    }

    public String getTileID() {
        return tileID;
    }

    private void setTileID(String id) {
        tileID = id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean o) {
        occupied = o;
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