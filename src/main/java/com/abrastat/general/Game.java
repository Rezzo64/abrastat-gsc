package com.abrastat.general;

public abstract class Game {

    private Player playerOne;
    private Player playerTwo;

    public Game() {

    }

    public Player getPlayerOne()    {
        return this.playerOne;
    }

    public Player getPlayerTwo()    {
        return this.playerTwo;
    }
}

