package com.abrastat.general;

public abstract class Game {

    private Player playerOne;
    private Player playerTwo;

    public Game() {

        this.getPlayerOne();
        this.getPlayerTwo();

    }

    protected Player getPlayerOne()    {
        return this.playerOne;
    }

    protected Player getPlayerTwo()    {
        return this.playerTwo;
    }

    protected boolean checkPokemonAreNotFainted() {
        return playerOne.getCurrentPokemon().getCurrentHP() > 0
               &&
               playerTwo.getCurrentPokemon().getCurrentHP() > 0;
    }

}

