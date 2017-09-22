package com.excilys.formation.battleships.android.ui;

import android.app.Application;
import android.content.Intent;

import com.excilys.formation.battleships.logic.board.Board;
import com.excilys.formation.battleships.logic.player.AIPlayer;
import com.excilys.formation.battleships.logic.player.Player;
import com.excilys.formation.battleships.logic.ships.AbstractShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BattleShipsApplication extends Application {
    private static Game game;

    private static Player[] mPlayers;
    private static Board mOpponentBoard;
    private static Random rand;
    private static BoardController mBoard;

    @Override
    public void onCreate() {
        super.onCreate();
        game = new Game();
        rand = new Random();
    }

    public static void init(String name) {
        game.init(name);
    }


    public static Game getGame() {
        return game;
    }

    public static Player[] getPlayers() {
        return mPlayers;
    }

    public static Board getOpponentBoard() {
        return mOpponentBoard;
    }

    public static BoardController getBoard() {
        return mBoard;
    }

    public static Random getRand() {
        return rand;
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

            List<AbstractShip>[] ships = getRandomDrawableShips();

            mPlayer1 = new AndroidPlayer(b, mOpponentBoard, ships[0]);
            mPlayer2 = new AIPlayer(mOpponentBoard, b, ships[1]);

            mPlayer1.putShips();
            mPlayer2.putShips();
            mPlayers = new Player[]{mPlayer1, mPlayer2};
        }

        private List<AbstractShip>[] getRandomShips() {
            List<AbstractShip> l1 = new ArrayList<>(), l2 = new ArrayList<>();
            for (int i = 0; i < 3 + rand.nextInt(7); i++) {
                int whichShip = 2 + rand.nextInt(3);
                l1.add(AbstractShip.getShip(whichShip));
                l2.add(AbstractShip.getShip(whichShip));
            }

            return new List[]{l1, l2};
        }

        private List<AbstractShip>[] getRandomDrawableShips() {
            List<AbstractShip> l1 = new ArrayList<>(), l2 = new ArrayList<>();
            for (int i = 0; i < 3 + rand.nextInt(7); i++) {
                int whichShip = 2 + rand.nextInt(3);
                l1.add(AbstractShip.getDrawableShip(whichShip));
                l2.add(AbstractShip.getDrawableShip(whichShip));
            }

            return new List[]{l1, l2};
        }
    }

    class AndroidPlayer extends Player {
        public AndroidPlayer(Board board, Board opponentBoard, List<AbstractShip> ships) {
            super(board, opponentBoard, ships);
        }

        @Override
        public void putShips() {
            Intent intent = new Intent(BattleShipsApplication.this, PutShipsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
