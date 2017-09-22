package com.excilys.formation.battleships.logic.ships;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class AbstractShip implements Serializable {

    private char label;
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

    AbstractShip(char label, int length) {
        this.label = label;
        this.length = length;
    }

    AbstractShip(Orientation orientation, char label, int length) {
        this(label, length);
        this.orientation = orientation;
    }


    public char getLabel() {
        return label;
    }

    public int getLength() {
        return length;
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
}
