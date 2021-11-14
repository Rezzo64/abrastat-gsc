package com.abrastat.gsc;

import com.abrastat.general.*;
import java.util.concurrent.ThreadLocalRandom;
import static com.abrastat.gsc.GSCDamageCalc.*;

public final class GSCGame extends Game {

    private int turnNumber = 0;
    private GSCPokemon pokemonPlayerOne, pokemonPlayerTwo;

    public GSCGame() {
        super();
        this.pokemonPlayerOne = (GSCPokemon) getPlayerOne().getCurrentPokemon();
        this.pokemonPlayerTwo = (GSCPokemon) getPlayerTwo().getCurrentPokemon();
    }

    public void initTurn(GSCMove movePlayerOne, GSCMove movePlayerTwo)   {
        turnNumber++;

        // IF speed priority move selected (Quick Attack, Roar)
        // IF Pursuit selected

        if (playerOneIsFaster()) { // true = player1, false = player2
            takeTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne);
            takeTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo);
        } else {
            takeTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo);
            takeTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne);
        }

        // IF battle effects like Perish Song, Light Screen

    }

    private void takeTurn(GSCPokemon attackingPokemon, GSCPokemon defendingPokemon, GSCMove move) {
        if (someoneFainted())
        { return; } // end turn when either member faints

        if (canAttack(attackingPokemon)) {
            calcDamage(attackingPokemon, defendingPokemon, move);
        }


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

    private boolean someoneFainted() {
        return this.pokemonPlayerOne.getCurrentHP() == 0 || this.pokemonPlayerTwo.getCurrentHP() == 0;
    }

    private boolean canAttack(GSCPokemon attackingPokemon) {

        int roll;

        switch (attackingPokemon.getVolatileStatus())   {
            case PARALYSIS:
                roll = ThreadLocalRandom.current().nextInt(256);
                if (roll < 64) {

                }
                break;
            case SLEEP:
        }

        return true;
    }

    private void checkStatus(GSCPokemon attackingPokemon) {


    }

}
