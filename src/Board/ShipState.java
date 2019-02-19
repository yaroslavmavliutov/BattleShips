package Board;

import Ship.AbstractShip;

public class ShipState {
	
	
	public AbstractShip ship;
	private Boolean struck;
	
	
	//constuctor
	public ShipState() {
		this.ship = null;
		this.struck = null;
	}
	
	// si il a une hit success, donc Il a blessé cette navire
	// et cette coordonnée est success (struck = true)
	public void addStrike() {
		this.ship.addStrike();
		this.struck = true;
	}
	
	// si il tire, mais cette coordonnée n'a pas de navire
	public void noStrike() {
		this.struck = false;
	}
	
	public Boolean isStrike() {
		return this.struck;
	}
	
	public String toString() {
		if (this.struck == null || this.struck == false) return this.ship.TypeNavire;
		else return ColorUtil.colorize(this.ship.TypeNavire, ColorUtil.Color.RED);
	}
	
	// Si le navire est coulé
	public boolean isSunk() {
		return this.ship.isSunk();
	}
	
	public AbstractShip getShip() {
		return this.ship;
	}
}
