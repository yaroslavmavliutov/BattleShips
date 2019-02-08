package Board;
import Ship.*;
import Ship.AbstractShip.Orientation;

public class TestBoard {
	public static void main(String[] args) {
		Board test = new Board("test");
		//test.print();
		Carrier nn = new Carrier(Orientation.North);
		//Carrier nn2 = new Carrier(Orientation.East);
		test.putShip(nn, 10, 10);
		//test.putShip(nn2, 6, 6);
		test.setHit(test.getHit(3, 3), 3, 3);
		test.print();
		
	}
}
