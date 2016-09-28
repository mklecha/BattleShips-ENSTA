package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.ship.Submarine;
import com.excilys.formation.battleships.android.ui.DrawableShip;

import java.util.HashMap;
import java.util.Map;

import battleships.formation.excilys.com.battleships.R;

public class DrawableSubmarine extends Submarine implements DrawableShip {

    private static final Map<Orientation, Integer> DRAWABLES = new HashMap<>();

    static {
        DRAWABLES.put(Orientation.EAST, R.drawable.submarine_e);
        DRAWABLES.put(Orientation.NORTH, R.drawable.submarine_n);
        DRAWABLES.put(Orientation.SOUTH, R.drawable.submarine_s);
        DRAWABLES.put(Orientation.WEST, R.drawable.submarine_w);
    }

    @Override
    public int getDrawable() {
        if (getOrientation() == null) {
            throw new IllegalStateException("ship without orientation cannot be drawn");
        }
        return DRAWABLES.get(getOrientation());
    }
}
