package com.excilys.formation.battleships.logic.ships;

public class Destroyer extends AbstractShip {
    private static char LABEL = 'D';
    private static int LENGTH = 2;

    public Destroyer() {
        super(LABEL, LENGTH);
    }

    public Destroyer(Orientation orientation) {
        super(orientation, LABEL, LENGTH);
    }
}
