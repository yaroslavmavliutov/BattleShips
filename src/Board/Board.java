package Board;

import Ship.*;

public class Board implements IBoard{

	private String NameBoard = new String();
	private String NamePlayer = new String();
	private int size_grille;
	
	// null, true ou false
	private Boolean[][] Frappes2D;
	
	private ShipState[][] Narives2D;
	
	// Nous avons créé un tableau de char à partir de string
	// Pour afficher " A B C D E...."
	private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	// constructor #1
	Board(String name, int size) {
		this.NameBoard = name;
		this.size_grille = size;
		this.Narives2D = new ShipState[size][size];
		// Nous devons initialiser chaque coordonnée
		for (int i = 0; i < size ; i++) {
			for (int j = 0; j < size; j ++)
				this.Narives2D[i][j] = new ShipState();
		}
		this.Frappes2D = new Boolean[size][size];
	}
	

	// constructor #2
	Board(String name) {
		this.NameBoard = name;
		this.size_grille = 10;
		this.Narives2D = new ShipState[10][10];
		for (int i = 0; i < 10 ; i++) {
			for (int j = 0; j < 10; j ++)
				this.Narives2D[i][j] = new ShipState();
		}
		this.Frappes2D = new Boolean[10][10];
	}

	public void print() {
		System.out.print(" Your name " + this.NamePlayer + "\n");
		System.out.print(" Your table: " + this.NameBoard + "\n");
		for (int i = -1; i < this.size_grille; i++) {
			
			if (i == -1) {
				//Pour afficher " A B C D E...."
				System.out.print("\t ");
				for (int k = 0; k < this.size_grille; k++) {
					System.out.print(this.alphabet[k] + " ");
				}
			} else {
				System.out.print(i + 1 + "\t");			 
				for (int j = 0; j < this.size_grille; j++) {
					if (this.Narives2D[i][j].getShip() != null)
						System.out.print(" "+this.Narives2D[i][j].toString());
					else {
						// isStrike() == null si pas tiré ici
						// isStrike() == false si tiré, mais pas de ship
						// isStrike() == true si tiré, et il y a un ship
						if (this.Narives2D[i][j].isStrike() == null)
								System.out.print(" .");
							else
								System.out.print(" x");
					}
						
				}
			}
			if (i == -1) {
				System.out.print("\t\t ");
				for (int k = 0; k < this.size_grille; k++) {
					System.out.print(this.alphabet[k] + " ");
				}
			} else {
				System.out.print("\t"+(i + 1) + "\t");
				for (int j = 0; j < this.size_grille; j++) {
					// Frappes2D[i][j] == null, si pas tiré ici
					// Frappes2D[i][j] == false, si tiré, mais pas success
					// Frappes2D[i][j] == true, si tiré, et il y a success..x rouge
					if (this.Frappes2D[i][j] == null)
						System.out.print(" .");
					else if (this.Frappes2D[i][j] == false)
						System.out.print(" x");
					else
						System.out.print(" "+ColorUtil.colorize("x", ColorUtil.Color.RED));
				}
			}
			System.out.print("\n");
		}

	}
	
	public void setNamePlayer(String name) {
		this.NamePlayer = name;
	}
	
	public char[] getalphabet() {
		return this.alphabet;
	}
	
	@Override
	public int getSize() {
		return this.size_grille;
	}
	// MIN de x et y sont 1
	// MAX est 10
	
	//mais le tableau commence par l'index 0
	
	@Override
	public void putShip(AbstractShip ship, int x, int y) {
		// Vérification dimension
		if (x < 1 || y < 1 || x > this.size_grille || y > this.size_grille) {
			throw new IllegalArgumentException("Out of grid");
		}
		// switch pour déterminer l'orientation
		switch (ship.OrientationNavire) {
		case North:
			if (y - ship.getLength() < 1) {
				throw new IllegalArgumentException("Ship is out of the grid");	
			}
			for (int i = 0; i < ship.getLength(); i++) {
				if (hasShip(x, y-i) == true) {
					throw new IllegalArgumentException("Ships were covered");
				}
			}
			for (int i = 0; i < ship.SizeNavire; i++) {
				// Dans cette coordonnée, nous définissons le navire donné
				this.Narives2D[y-1-i][x-1].ship = ship;
					
			}
			break;
		case South:
			// la même principe comme pour "case North"
			if (y + ship.getLength() > this.size_grille) {
				throw new IllegalArgumentException("Ship is out of the grid");	
			}
			for (int i = 0; i < ship.getLength(); i++) {
				if (hasShip(x, y+i) == true)
					throw new IllegalArgumentException("Ships were covered");
			}
			for (int i = 0; i < ship.SizeNavire; i++)
				this.Narives2D[y-1+i][x-1].ship = ship;	
			break;
		case East:
			// la même principe comme pour "case North"
			if (x + ship.getLength() > this.size_grille) {
				throw new IllegalArgumentException("Ship is out of the grid");	
			}
			for (int i = 0; i < ship.getLength(); i++) {
				if (hasShip(x+i, y) == true)
					throw new IllegalArgumentException("Ships were covered");
			}
			for (int i = 0; i < ship.SizeNavire; i++)
				this.Narives2D[y-1][x-1+i].ship = ship;	
			break;
		case West:
			// la même principe comme pour "case North"
			if (x - ship.getLength() < 1) {
				throw new IllegalArgumentException("Ship is out of the grid");	
			}
			for (int i = 0; i < ship.getLength(); i++) {
				if (hasShip(x-i, y) == true)
					throw new IllegalArgumentException("Ships were covered");
			}
			for (int i = 0; i < ship.SizeNavire; i++)
				this.Narives2D[y-1][x-1-i].ship = ship;	
			break;
		default:
			break;
		}
	}

	@Override
	public boolean hasShip(int x, int y) {
		if (x < 1 || y < 1 || x > this.size_grille || y > this.size_grille) {
			throw new IllegalArgumentException("Out of glid");
		}
		if (this.Narives2D[y-1][x-1].getShip() != null) return true;
		else return false;
	}

	@Override
	public void setHit(boolean hit, int x, int y) {
		if (x < 1 || y < 1 || x > this.size_grille || y > this.size_grille)
			throw new IllegalArgumentException("Out of grid");
		if (hit == true){
			this.Frappes2D[y-1][x-1] = true;
		}
		else this.Frappes2D[y-1][x-1] = false;
	}

	@Override
	public Boolean getHit(int x, int y) {
		if (x < 1 || y < 1 || x > this.size_grille || y > this.size_grille)
			throw new IllegalArgumentException("Out of grid");
		return this.Frappes2D[y-1][x-1];	
	}
	
	@Override
	public Hit sendHit(int x, int y) {
		if (x < 1 || y < 1 || x > this.size_grille || y > this.size_grille)
			throw new IllegalArgumentException("Out of grid");
		// Donc, ici il a déjà tire
		if (this.Narives2D[y-1][x-1].isStrike() != null) {
			return null;
		}
		if (this.Narives2D[y-1][x-1].getShip() == null) {
			// Il a tiré, mais il n'y a pas de navire
			this.Narives2D[y-1][x-1].noStrike();
			return Hit.MISS;
		} else {
			this.Narives2D[y-1][x-1].addStrike();
			if (this.Narives2D[y-1][x-1].isSunk() != true) {
				return Hit.STIKE;
			}
			else {
					//System.out.println(Hit.fromInt(this.Narives2D[y-1][x-1].ship.SizeNavire).toString()+" coulé");
				return Hit.fromInt(this.Narives2D[y-1][x-1].ship.SizeNavire);
			}
		}
	}

}











