package com.excilys.formation.battleships.android.ui.ships;

import com.excilys.formation.battleships.logic.ships.Battleship;

import battleships.formation.excilys.com.battleships.R;

public class DrawableBattleship extends Battleship implements DrawableShip {
    @Override
    public int getDrawable() {
        switch (super.getOrientation()) {
            case NORTH:
                return R.drawable.battleship_n;
            case SOUTH:
                return R.drawable.battleship_s;
            case EAST:
                return R.drawable.battleship_e;
            case WEST:
                return R.drawable.battleship_w;
        }
        throw new IllegalArgumentException();
    }
}
