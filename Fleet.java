import java.util.ArrayList;

/**
 * Created by Jordan on 6/12/2016.
 */
public class Fleet {

    ArrayList fleet = new ArrayList<Ship>();

    final int FLEET_SIZE = 5;


    public Fleet(){

        fleet.add(new Ship(5));
        fleet.add(new Ship(4));
        fleet.add(new Ship(3));
        fleet.add(new Ship(3));
        fleet.add(new Ship(2));

    }

    public Ship getShip(int index){
        return ((Ship)fleet.get(index));
    }

    public int getFleetSize(){
        return FLEET_SIZE;
    }
}
