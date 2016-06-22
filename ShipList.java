import java.util.ArrayList;

public class ShipList extends ArrayList<Ship> {

	private static final long serialVersionUID = 1L;

	public boolean shipAlreadyExists(Ship newShip) {
		for (Ship ship : this) {
			if (newShip.isXOriented()) {
				for (int i = newShip.getXPosition(); i < newShip.getXPosition() + newShip.getLength(); i++) {
					if (ship.isCordinate(i, newShip.getYPosition())) {
						return true;
					}
				}
			} else {
				for (int i = newShip.getYPosition(); i < newShip.getYPosition() + newShip.getLength(); i++) {
					if (ship.isCordinate(newShip.getXPosition(), i)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public Ship shipAtCordinates(int x, int y) {
		for (Ship ship : this) {
			if (ship.isCordinate(x, y))
				return ship;
		}

		return null;
	}

	public boolean isSunk() {
		for (Ship ship : this) {
			if (!ship.isSunk()) {
				return false;
			}
		}

		return true;
	}
}
