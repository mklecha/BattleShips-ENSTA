package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.ship.Destroyer;
import com.excilys.formation.battleships.android.ui.DrawableShip;

import java.util.HashMap;
import java.util.Map;

import battleships.formation.excilys.com.battleships.R;

public class DrawableDestroyer extends Destroyer implements DrawableShip {

    private static final Map<Orientation, Integer> DRAWABLES = new HashMap<>();

    static {
        DRAWABLES.put(Orientation.EAST, R.drawable.destroyer_e);
        DRAWABLES.put(Orientation.NORTH, R.drawable.destroyer_n);
        DRAWABLES.put(Orientation.SOUTH, R.drawable.destroyer_s);
        DRAWABLES.put(Orientation.WEST, R.drawable.destroyer_w);
    }

    @Override
    public int getDrawable() {
        if (getOrientation() == null) {
            throw new IllegalStateException("ship without orientation cannot be drawn");
        }
        return DRAWABLES.get(getOrientation());
    }
}
