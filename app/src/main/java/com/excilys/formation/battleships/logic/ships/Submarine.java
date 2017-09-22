package com.excilys.formation.battleships.logic.ships;

public class Submarine extends AbstractShip {
    private static char LABEL = 'S';
    private static int LENGTH = 3;

    public Submarine() {
        super(LABEL, LENGTH);
    }

    public Submarine(Orientation orientation) {
        super(orientation, LABEL, LENGTH);
    }
}
