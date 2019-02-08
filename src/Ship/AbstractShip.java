package Ship;

public class AbstractShip {

	public String TypeNavire = new String();
	public String NameTypeNavire = new String();
	public int SizeNavire;
	public Orientation OrientationNavire;
	
	// enum est plus meilleur pour représenter cette information
	public enum Orientation{
		  
	    North,
	    South,
	    East,
	    West
	}
	
	AbstractShip(String name, String label, int size, Orientation orient) {
		this.NameTypeNavire = name;
		this.TypeNavire = label;
		this.SizeNavire = size;
		this.OrientationNavire = orient;
	}
	
}
