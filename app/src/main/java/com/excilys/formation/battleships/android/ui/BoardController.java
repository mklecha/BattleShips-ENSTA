package com.excilys.formation.battleships.android.ui;

import com.excilys.formation.battleships.Hit;
import com.excilys.formation.battleships.IBoard;
import com.excilys.formation.battleships.ship.AbstractShip;

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
        mShipsFragment = BoardGridFragment.newInstance(SHIPS_FRAGMENT, mBoard.getSize(), R.drawable.ships_bg, R.string.grid_ships_title);
        mHitsFragment = BoardGridFragment.newInstance(HITS_FRAGMENT, mBoard.getSize(), R.drawable.hits_bg, R.string.grid_hits_title);

        mFragments = new BoardGridFragment[] {
            mShipsFragment, mHitsFragment
        };
    }

    @Override
    public Hit sendHit(int x, int y) {
        return mBoard.sendHit(x, y);
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
        mBoard.putShip(ship, x, y);

        AbstractShip.Orientation orientation = ship.getOrientation();
        switch (orientation) {
            case NORTH:
                y = y - ship.getLength() + 1;
                break;
            case WEST:
                x = x - ship.getLength() + 1;
                break;

        }
        mShipsFragment.putDrawable(((DrawableShip) ship).getDrawable(), x, y);
    }

    @Override
    public boolean hasShip(int x, int y) {
        return mBoard.hasShip(x, y);
    }

    @Override
    public void setHit(boolean hit, int x, int y) {
        mBoard.setHit(hit, x, y);
        mHitsFragment.putDrawable(hit ? R.drawable.hit : R.drawable.miss, x, y);
    }

    @Override
    public Boolean getHit(int x, int y) {
        return mBoard.getHit(x, y);
    }

    public BoardGridFragment[] getFragments() {
        return mFragments;
    }
}
