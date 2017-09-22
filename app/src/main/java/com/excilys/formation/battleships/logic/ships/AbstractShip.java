package com.excilys.formation.battleships.logic.ships;

import com.excilys.formation.battleships.android.ui.ships.DrawableBattleship;
import com.excilys.formation.battleships.android.ui.ships.DrawableCarrier;
import com.excilys.formation.battleships.android.ui.ships.DrawableDestroyer;
import com.excilys.formation.battleships.android.ui.ships.DrawableSubmarine;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class AbstractShip implements Serializable {

    private String name;
    private int length;
    private Orientation orientation;

    private int strikeCount;

    public enum Orientation implements Serializable {
        NORTH, SOUTH, EAST, WEST;

        public static Orientation getOrientation(String orientation) {
            switch (orientation) {
                case "n":
                    return NORTH;
                case "w":
                    return WEST;
                case "s":
                    return SOUTH;
                case "e":
                    return EAST;
            }
            throw new NoSuchElementException();
        }

        public static Orientation getOrientation(int orientation) {
            switch (orientation) {
                case 0:
                    return NORTH;
                case 1:
                    return WEST;
                case 2:
                    return SOUTH;
                case 3:
                    return EAST;
            }
            throw new NoSuchElementException();
        }
    }

    AbstractShip(String name, int length) {
        this.name = name;
        this.length = length;
    }

    AbstractShip(Orientation orientation, String name, int length) {
        this(name, length);
        this.orientation = orientation;
    }

    public char getLabel() {
        return name.charAt(0);
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public void incrementStrikeCount() {
        this.strikeCount++;
    }

    public boolean isSunk() {
        return this.strikeCount >= this.length;
    }

    public static AbstractShip getShip(int whichShip) {
        switch (whichShip) {
            case 2:
                return new Destroyer();
            case 3:
                return new Submarine();
            case 4:
                return new Battleship();
            case 5:
                return new Carrier();
        }
        throw new IllegalArgumentException();
    }

    public static AbstractShip getDrawableShip(int whichShip) {
        switch (whichShip) {
            case 2:
                return new DrawableDestroyer();
            case 3:
                return new DrawableSubmarine();
            case 4:
                return new DrawableBattleship();
            case 5:
                return new DrawableCarrier();
        }
        throw new IllegalArgumentException();
    }
}
