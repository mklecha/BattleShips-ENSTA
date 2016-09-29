package com.excilys.formation.battleships.android.ui;

import android.app.Application;

import com.excilys.formation.battleships.Board;
import com.excilys.formation.battleships.Player;

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
        return null;
    }

    public static Board getOpponentBoard() {
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
