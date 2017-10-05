package com.excilys.formation.battleships.dbentities;

import com.orm.SugarRecord;

/**
 * trident 05/10/17.
 */

public class User extends SugarRecord<User>{
    String name;
    int gamesWin;
    int gamesDone;

    public User(){

    }

    public User(String name, int gamesWin, int gamesDone) {
        this.name = name;
        this.gamesWin = gamesWin;
        this.gamesDone = gamesDone;
    }

    public User(String name) {
        this.name = name;
        this.gamesWin = 0;
        this.gamesDone = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesWin() {
        return gamesWin;
    }

    public void setGamesWin(int gamesWin) {
        this.gamesWin = gamesWin;
    }

    public int getGamesDone() {
        return gamesDone;
    }

    public void setGamesDone(int gamesDone) {
        this.gamesDone = gamesDone;
    }
}
