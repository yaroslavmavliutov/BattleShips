package Ship;

public class Destroyer extends AbstractShip{
	
	public Destroyer(Orientation orient) {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("Destroyer", "D", 2, orient);
	}
	
	public Destroyer() {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("Destroyer", "D", 2, Orientation.East);
	}

}
