package com.excilys.formation.battleships.android.ui;

import android.graphics.Point;

import com.excilys.formation.battleships.logic.Hit;
import com.excilys.formation.battleships.logic.board.IBoard;
import com.excilys.formation.battleships.android.ui.ships.DrawableShip;
import com.excilys.formation.battleships.logic.ships.AbstractShip;
import com.excilys.formation.battleships.logic.ships.ShipState;

import java.util.ArrayList;

import battleships.formation.excilys.com.battleships.R;

public class BoardController implements IBoard {

    /* ***
     * Public constants
     */
    public static final int SHIPS_FRAGMENT = 0;
    public static final int HITS_FRAGMENT = 1;

    /* ***
     * Attributes
     */
    private final IBoard mBoard;
    private final BoardGridFragment[] mFragments;
    private final BoardGridFragment mHitsFragment;
    private final BoardGridFragment mShipsFragment;


    public BoardController(IBoard board) {
        mBoard = board;
        mShipsFragment = BoardGridFragment.newInstance(SHIPS_FRAGMENT, mBoard.getSize(), R.drawable.ships_bg, R.string.board_ships_title);
        mHitsFragment = BoardGridFragment.newInstance(HITS_FRAGMENT, mBoard.getSize(), R.drawable.hits_bg, R.string.board_hits_title);

        mFragments = new BoardGridFragment[]{
                mShipsFragment, mHitsFragment
        };
    }

    public BoardGridFragment[] getFragments() {
        return mFragments;
    }

    public void displayHitInShipBoard(boolean hit, int x, int y) {
        mShipsFragment.putDrawable(hit ? R.drawable.hit : R.drawable.miss, x, y);
    }


    @Override
    public Hit sendHit(int x, int y) {
        Hit hit = mBoard.sendHit(x, y);
        if (hit != Hit.MISS) {
            mShipsFragment.putDrawable(R.drawable.miss, x, y);
        }
        return hit;
    }

    @Override
    public int getSize() {
        return mBoard.getSize();
    }

    @Override
    public void putShip(AbstractShip ship, int x, int y) {
        if (!(ship instanceof DrawableShip)) {
            throw new IllegalArgumentException("Cannot put a Ship that does not implement DrawableShip.");
        }
        DrawableShip dShip = (DrawableShip) ship;

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
            if ((p.x < 0 && p.x > getSize()) || (p.y < 0 || p.y > getSize())) {
                throw new ArrayIndexOutOfBoundsException("Ship beyond the horizon");
            }
            if (this.hasShip(p.x, p.y)) {
                throw new IllegalArgumentException("There already is a ship");
            }
        }
        mBoard.putShip(ship, x, y);
        switch (ship.getOrientation()) {
            case NORTH:
                y = y - ship.getLength() + 1;
                break;
            case WEST:
                x = x - ship.getLength() + 1;
                break;
        }
        mShipsFragment.putDrawable(dShip.getDrawable(), x, y);
    }

    @Override
    public boolean hasShip(int x, int y) {
        return mBoard.hasShip(x, y);
    }

    @Override
    public void setHit(boolean hit, int x, int y) {
        mBoard.setHit(hit, x, y);
        displayHitInShipBoard(hit, x, y);
    }

    @Override
    public Boolean getHit(int x, int y) {
        return mBoard.getHit(x, y);
    }

    @Override
    public void printBoard() {
    }

    @Override
    public boolean hasMoreShips() {
        return mBoard.hasMoreShips();
    }
}
