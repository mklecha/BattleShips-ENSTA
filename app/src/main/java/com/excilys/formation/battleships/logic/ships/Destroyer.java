package com.excilys.formation.battleships.logic.ships;

public class Destroyer extends AbstractShip {
    private static String NAME = "Destroyer";
    private static int LENGTH = 2;

    public Destroyer() {
        super(NAME, LENGTH);
    }

    public Destroyer(Orientation orientation) {
        super(orientation, NAME, LENGTH);
    }
}
