import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Created by Jordan on 6/9/2016.
 */
public class TileListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {

//        ImageIcon red = createImageIcon("resources/oceanpic.jpg");
//        ImageIcon grey = createImageIcon("resources/oceanpic.jpg");

        System.out.println("The " + ((Tile) e.getSource()).getTileID() + " button is clicked");
        System.out.println("Button is occupied: " + ((Tile) e.getSource()).isOccupied());

        Tile tile = ((Tile) e.getSource());

        if (tile.isOccupied()) {
            ((Tile) e.getSource()).setBackground(Color.RED);
        } else {
            ((Tile) e.getSource()).setBackground(Color.BLUE);
        }
    }

}