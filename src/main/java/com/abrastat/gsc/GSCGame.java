package com.abrastat.gsc;

import com.abrastat.general.*;

import java.util.concurrent.ThreadLocalRandom;

public final class GSCGame extends Game {

    private int turnNumber = 0;
    private GSCPokemon pokemonPlayerOne, pokemonPlayerTwo;

    public GSCGame() {
        super();
        this.pokemonPlayerOne = (GSCPokemon) getPlayerOne().getCurrentPokemon();
        this.pokemonPlayerTwo = (GSCPokemon) getPlayerTwo().getCurrentPokemon();
    }

    public void runTurn(Move movePlayerOne, Move movePlayerTwo)   {
        turnNumber++;
        if (playerOneIsFaster()) { // true = player1, false = player2
            takeTurn(pokemonPlayerOne, movePlayerOne);
            takeTurn(pokemonPlayerTwo, movePlayerTwo);
        } else {
            takeTurn(pokemonPlayerTwo, movePlayerTwo);
            takeTurn(pokemonPlayerOne, movePlayerOne);
        }
    }

    private void takeTurn(GSCPokemon pokemon, Move move) {

    }

    private boolean playerOneIsFaster() {

        // TODO Quick Attack, Mach Punch, Roar etc. priority moves logic checking
        return (
                pokemonPlayerOne.getStatSpe() == pokemonPlayerTwo.getStatSpe()
                        ?
                        ThreadLocalRandom.current().nextBoolean() // random player to go first this turn
                        :
                        pokemonPlayerOne.getStatSpe() ==
                                Math.max(pokemonPlayerOne.getStatSpe(), pokemonPlayerTwo.getStatSpe())
        );
    }
}
