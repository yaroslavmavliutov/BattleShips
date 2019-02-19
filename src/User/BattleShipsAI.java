package User;

import java.io.Serializable;
import java.util.*;
import Board.*;
import Board.IBoard.Hit;
import Ship.*;
import Ship.AbstractShip.Orientation;
import User.Player.Hit_coord;

public class BattleShipsAI implements Serializable {

    /* **
     * Attributs
     */

    /**
     * grid size.
     */
    private final int size;

    /**
     * My board. My ships have to be put on this one.
     */
    private final IBoard board;

    /**
     * My opponent's board. My hits go on this one to strike his ships.
     */
    private final IBoard opponent;

    /**
     * Coords of last known strike. Would be a good idea to target next hits around this point.
     */
    private int lastStrike[];

    /**
     * If last known strike lead me to think the underlying ship has vertical placement.
     */
    private Boolean lastVertical;

    /* **
     * Constructeur
     */

    /**
     *
     * @param myBoard board where ships will be put.
     * @param opponentBoard Opponent's board, where hits will be sent.
     */
    public BattleShipsAI(IBoard myBoard, IBoard opponentBoard) {
        this.board = myBoard;
        this.opponent = opponentBoard;
        size = board.getSize();
    }

    /* **
     * Méthodes publiques
     */

    /**
     * Put the ships on owned board.
     * @param ships the ships to put
     */
    public void putShips(AbstractShip ships[]) {
        // int x, y
    	int x = 0, y = 0;
    	boolean done = false;
        AbstractShip.Orientation o;
        Random rnd = new Random();
        AbstractShip.Orientation[] orientations = AbstractShip.Orientation.values();
        
        for (AbstractShip s : ships) {
            do {
            	//done = true;
            	x = rnd.nextInt(this.size);
            	y = rnd.nextInt(this.size);
            	//random l'orientation
            	s.OrientationNavire = orientations[rnd.nextInt(orientations.length)];
            	//try..catch, parce que putship a "throw new IllegalArgumentException()"
            	//donc, ici est possible de erreur
            	try {
                	if (canPutShip(s, x, y)) {
                		board.putShip(s, x, y);
                		done = true;
                	} else done = false;
                }catch(IllegalArgumentException e) {
                	done = false;
                }
            } while(!done);   
        }
    }

    /**
     *
     * @param coords array must be of size 2. Will hold the coord of the send hit.
     * @return the status of the hit.
     */
    public Hit_coord sendHit() {
        int res[] = null;
        boolean done = false;
        Hit_coord hit_coord = new Hit_coord();

        if (lastVertical != null) {
            if (lastVertical) {
                res = pickVCoord();
            } else {
                res = pickHCoord();
            }

            if (res == null) {
                lastStrike = null;
                lastVertical = null;
            }
        } else if (lastStrike != null) {
            res = pickVCoord();
            if (res == null) {
                res = pickHCoord();
            }
            if (res == null) {
                lastStrike = null;
            }
        }

        if (lastStrike == null) {
            res = pickRandomCoord();
        }
        
        Hit hit = null;
        do {
        	done = true;
        	//try..catch, parce que sendHit et setHit ont "throw new IllegalArgumentException()"
        	//donc, ici est possible de erreur
        	try {
        		hit = opponent.sendHit(res[0], res[1]);
        		// // hit = null si il y a déjà tire
	        	if (hit != null) {
	        		board.setHit(hit != Hit.MISS, res[0], res[1]);
	            } else done = false;
	        }catch(IllegalArgumentException e) {
	        	done = false;
	        }   
        }while(!done);

        if (hit != Hit.MISS) {
            if (lastStrike != null) {
                lastVertical = guessOrientation(lastStrike, res);
            }
            lastStrike = res;
        }
        
        //notre objet contenant hit et coordinatee
        hit_coord.hit = hit;
        hit_coord.x = res[0];
        hit_coord.y = res[1];
        

        return hit_coord;
    }

    /* ***
     * Méthodes privées
     */

    private boolean canPutShip(AbstractShip ship, int x, int y) {
        AbstractShip.Orientation o = ship.getOrientation();
        int dx = 0, dy = 0;
        if (o == AbstractShip.Orientation.East) {
            if (x + ship.getLength() >= this.size) {
                return false;
            }
            dx = 1;
        } else if (o == AbstractShip.Orientation.South) {
            if (y + ship.getLength() >= this.size) {
                return false;
            }
            dy = 1;
        } else if (o == AbstractShip.Orientation.North) {
            if (y + 1 - ship.getLength() < 0) {
                return false;
            }
            dy = -1;
        } else if (o == AbstractShip.Orientation.West) {
            if (x + 1 - ship.getLength() < 0) {
                return false;
            }
            dx = -1;
        }

        int ix = x;
        int iy = y;

        for (int i = 0; i < ship.getLength(); ++i) {
        	try {
        		if (board.hasShip(ix, iy)) {
                    return false;
                }
            }catch(IllegalArgumentException e) {
            	return false;
            }
            
            ix += dx;
            iy += dy;
        }

        return true;
    }

    private boolean guessOrientation(int[] c1, int[] c2) {
        return c1[0] == c2[0];
    }

    private boolean isUndiscovered(int x, int y) {
        //on vérifie si la coordonnée est valide
    	try {
    		return x >= 0 && x <= size && y >= 0 && y <= size && board.getHit(x, y) == null;
        }catch(IllegalArgumentException e) {
        	return false;
        }
    }

    private int[] pickRandomCoord() {
        Random rnd = new Random();
        int x;
        int y;

        do {
            x = rnd.nextInt(size) + 1;
            y = rnd.nextInt(size) + 1;
        } while (!isUndiscovered(x, y));
        return new int[] {x, y};
    }

    /**
     * pick a coord verically around last known strike
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickVCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int iy : new int[]{y - 1, y + 1}) {
            if (isUndiscovered(x, iy)) {
                return new int[]{x, iy};
            }
        }
        return null;
    }

    /**
     * pick a coord horizontally around last known strike
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickHCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int ix : new int[]{x - 1, x + 1}) {
            if (isUndiscovered(ix, y)) {
                return new int[]{ix, y};
            }
        }
        return null;
    }
}
