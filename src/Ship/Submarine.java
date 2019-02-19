package Ship;

public class Submarine extends AbstractShip{
	
	public Submarine(Orientation orient) {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("Submarine", "S", 3, orient);
	}
	
	public Submarine() {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("Submarine", "S", 3, Orientation.East);
	}

}
