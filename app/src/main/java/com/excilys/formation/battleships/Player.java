package com.excilys.formation.battleships;

import com.excilys.formation.battleships.ship.AbstractShip;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
    private final String name;
    protected Board board;
    protected Board opponentBoard;
    public int destroyedCount;
    protected AbstractShip[] ships;
    public boolean lose;

    public Player(String name, Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
        this.name = name;
    }

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0;

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getName(), s.getLength());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            // TODO set ship orientation
            // TODO put ship at given position
            AbstractShip.Orientation orientation = null;
            if (res.orientation.equals("n")) {
                orientation = AbstractShip.Orientation.NORTH;
            } else if (res.orientation.equals("s")) {
                orientation = AbstractShip.Orientation.SOUTH;
            } else if (res.orientation.equals("e")) {
                orientation = AbstractShip.Orientation.EAST;
            } else if (res.orientation.equals("o")) {
                orientation = AbstractShip.Orientation.WEST;
            }



            s.setOrientation(orientation);
            try {
                board.putShip(s, res.x, res.y);
                // TODO when ship placement successful
                ++i;
                done = i == 5;
            } catch (IllegalArgumentException e) {
                System.err.println("Impossible de placer le navire a cette position");
            }
            board.print();

        } while (!done);
    }

    public Hit sendHit(int[] coords) {
        boolean done;
        Hit hit = null;
        do {
            // TODO call InputHelper to get hit coords.
            // TODO call sendHit on this.opponentBoard

            System.out.println("où frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            try {
                hit = opponentBoard.sendHit(hitInput.x, hitInput.y);
                done = true;
            } catch (Exception e) {
                done = false;
                System.err.println("coordonées incorrectes");
            }
            // TODO : Game expects sendHit to return BOTH hit result & hit coords.
            // return hit is obvious. But how to return coords at the same time ?
            coords[0] = hitInput.x;
            coords[1] = hitInput.y;
        } while (!done);
        return hit;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }
}