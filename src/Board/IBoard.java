package Board;

import java.util.NoSuchElementException;

import Ship.AbstractShip;

public interface IBoard { 

    /**
     * Get the size of the grids contained in the Board
     * @return the size of the grids contained in the Board
     */
    int getSize();

    /**
    * Put the given ship at the given position
    * @param ship The ship to place on the board
    * @param x
    * @param y
    */
    void putShip(AbstractShip ship, int x, int y);

    /**
     * Get if a ship is placed at the given position
     * @param x
     * @param y
     * @return true if a ship is located at the given position
     */
    boolean hasShip(int x, int y);

    /**
     * Set the state of the hit at a given position
     * @param hit true if the hit must be set to successful
     * @param x
     * @param y
     */
    void setHit(boolean hit, int x, int y);

    /**
     * Get the state of a hit at the given position
     * @param x
     * @param y
     * @return true if the hit is successful
     */
    //boolean getHit(int x, int y);
    // BattleShipsAI.java nous avons la methode "private boolean isUndiscovered(int x, int y)"
    // que verifie si getHit == null. C'est possible si Boolean(true, false, null)
    Boolean getHit(int x, int y);
    
    public enum Hit {
        MISS(-1, "manqué"),
        STIKE(-2, "touché"),
        DESTROYER(2, "Frégate"),
        SUBMARINE(3, "Sous-marin"),
        BATTLESHIP(4, "Croiseur"),
        CARRIER(5, "Porte-avion");

        /* ***
         * Attributs
         */
        private int value;
        private String label;

        /* ***
         * Constructeur
         */
        Hit(int value, String label) {
            this.value = value;
            this.label = label;
        }

        /* ***
         * Méthodes
         */
        public static Hit fromInt(int value) {
            for (Hit hit : Hit.values()) {
                if (hit.value == value) {
                    return hit;
                }
            }
            throw new NoSuchElementException("no enum for value " + value);
        }

        public String toString() {
            return this.label;
        }
    };
    
    Hit sendHit(int x, int y);
}


