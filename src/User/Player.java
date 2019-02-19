package User;

import java.io.Serializable;

import java.util.List;

import Board.*;
import Board.IBoard.Hit;
import Ship.*;
import Ship.AbstractShip.Orientation;
import User.InputHelper.CoordInput;

public class Player {
    /* **
     * Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;

    /* **
     * Constructeur
     */
    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
    }

    /* **
     * Méthodes
     */
    
    // Il a crée d'une nouvelle classe pour transférer HIT et ces coordinatees dans le "public Hit_coord sendHit()"
    public static class Hit_coord {
        public Hit hit;
        public int x;
        public int y;
    }

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0;

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getName(), s.getLength());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            // La comparaison "string" to "enum"
            // C'est notre function qui definie dans le class AbsrtactShip
            s.OrientationNavire = Orientation.valueOfString(res.orientation);
            //try..catch, parce que putShip a "throw new IllegalArgumentException()"
        	//donc, ici est possible de erreur
            try {
            	// on veut mettre le navire dans une coordonnée
            	this.board.putShip(s, res.x, res.y);
            	++i;
	            done = i == 5;
	            board.print();
            }catch(IllegalArgumentException e) {
                System.err.println("You can't place the ship here");
            }
           
        } while (!done);
    }

    public Hit_coord sendHit() {
        boolean done = false;
        Hit hit = null;
        
        //Créé un objet contenant Hit et coordinatee
        Hit_coord res = new Hit_coord();
        do {
            System.out.println("où frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            hitInput.x +=1;
            hitInput.y +=1;
            //try..catch, parce que sendHit et setHit ont "throw new IllegalArgumentException()"
        	//donc, ici est possible de erreur
            try {
            	hit = this.opponentBoard.sendHit(hitInput.x, hitInput.y);
            	// hit = null si il y a déjà tire
            	if (hit != null) {
            		this.board.setHit(hit != Hit.MISS, hitInput.x, hitInput.y);
            		res.hit = hit;
            		res.x = hitInput.x;
            		res.y = hitInput.y;
            		done = true;
                } else System.err.println("You can't hit here\n ["+this.board.getalphabet()[hitInput.x - 1]+":"+hitInput.y+"].\n");
            }catch(IllegalArgumentException e) {
                System.err.println("Invalid coordinate. Impossible hit\n");
            }   
        } while (!done);
              

        return res;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
