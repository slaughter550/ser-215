import java.util.ArrayList;

public class ShipList extends ArrayList<Ship> {

	private static final long serialVersionUID = 1L;

	public boolean shipAlreadyExists(Ship newShip) {
		for (Ship ship : this) {
			if (newShip.isXOriented()) {
				for (int i = newShip.getX(); i < newShip.getX() + newShip.getSize(); i++) {
					if (ship.isCordinate(i, newShip.getY())) {
						return true;
					}
				}
			} else {
				for (int i = newShip.getY(); i < newShip.getY() + newShip.getSize(); i++) {
					if (ship.isCordinate(newShip.getX(), i)) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
