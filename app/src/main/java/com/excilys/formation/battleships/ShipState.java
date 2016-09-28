package com.excilys.formation.battleships;

import com.excilys.formation.battleships.ship.AbstractShip;

import java.io.Serializable;

public class ShipState implements Serializable {

    private boolean struck;
    private AbstractShip ship;

    public ShipState(AbstractShip ship) {
        this.ship = ship;
        this.struck = false;
    }

    public void addStrike() {
        if (struck) {
            throw new IllegalStateException("already struck");
        }
        this.struck = true;
        this.ship.addStrike();
    }

    public boolean isSunk() {
        return ship.isSunk();
    }

    public boolean isStruck() {
        return struck;
    }

    public String print() {
        ColorUtil.Color color = ColorUtil.Color.RESET;
        if (ship.isSunk()) {
            color = ColorUtil.Color.RED;
        } else if (struck) {
            color = ColorUtil.Color.PURPLE;
        }
        return ColorUtil.colorize(ship.getLabel(), color);
    }

    public AbstractShip getShip() {
        return ship;
    }
}
