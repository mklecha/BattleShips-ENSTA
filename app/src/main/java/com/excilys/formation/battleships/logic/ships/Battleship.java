package com.excilys.formation.battleships.logic.ships;

public class Battleship extends AbstractShip {
    private static char LABEL = 'B';
    private static int LENGTH = 4;

    public Battleship() {
        super(LABEL, LENGTH);
    }

    public Battleship(Orientation orientation) {
        super(orientation, LABEL, LENGTH);
    }
}
