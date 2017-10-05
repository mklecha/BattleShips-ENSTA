package com.excilys.formation.battleships.logic.board;

import android.graphics.Point;

import com.excilys.formation.battleships.logic.Hit;
import com.excilys.formation.battleships.logic.ships.AbstractShip;
import com.excilys.formation.battleships.logic.ships.ShipState;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements IBoard, Serializable {

    private static final int MAX_SIZE = 15;
    private static final int MIN_SIZE = 1;

    private static final char NO_SHIP = '.';
    private static final char ENEMY_SHIP = 'x';
    private static final char NO_ENEMY_SHIP = '.';
    private static final char NOT_TRIED = ' ';

    private String name;
    private ShipState[][] ships;
    private Boolean[][] hits;

    public Board(String name, int size) throws IllegalArgumentException {
        if (size > MAX_SIZE || size < MIN_SIZE)
            throw new IllegalArgumentException("The size is too big. Max size is " + MAX_SIZE + " and min is " + MIN_SIZE);
        this.name = name;
        this.ships = new ShipState[size][size];
        this.hits = new Boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.ships[i][j] = null;
                this.hits[i][j] = null;
            }
        }
    }

    public Board(String name) {
        this(name, 10);
    }

    public void printBoard() {

        //First line
        System.out.print("   ");
        for (int j = 0; j < ships[0].length; j++) {
            System.out.print((char) ('A' + j) + " ");
        }

        System.out.print("         ");
        for (int j = 0; j < ships[0].length; j++) {
            System.out.print((char) ('A' + j) + " ");
        }
        System.out.println();

        //Grid
        for (int y = 0; y < ships.length; y++) {
            //Numbers at front
            System.out.print(getIntString3Places(1 + y));

            for (int x = 0; x < ships[0].length; x++) {
                System.out.print((ships[x][y] == null ? NO_SHIP : ships[x][y].toString()) + " ");
            }

            System.out.print("      " + getIntString3Places(1 + y));

            for (int x = 0; x < hits[0].length; x++) {
                System.out.print(hits[x][y] == null ? NOT_TRIED : hits[x][y] ? ENEMY_SHIP : NO_ENEMY_SHIP);
                System.out.print(" ");
            }

            System.out.println();
        }
    }

    private String getIntString3Places(int i) {
        if (i < 10) {
            return " " + i + " ";
        }
        if (i < 100) {
            return i + " ";
        }
        return "" + i;
    }

    @Override
    public int getSize() {
        return ships.length;
    }

    @Override
    public void putShip(AbstractShip ship, int x, int y) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < ship.getLength(); i++) {
            switch (ship.getOrientation()) {
                case SOUTH:
                    points.add(new Point(x, y + i));
                    break;
                case NORTH:
                    points.add(new Point(x, y - i));
                    break;
                case EAST:
                    points.add(new Point(x + i, y));
                    break;
                case WEST:
                    points.add(new Point(x - i, y));
                    break;
            }
        }

        for (Point p : points) {
            if ((p.x < 0 && p.x > this.ships.length) || (p.y < 0 || p.y > this.ships.length)) {
                throw new ArrayIndexOutOfBoundsException("Ship beyond the horizon");
            }
            if (ships[p.x][p.y] != null) {
                throw new IllegalArgumentException("There already is a ship");
            }
        }

        for (Point p : points) {
            ships[p.x][p.y] = new ShipState(ship);
        }
    }

    @Override
    public boolean hasShip(int x, int y) {
        return ships[x][y] != null;
    }

    @Override
    public void setHit(boolean hit, int x, int y) {
        hits[x][y] = hit;
    }

    @Override
    public Boolean getHit(int x, int y) {
        return hits[x][y];
    }

    @Override
    public Hit sendHit(int x, int y) {
        if (ships[x][y] == null) {
            return Hit.MISS;
        }

        if (ships[x][y].isStruck() || ships[x][y].isSunk()) {
            throw new IllegalArgumentException("This field was already under fire");
        }

        ships[x][y].addStrike();
        if (ships[x][y].isSunk()) {
            return Hit.fromInt(ships[x][y].getShip().getLength());
        }
        return Hit.STIKE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasMoreShips() {


        // - Demo version -
        for (ShipState[] rows : ships) {
            for (ShipState ship : rows) {
                if (ship != null && ship.isSunk())
                    return false;
            }
        }
        return true;
        /*
        for (ShipState[] rows : ships) {
            for (ShipState ship : rows) {
                if (ship != null && !ship.isSunk())
                    return true;
            }
        }
        return false;*/
    }

    public void checkHit(int x, int y) {
        if (x >= getSize() || y >= getSize())
            throw new ArrayIndexOutOfBoundsException("Shot beyond the horizon");
        if (hits[x][y] != null) {
            throw new IllegalArgumentException("This field was already under fire");
        }
    }
}
