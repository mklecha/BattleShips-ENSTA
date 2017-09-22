package com.excilys.formation.battleships.logic.ships;


import com.excilys.formation.battleships.logic.ColorUtil;

import java.io.Serializable;

public class ShipState implements Serializable {
    private AbstractShip ship;
    private boolean struck = false;

    public ShipState(AbstractShip ship) {
        this.ship = ship;
    }

    public void addStrike() {
        ship.incrementStrikeCount();
        struck = true;
    }

    public boolean isStruck() {
        return struck;
    }

    public String toString() {
        if (isSunk()) {
            return ColorUtil.colorize(ship.getLabel(), ColorUtil.Color.PURPLE);
        }
        if (this.struck) {
            return ColorUtil.colorize(ship.getLabel(), ColorUtil.Color.RED);
        }
        return ship.getLabel() + "";
    }

    public boolean isSunk() {
        return ship.isSunk();
    }

    public AbstractShip getShip() {
        return ship;
    }
}
