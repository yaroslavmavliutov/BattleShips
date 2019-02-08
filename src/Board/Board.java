package Board;

import Ship.*;;

public class Board implements IBoard{

	private String NameBoard = new String();
	private int size_grille;
	private char[][] Narives2D;
	private boolean[][] Frappes2D;
	// Nous avons créé un tableau de char à partir de string
	private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	// constructor #1
	Board(String name, int size) {
		this.NameBoard = name;
		this.size_grille = size;
		this.Narives2D = new char[size][size];
		this.Frappes2D = new boolean[size][size];
	}
	

	// constructor #2
	Board(String name) {
		this.NameBoard = name;
		this.size_grille = 10;
		this.Narives2D = new char[10][10];
		this.Frappes2D = new boolean[10][10];
	}

	public void print() {
		System.out.print(" Narives:\t ");
		System.out.print(" Frappes:\n\t ");
		for (int k = 0; k < this.size_grille; k++) {
			System.out.print(this.alphabet[k] + " ");
		}
		System.out.print("\t\t");
		for (int k = 0; k < this.size_grille; k++) {
			System.out.print(this.alphabet[k] + " ");
		}
		
		System.out.print("\n");
		for (int i = 0; i < this.size_grille; i++) {
			System.out.print(i + 1 + "\t");
			for (int j = 0; j < this.size_grille; j++) {
				if (this.Narives2D[i][j] != 0)
					System.out.print(" #");
				else
					System.out.print(" .");
			}
			System.out.print("\t");
			System.out.print(i + 1 + "\t");
			for (int j = 0; j < this.size_grille; j++) {
				if (this.Frappes2D[i][j] == true)
					System.out.print(" x");
				else
					System.out.print(" .");
			}
			System.out.print("\n");
		}

		
		/*System.out.print("\n");
		for (int i = 0; i < this.size_grille; i++) {
			System.out.print(i + 1 + "\t");
			for (int j = 0; j < this.size_grille; j++) {
				if (this.Frappes2D[i][j] == true)
					System.out.print(" x");
				else
					System.out.print(" .");
			}
			System.out.print("\n");
		}*/

	}


	@Override
	public int getSize() {
		return this.size_grille;
	}

	@Override
	public void putShip(AbstractShip ship, int x, int y) {
		// switch pour déterminer l'orientation
		switch (ship.OrientationNavire) {
		case North:
			// "for" pour dessiner une partie du navire
			for (int i = 0; i < ship.SizeNavire; i++) {
				// hasShip = true si cette coordonnée est libre
				if (hasShip(x, y-i) == true) this.Narives2D[y-1-i][x-1] = 1;
				else {
					/* Nous pouvons recevoir hasShip = false, si:
					 *  1) cette coordonnée est occupée
					 *  2) Exeption "ArrayIndexOutOfBoundsException"
					 *  Donc, le prochain boucle "for" réécrire les coordonnées précédentes de ce navire
					 *  
					 *  Exmp. Destroyer: les coordonnees (x, y) et (x, y+1)
					 *  Si (x, y) est bonne, donc Narives2D[y][x] = 1
					 *  Mais (x, y+1) est pas bonne, donc nous avons besoin de 
					 *  annuler (x, y) comme Narives2D[y][x] = 0
					 */
					for (int k = i-1; k >=0; k--) {
						this.Narives2D[y-1-k][x-1] = 0; 
					}
					break;
				}
			}
			break;
		case South:
			// la même principe comme pour "case North"
			for (int i = 0; i < ship.SizeNavire; i++) {
				if (hasShip(x, y+i) == true) this.Narives2D[y-1+i][x-1] = 1;
				else {
					for (int k = i-1; k >=0; k--) {
						this.Narives2D[y+k-1][x-1] = 0;
					}
					break;
				}
			}
			break;
		case East:
			// la même principe comme pour "case North"
			for (int i = 0; i < ship.SizeNavire; i++) {
				if (hasShip(x+i, y) == true) this.Narives2D[y-1][x-1+i] = 1;
				else {
					for (int k = i-1; k >=0; k--) {
						this.Narives2D[y-1][x+k-1] = 0;
					}
					break;
				}
			}
			break;
		case West:
			// la même principe comme pour "case North"
			for (int i = 0; i < ship.SizeNavire; i++) {
				if (hasShip(x-i, y) == true) this.Narives2D[y-1][x-1-i] = 1;
				else {
					for (int k = i-1; k >=0; k--) {
						this.Narives2D[y-1][x-k-1] = 0;
					}
					break;
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean hasShip(int x, int y) {
		try {
			// Narives2D[y][x] = 1 si cette coordonnée est déjà occupée
			if (this.Narives2D[y-1][x-1] == 1) {
				System.out.println("Invalid position (" + x + " : " + y + ") is busy coordinate ");
				return false;
			}
			else return true;
		} catch(ArrayIndexOutOfBoundsException e) {
			// x ou y > size_grille
			System.out.println("The index you have entered is invalid");
	        return false;
	    }
	}

	@Override
	public void setHit(boolean hit, int x, int y) {
		try {
			if (hit == true) this.Frappes2D[y-1][x-1] = true;
		} catch(ArrayIndexOutOfBoundsException e) {
			// x ou y > size_grille
	        System.out.println("The index you have entered is invalid");
	    }
	}

	@Override
	public boolean getHit(int x, int y) {
		try {
			// si Narives2D[y][x] = 1 donc il y a une partie de navale. Du coup, bon hit
			if (this.Narives2D[y-1][x-1] == 1) return true;
			else return false;
		} catch(ArrayIndexOutOfBoundsException e) {
			// x ou y > size_grille
	        System.out.println("The index you have entered is invalid");
	        return false;
	    }
		
	}

}
