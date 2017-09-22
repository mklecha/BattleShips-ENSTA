package com.excilys.formation.battleships.android.ui;

import android.app.Application;

import com.excilys.formation.battleships.logic.board.Board;
import com.excilys.formation.battleships.logic.player.AIPlayer;
import com.excilys.formation.battleships.logic.player.Player;
import com.excilys.formation.battleships.logic.ships.AbstractShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BattleShipsApplication extends Application {
    private static final BattleShipsApplication instance = new BattleShipsApplication();
    private Game game;

    private BattleShipsApplication() {
        r = new Random();
        game = new Game();
    }

    public static BattleShipsApplication getInstance() {
        return instance;
    }

    private Player[] mPlayers;
    private BoardController mBoard;
    private Board mOpponentBoard;
    private Random r;

    public void init(String name) {
        game.init(name);
    }

    public BoardController getBoard() {
        return mBoard;
    }

    public Board getOpponentBoard() {
        return mOpponentBoard;
    }

    public Player[] getPlayers() {
        return mPlayers;
    }

    public Random getRand() {
        return r;
    }

    public Game getGame() {
        return game;
    }

    class Game {

        private Player mPlayer1;
        private Player mPlayer2;

        public Game() {
        }

        void init(String playerName) {
            Board b = new Board(playerName);
            mBoard = new BoardController(b);
            mOpponentBoard = new Board("IA");

            List<AbstractShip>[] ships = getRandomShips();

            mPlayer1 = new Player(b, mOpponentBoard, ships[0]);
            mPlayer2 = new AIPlayer(mOpponentBoard, b, ships[1]);

//            mPlayer1.putShips();
            mPlayer2.putShips();
            mPlayers = new Player[]{mPlayer1, mPlayer2};
        }

        private List<AbstractShip>[] getRandomShips() {
            List<AbstractShip> l1 = new ArrayList<>(), l2 = new ArrayList<>();
            for (int i = 0; i < 3 + r.nextInt(7); i++) {
                int whichShip = 2 + r.nextInt(3);
                l1.add(AbstractShip.getShip(whichShip));
                l2.add(AbstractShip.getShip(whichShip));
            }

            return new List[]{l1, l2};
        }
    }
}
