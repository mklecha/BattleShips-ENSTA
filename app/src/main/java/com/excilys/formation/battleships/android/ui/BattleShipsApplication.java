package com.excilys.formation.battleships.android.ui;

import android.app.Application;
import android.content.Intent;

import com.excilys.formation.battleships.AIPlayer;
import com.excilys.formation.battleships.Board;
import com.excilys.formation.battleships.Player;
import com.excilys.formation.battleships.android.ui.ships.DrawableBattleship;
import com.excilys.formation.battleships.android.ui.ships.DrawableCarrier;
import com.excilys.formation.battleships.android.ui.ships.DrawableDestroyer;
import com.excilys.formation.battleships.android.ui.ships.DrawableSubmarine;
import com.excilys.formation.battleships.ship.AbstractShip;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nicolas on 27/09/16.
 */

public class BattleShipsApplication extends Application {

    /* ***
     * Attributes
     */
    private static Player[] mPlayers;
    private static BoardController mBoard1;
    private static Board mBoard2;
    private static Game mGame;
    private static String mPlayerName;


    /* ***
     * Lifecycle
     */

    @Override
    public void onCreate() {
        mGame = new Game();
    }

    /* ***
     * Methods
     */


    public static Player[] getPlayers() {
        return mPlayers;
    }

    public static BoardController getBoard() {
        return mBoard1;
    }

    public static Board getOpponentBoard() {
        return mBoard2;
    }


    public static Game getGame() {
        return mGame;
    }

    public static String getPlayerName() {
        return mPlayerName;
    }
    /* ***
     * Nested classes
     */

    public class AndroidPlayer extends Player {

        public AndroidPlayer(Board b1, Board b2, List<AbstractShip> ships) {
            super(b1, b2, ships);
        }

        @Override
        public void  putShips() {
            Intent intent = new Intent(BattleShipsApplication.this, PutShipsActivity.class);
            startActivity(intent);

        }
    }

    public class Game {

        /* ***
         * Attributes
         */
        private AndroidPlayer mPlayer1;
        private AIPlayer mPlayer2;
        /* ***
         * Methods
         */
        public Game() {
        }

        public Game init(String playerName) {
            mPlayerName = playerName;
            // TODO init boards
            Board b = new Board(playerName);
            mBoard1 = new BoardController(b);
            mBoard2 = new Board("IA");

            // TODO init this.mPlayer1 & this.mPlayer2
            mPlayer1 = new AndroidPlayer(b, mBoard2, createDefaultShips());
            mPlayer2 = new AIPlayer(mBoard2, b, createDefaultShips());

            // place player ships
            mPlayer1.putShips();
            mPlayer2.putShips();
            mPlayers = new Player[] {mPlayer1, mPlayer2};

            return this;
        }

        private List<AbstractShip> createDefaultShips() {
            return Arrays.asList(new AbstractShip[]{new DrawableDestroyer(), new DrawableSubmarine(), new DrawableSubmarine(), new DrawableBattleship(), new DrawableCarrier()});
        }
    }
}
