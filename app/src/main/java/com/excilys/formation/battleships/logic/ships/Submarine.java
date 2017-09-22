package com.excilys.formation.battleships.logic.ships;

public class Submarine extends AbstractShip {
    private static String NAME = "Submarine";
    private static int LENGTH = 3;

    public Submarine() {
        super(NAME, LENGTH);
    }

    public Submarine(Orientation orientation) {
        super(orientation, NAME, LENGTH);
    }
}
