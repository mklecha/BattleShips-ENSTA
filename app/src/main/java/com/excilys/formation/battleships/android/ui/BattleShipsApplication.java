package com.excilys.formation.battleships.android.ui;

import android.app.Application;

import com.excilys.formation.battleships.logic.board.Board;
import com.excilys.formation.battleships.logic.player.Player;


public class BattleShipsApplication extends Application {

    /* ***
     * Attributes
     */
    private static Player[] mPlayers;

    // ...


    /* ***
     * Lifecycle
     */
    // ...

    /* ***
     * Methods
     */
    // ...
    public static BoardController getBoard() {
        // TODO complete me
        return null;
    }

    public static Board getOpponentBoard() {
        // TODO complete me
        return null;
    }

    public static Player[] getPlayers() {
        return mPlayers;
    }

    /* ***
     * Nested classes
     */
    // ...
}
