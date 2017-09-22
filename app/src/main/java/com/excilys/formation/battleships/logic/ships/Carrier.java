package com.excilys.formation.battleships.logic.ships;

public class Carrier extends AbstractShip {
    private static char LABEL = 'C';
    private static int LENGTH = 5;

    public Carrier() {
        super(LABEL, LENGTH);
    }

    public Carrier(Orientation orientation) {
        super(orientation, LABEL, LENGTH);
    }
}
