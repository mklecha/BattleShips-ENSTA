package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.logic.ships.Destroyer;

import battleships.formation.excilys.com.battleships.R;

public class DrawableDestroyer extends Destroyer implements DrawableShip {

    @Override
    public int getDrawable() {
        switch (super.getOrientation()) {
            case NORTH:
                return R.drawable.destroyer_n;
            case SOUTH:
                return R.drawable.destroyer_s;
            case EAST:
                return R.drawable.destroyer_e;
            case WEST:
                return R.drawable.destroyer_w;
        }
        throw new IllegalArgumentException();
    }
}
