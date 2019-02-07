
public class Board {
	
	
	private String NameBoard;
	private int size_grille;
	private char[][] Narives2D;
	private boolean[][] Frappes2D;
	private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	// constructor #1
	Board (String name, int size) {
		this.NameBoard = name;
		this.size_grille = size;
		this.Narives2D = new char[size][size];
		this.Frappes2D = new boolean[size][size];
	}
	
	// constructor #2
	Board (String name) {
		this.NameBoard = name;
		this.size_grille = 10;
		this.Narives2D = new char[10][10];
		this.Frappes2D = new boolean[10][10];
	}
	
	public void print() {
		System.out.print(" Narives:\n\t ");
		for(int k = 0; k < this.size_grille; k++) {
			System.out.print(this.alphabet[k] + " ");
		}
		System.out.print("\n");
		for(int i = 0; i < this.size_grille; i++) {
			System.out.print(i+1 + "\t");
			for(int j = 0; j < this.size_grille; j++) {
				if (this.Narives2D[i][j] != 0) System.out.print(" +");
				else System.out.print(" .");
			}
			System.out.print("\n");
		}
		
		System.out.print(" Frappes:\n\t ");
		for(int k = 0; k < this.size_grille; k++) {
			System.out.print(this.alphabet[k] + " ");
		}
		System.out.print("\n");
		for(int i = 0; i < this.size_grille; i++) {
			System.out.print(i+1 + "\t");
			for(int j = 0; j < this.size_grille; j++) {
				if (this.Frappes2D[i][j] == true) System.out.print(" x");
				else System.out.print(" .");
			}
			System.out.print("\n");
		}
			
	}
}
