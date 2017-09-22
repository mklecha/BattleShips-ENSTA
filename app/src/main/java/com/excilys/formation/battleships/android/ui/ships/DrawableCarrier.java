package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.logic.ships.Carrier;

import battleships.formation.excilys.com.battleships.R;

public class DrawableCarrier extends Carrier implements DrawableShip {
    @Override
    public int getDrawable() {
        switch (super.getOrientation()) {
            case NORTH:
                return R.drawable.carrier_n;
            case SOUTH:
                return R.drawable.carrier_s;
            case EAST:
                return R.drawable.carrier_e;
            case WEST:
                return R.drawable.carrier_w;
        }
        throw new IllegalArgumentException();

    }
}
