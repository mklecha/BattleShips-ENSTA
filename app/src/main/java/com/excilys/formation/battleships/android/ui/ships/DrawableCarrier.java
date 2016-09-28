package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.ship.Carrier;
import com.excilys.formation.battleships.android.ui.DrawableShip;

import java.util.HashMap;
import java.util.Map;

import battleships.formation.excilys.com.battleships.R;

public class DrawableCarrier extends Carrier implements DrawableShip {

    private static final Map<Orientation, Integer> DRAWABLES = new HashMap<>();

    static {
        DRAWABLES.put(Orientation.EAST, R.drawable.carrier_e);
        DRAWABLES.put(Orientation.NORTH, R.drawable.carrier_n);
        DRAWABLES.put(Orientation.SOUTH, R.drawable.carrier_s);
        DRAWABLES.put(Orientation.WEST, R.drawable.carrier_w);
    }

    @Override
    public int getDrawable() {
        if (getOrientation() == null) {
            throw new IllegalStateException("ship without orientation cannot be drawn");
        }
        return DRAWABLES.get(getOrientation());
    }
}
