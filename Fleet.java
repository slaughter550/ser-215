import java.util.ArrayList;

/**
 * Created by Jordan on 6/12/2016.
 * 
 * Move to Ship or Gameboard Class?
 */

public class Fleet {
    
    final int FLEET_SIZE = 5;
    ArrayList fleet = new ArrayList<Ship>();
    
//-----------------------------------------------------------Create Standard Fleet    
    public Fleet(){

        fleet.add(new Ship(5));
        fleet.add(new Ship(4));
        fleet.add(new Ship(3));
        fleet.add(new Ship(3));
        fleet.add(new Ship(2));
    }
    
//---------------------------------------------------------------Get Ship in Fleet 
    public Ship getShip(int index){
    return ((Ship)fleet.get(index));
}
    
//--------------------------------------------------------------------------------
    public int getFleetSize(){
        return FLEET_SIZE;
    }
}
