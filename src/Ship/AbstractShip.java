package Ship;

public class AbstractShip {

	public String TypeNavire = new String();
	public String NameTypeNavire = new String();
	public int SizeNavire;
	public Orientation OrientationNavire;
	
	AbstractShip(String name, String label, int size, Orientation orient) {
		this.NameTypeNavire = name;
		this.TypeNavire = label;
		this.SizeNavire = size;
		this.OrientationNavire = orient;
	}
	
}

enum Orientation{
	  
    North,
    South,
    East,
    West
}
