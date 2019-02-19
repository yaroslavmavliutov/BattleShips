package User;

import java.io.Serializable;
import java.util.List;

import Board.Board;
import Board.IBoard.Hit;
import Ship.AbstractShip;
import Ship.AbstractShip.Orientation;
import User.Player.Hit_coord;

public class AIPlayer extends Player {
    /* **
     * Attribut
     */
    private BattleShipsAI ai;

    /* **
     * Constructeur
     */
    public AIPlayer(Board ownBoard, Board opponentBoard, List<AbstractShip> ships) {
        super(ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
    }
    
    public void putShips() {
    	this.ai.putShips(ships);
    }
    
    public Hit_coord sendHit() {
    	Hit_coord res = new Hit_coord();
    	
    	Hit_coord hit_ai = this.ai.sendHit();   
        return hit_ai;
    }
}
