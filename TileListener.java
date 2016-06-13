import java.awt.*;
import java.awt.event.*;

/**
 * Created by Jordan on 6/9/2016.
 */
public class TileListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        
        //test prints
        System.out.println("The " + ((Tile) e.getSource()).getTileID() + " button is clicked");
        System.out.println("Button is occupied: " + ((Tile) e.getSource()).isOccupied());

        Tile tile = ((Tile) e.getSource());
        
        //change tile color: red hit, blue miss
        if (tile.isOccupied()) {
            ((Tile) e.getSource()).setBackground(Color.RED);
        } else {
            ((Tile) e.getSource()).setBackground(Color.BLUE);
        }
    }
}
