package Ship;

public class Battleship extends AbstractShip{
	
	public Battleship(Orientation orient) {
		super("BattleShip", "B", 4, orient);
	}
	
	public Battleship() {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("BattleShip", "B", 4, Orientation.East);
	}

}
