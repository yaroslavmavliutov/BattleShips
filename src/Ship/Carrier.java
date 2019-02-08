package Ship;

public class Carrier extends AbstractShip{
	
	public Carrier(Orientation orient) {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("Carrier", "C", 5, orient);
	}
	
	public Carrier() {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("Carrier", "C", 5, Orientation.East);
	}

}
