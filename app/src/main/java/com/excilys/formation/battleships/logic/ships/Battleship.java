package com.excilys.formation.battleships.logic.ships;

public class Battleship extends AbstractShip {
    private static String NAME = "BattleShip";
    private static int LENGTH = 4;

    public Battleship() {
        super(NAME, LENGTH);
    }

    public Battleship(Orientation orientation) {
        super(orientation, NAME, LENGTH);
    }
}
