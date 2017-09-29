package com.excilys.formation.battleships.logic.player;


import com.excilys.formation.battleships.logic.Hit;
import com.excilys.formation.battleships.logic.InputHelper;
import com.excilys.formation.battleships.logic.board.Board;
import com.excilys.formation.battleships.logic.ships.AbstractShip;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
    private Board board;
    private Board opponentBoard;
    List<AbstractShip> ships;

    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships;
        this.opponentBoard = opponentBoard;
    }

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips() {
        for (AbstractShip ship : ships) {
            board.printBoard();
            System.out.println("Place " + ship.getLabel() + "(" + ship.getLength() + ") ship");
            boolean done = false;
            while (!done) {
                InputHelper.ShipInput input = InputHelper.readShipInput();
                try {
                    ship.setOrientation(AbstractShip.Orientation.getOrientation(input.orientation));
                    board.putShip(ship, input.x, input.y);
                    done = true;
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public Hit sendHit(int[] coords) {
        System.out.println("Select coords to shoot");
        boolean done = false;
        Hit hit = null;
        while (!done) {
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();

            try {
                board.checkHit(hitInput.x, hitInput.y);
                hit = this.opponentBoard.sendHit(hitInput.x, hitInput.y);
                board.setHit(hit != Hit.MISS, hitInput.x, hitInput.y);
                done = true;
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
        return hit;
    }

    public void printBoard() {
        board.printBoard();
    }

    public List<AbstractShip> getShips() {
        return ships;
    }

    public void setShips(List<AbstractShip> ships) {
        this.ships = ships;
    }

    public boolean getLose() {
        return !board.hasMoreShips();
    }


    public String getName() {
        return board.getName();
    }
}