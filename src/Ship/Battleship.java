package Ship;

public class Battleship extends AbstractShip{
	
	Battleship(Orientation orient) {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("BattleShip", "B", 4, orient);
	}
	
	Battleship() {
		//On appele le constructeur de notre superclasse (AbstractShip) 
		super("BattleShip", "B", 4, Orientation.East);
	}

}
