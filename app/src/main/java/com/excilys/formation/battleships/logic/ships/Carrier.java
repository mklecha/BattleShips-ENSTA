package com.excilys.formation.battleships.logic.ships;

public class Carrier extends AbstractShip {
    private static String NAME = "Carrier";
    private static int LENGTH = 5;

    public Carrier() {
        super(NAME, LENGTH);
    }

    public Carrier(Orientation orientation) {
        super(orientation, NAME, LENGTH);
    }
}
