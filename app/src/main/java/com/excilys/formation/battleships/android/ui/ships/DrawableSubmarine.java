package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.logic.ships.Submarine;

import battleships.formation.excilys.com.battleships.R;

public class DrawableSubmarine extends Submarine implements DrawableShip {
    @Override
    public int getDrawable() {
        switch (super.getOrientation()) {
            case NORTH:
                return R.drawable.submarine_n;
            case SOUTH:
                return R.drawable.submarine_s;
            case EAST:
                return R.drawable.submarine_e;
            case WEST:
                return R.drawable.submarine_w;
        }
        throw new IllegalArgumentException();
    }
}
