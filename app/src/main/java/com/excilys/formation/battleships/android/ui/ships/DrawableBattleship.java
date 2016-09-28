package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.android.ui.DrawableShip;
import com.excilys.formation.battleships.ship.BattleShip;

import java.util.HashMap;
import java.util.Map;

import battleships.formation.excilys.com.battleships.R;

public class DrawableBattleship extends BattleShip implements DrawableShip {

    private static final Map<Orientation, Integer> DRAWABLES = new HashMap<>();

    static {
        DRAWABLES.put(Orientation.EAST, R.drawable.battleship_e);
        DRAWABLES.put(Orientation.NORTH, R.drawable.battleship_n);
        DRAWABLES.put(Orientation.SOUTH, R.drawable.battleship_s);
        DRAWABLES.put(Orientation.WEST, R.drawable.battleship_w);
    }

    @Override
    public int getDrawable() {
        if (getOrientation() == null) {
            throw new IllegalStateException("ship without orientation cannot be drawn");
        }
        return DRAWABLES.get(getOrientation());
    }
}