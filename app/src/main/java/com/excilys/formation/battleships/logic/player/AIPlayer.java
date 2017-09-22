package com.excilys.formation.battleships.logic.player;



import com.excilys.formation.battleships.logic.BattleShipsAI;
import com.excilys.formation.battleships.logic.Hit;
import com.excilys.formation.battleships.logic.board.Board;
import com.excilys.formation.battleships.logic.ships.AbstractShip;

import java.util.List;

public class AIPlayer extends Player {
    private BattleShipsAI ai;

    public AIPlayer(Board ownBoard, Board opponentBoard, List<AbstractShip> ships) {
        super(ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
//        ai = new MyAI(ownBoard, opponentBoard);
    }

    @Override
    public void putShips() {
        ai.putShips(ships);
    }

    @Override
    public Hit sendHit(int[] coords) {
        return ai.sendHit(coords);
    }
}