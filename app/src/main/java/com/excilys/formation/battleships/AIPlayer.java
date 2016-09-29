package com.excilys.formation.battleships;

import com.excilys.formation.battleships.ship.AbstractShip;

import java.io.Serializable;
import java.util.List;

public class AIPlayer extends Player implements Serializable {
    private BattleShipsAI ai;
    public AIPlayer(String name, Board ownBoard, Board opponentBoard, List<AbstractShip> ships) {
        super(name, ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
    }

    // TODO AIPlayer must not inherit "keyboard behavior" from player. Call ai instead.

    @Override
    public void putShips() {
        ai.putShips(ships);
    }

    public Hit sendHit(int[] coords) {
        return ai.sendHit(coords);
    }
}