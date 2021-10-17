package com.abrastat.gsc;

import com.abrastat.general.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.ThreadLocalRandom;

public final class GSCGame extends Game {

    private int turnNumber = 0;
    private GSCPokemon pokemonPlayer1, pokemonPlayer2;
    private MutablePair<GSCPokemon, GSCPokemon> turnOrderPair = new MutablePair<>();

    public GSCGame() {
        super();
    }

    public void runTurn(Move movePlayer1, Move movePlayer2)   {
        turnNumber++;
        if (calculateWhosFaster()) { // true = player1, false = player2
            takeTurns(pokemonPlayer1, pokemonPlayer2);
        } else {
            takeTurns(pokemonPlayer2, pokemonPlayer1);
        }
    }

    private void takeTurns(GSCPokemon fasterPokemon, GSCPokemon slowerPokemon) {

    }

    private boolean calculateWhosFaster() {
        return (
                pokemonPlayer1.getStatSpe() == pokemonPlayer2.getStatSpe()
                        ?
                        ThreadLocalRandom.current().nextBoolean() // random player to go first this turn
                        :
                        pokemonPlayer1.getStatSpe() ==
                                Math.max(pokemonPlayer1.getStatSpe(), pokemonPlayer2.getStatSpe())
        );
    }
}
