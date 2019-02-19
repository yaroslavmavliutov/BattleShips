package Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Board.IBoard.Hit;
import Ship.*;
import User.*;
import User.Player.Hit_coord;
import Ship.AbstractShip.Orientation;

public class TestGame {
	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);
		String name = "Player";
		String nameboard = "BOARD";
		
        System.out.print("Input your nickname: ");
        try {
        	name = in.nextLine();
        }catch (InputMismatchException e) {
        	// faire rien
        	assert true;
        }
        
        System.out.print("Input name your board: ");
        try {
        	nameboard = in.nextLine();
        }catch (InputMismatchException e) {
        	assert true;
        }
        
        Board myboard = new Board(nameboard);
		Board opponentboard = new Board(nameboard);
        myboard.setNamePlayer(name);
		myboard.print();
		
		// pour chaque joueur le lest de navires est individuel
		List<AbstractShip> ships1 = (List<AbstractShip>) Arrays.asList(new Destroyer(), new Submarine(), 
				new Submarine(), new Battleship(), new Carrier());
		List<AbstractShip> ships2 = (List<AbstractShip>) Arrays.asList(new Destroyer(), new Submarine(), 
				new Submarine(), new Battleship(), new Carrier());
		
		// computer
		AIPlayer ai = new AIPlayer(opponentboard, myboard, ships1);
		
		//moi
		Player player = new Player(myboard, opponentboard, ships2);
		
		System.out.println("\nYou must put ships (format: A1 e)\n ");
		// computer met les navires (random)
		ai.putShips();
		
		player.putShips();
		System.out.println("\nLET'S GO! (format A1)\n ");
		// nombre de navires détruits pour joueur 1
		int k = 0;
		// nombre de navires détruits pour joueur 2
		int s = 0;
		do {
			// ce objet qui a result de HIT et coordinatees
			Hit_coord sndhit1 = ai.sendHit();
			Hit_coord sndhit2 = player.sendHit();
			// donc, Il a coulé une navire
			if(sndhit1.hit != Hit.MISS && sndhit1.hit != Hit.STIKE) k++;
			if(sndhit2.hit != Hit.MISS && sndhit2.hit != Hit.STIKE) s++;
			System.out.println("------------------------\n");
			System.out.println("Your last hit: ["+myboard.getalphabet()[sndhit2.x - 1]+":"+sndhit2.y+"]\n");
			System.out.println("Resultat: "+sndhit2.hit);
			myboard.print();
		}while(k!=ships1.size() && s!=ships2.size());
		System.out.println("\n Good play");
	}
}
