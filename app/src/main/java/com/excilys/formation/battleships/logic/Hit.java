package com.excilys.formation.battleships.logic;


import java.util.NoSuchElementException;

public enum Hit {
    MISS(-1, "miss"),
    STIKE(-2, "hit"),
    DESTROYER(2, "DESTROYER"),
    SUBMARINE(3, "SUBMARINE"),
    BATTLESHIP(4, "BATTLESHIP"),
    CARRIER(5, "CARRIER");
    private int value;

    private String label;

    Hit(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Hit fromInt(int value) {
        for (Hit hit : Hit.values()) {
            if (hit.value == value) {
                return hit;
            }
        }
        throw new NoSuchElementException("no enum for value " + value);
    }

    public static boolean isShip(Hit hit) {
        return hit.value > 0;

    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return this.label;
    }
}
