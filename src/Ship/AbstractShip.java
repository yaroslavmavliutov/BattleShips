package Ship;

public class AbstractShip {

	public String TypeNavire = new String();
	public String NameTypeNavire = new String();
	public int SizeNavire;
	public Orientation OrientationNavire;
	
	public int strikeCount;
	
	// enum est plus meilleur pour représenter cette information
	public enum Orientation{
		  
	    North,
	    South,
	    East,
	    West;
	    
		// Pour comparer le string entrée (A0 n) avec les enum
	    public static Orientation valueOfString(String value){
	        if(value.equals("n"))
	            return Orientation.North;
	        else if(value.equals("s"))
	            return Orientation.South;
	        else if(value.equals("e"))
	            return Orientation.East;
	        else if(value.equals("w"))
	            return Orientation.West;
	        else
	            return null;
	    }
	}
	
	AbstractShip(String name, String label, int size, Orientation orient) {
		this.NameTypeNavire = name;
		this.TypeNavire = label;
		this.SizeNavire = size;
		this.OrientationNavire = orient;
		
		this.strikeCount = 0;
	}

	public String getName() {
		return this.NameTypeNavire;
	}

	public int getLength() {
		return this.SizeNavire;
	}
	
	// une coordonnée est blessée
	public void addStrike() {
		this.strikeCount+=1;
	}
	
	//Si sa longueur est de 5 et qu'il a été blessé 5 fois, il sera coulé
	public boolean isSunk() {
		if (this.strikeCount == this.SizeNavire) return true;
		else return false;
	}

	public Orientation getOrientation() {
		return this.OrientationNavire;
	}
	
}
