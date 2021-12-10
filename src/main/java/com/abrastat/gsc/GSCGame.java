package com.abrastat.gsc;

import com.abrastat.general.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import static com.abrastat.gsc.GSCDamageCalc.*;
import static com.abrastat.general.Status.*;

public final class GSCGame implements Game {

    private int turnNumber = 0;

    private GSCPokemon pokemonPlayerOne, pokemonPlayerTwo;

    private int p1ReflectCounter = 0, p2ReflectCounter = 0;
    private int p1LightScreenCounter = 0, p2LightScreenCounter = 0;
    private int p1SafeguardCounter = 0, p2SafeguardCounter = 0;

    public GSCGame() {

        this.pokemonPlayerOne = (GSCPokemon) player1.getCurrentPokemon();
        this.pokemonPlayerTwo = (GSCPokemon) player2.getCurrentPokemon();
    }



    public void initTurn(GSCMove movePlayerOne, GSCMove movePlayerTwo)   {
        turnNumber++;

        // IF switch selected
            // IF pursuit also selected

        // IF speed priority !=0 move selected (Quick Attack, Protect, Roar)

        if (playerOneIsFaster()) { // true = player1, false = player2
            takeTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne);
            takeTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo);
        } else {
            takeTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo);
            takeTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne);
        }

        // IF battle effects like Perish Song, Light Screen, Safeguard

        applyLeftovers(playerOneIsFaster());

        // thaw chance


    }

    private void takeTurn(GSCPokemon attackingPokemon, GSCPokemon defendingPokemon, GSCMove move) {
        if (someoneFainted())
        { return; } // end turn when either member faints

        checkStatusMoveEffects(attackingPokemon);
        if (canAttack(attackingPokemon)) {
            Messages.logAttack(attackingPokemon, move);
            calcDamage(attackingPokemon, defendingPokemon, move);
        }
        checkStatusAfterEffects(attackingPokemon);

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

    private boolean canAttack(@NotNull GSCPokemon attackingPokemon) {

        if (attackingPokemon.getNonVolatileStatus() == null)    {
            return true;
        }

        int roll = ThreadLocalRandom.current().nextInt(256);  // handles RNG factor

        switch (attackingPokemon.getNonVolatileStatus())   {
            // case SLEEP: // roll sleep awakening chance, increment sleep counter, check for sleep talk call, skip turn otherwise
            case REST:
                if (attackingPokemon.getSleepCounter() < 2) {
                    Messages.cantAttack(attackingPokemon, REST);
                    attackingPokemon.incrementSleepCounter();
                    return false;
                } else {
                    Messages.statusChanged(attackingPokemon, SLEEP);
                    return true;
                }
            // case FREEZE: // roll thaw chance, skip turn (always)
            case PARALYSIS:
                if (roll < 64) {
                    Messages.cantAttack(attackingPokemon, PARALYSIS);
                    return false;
                } else {
                    return true;
                }
            default:
                return true;
        }
    }

    private void checkStatus(GSCPokemon attackingPokemon) {

    }

    private void checkStatusMoveEffects(@NotNull GSCPokemon attackingPokemon) {

        int roll;    // handles RNG factor

        // some of these effects override others and need to be checked first as such
        // TODO
//        switch (attackingPokemon.getNonVolatileStatus())    {
//
//        }

        // TODO
        for (Status status : attackingPokemon.getVolatileStatus()) {
            switch (status) {
                case ATTRACT:
                    roll = ThreadLocalRandom.current().nextInt(256);
                    // roll move fail chance
                    break;
                case CONFUSION:
                case FATIGUE:
                    roll = ThreadLocalRandom.current().nextInt(256);
                    // increment counter, roll hurt self chance, check for end chance
                    break;
                case ENCORE:
                    roll = ThreadLocalRandom.current().nextInt(256);
                    // increment counter, override attack to instead be previously used attack, check for end chance
                    break;
                case LOCKON:
                    roll = ThreadLocalRandom.current().nextInt(256);
                    // remove accuracy checks (for this turn only), remove LOCKON status
                    break;
                case DISABLE:
                    roll = ThreadLocalRandom.current().nextInt(256);
                    // increment counter, block some attack from being selected, check for end chance
                    break;
            }
        }
    }

    private void checkStatusAfterEffects(@NotNull GSCPokemon attackingPokemon) {

        if (attackingPokemon.getNonVolatileStatus() == null)    {
            return;
        }

        // TODO
        switch (attackingPokemon.getNonVolatileStatus())    {
            case BURN:
                // apply burn damage
                break;
            case POISON:
                // apply poison damage
                break;
            case TOXIC:
                // apply toxic damage
                break;
        }

        //TODO
//        for (Status status : attackingPokemon.getVolatileStatus()) {
//            switch (status) {
//                case LEECHSEED:
//                    // apply leech seed damage
//                    // apply leech seed healing of the opponent
//                    break;
//                case CURSE:
//                    // apply curse damage
//                    break;
//                case NIGHTMARE:
//                    // apply nightmare damage if asleep
//                    break;
//                case SAFEGUARD:
//                    // decrement safeguard counter
//                    break;
//            }
//        }
    }

    private void applyLeftovers(boolean playerOneIsFaster) {

        // this is the cleanest way I can think of implementing this while respecting
        // the game mechanics.

        // flat integer division replicates in-game behaviour.

        if (pokemonPlayerOne.getHeldItem() == Item.LEFTOVERS && pokemonPlayerTwo.getHeldItem() == Item.LEFTOVERS)   {
            if (playerOneIsFaster == true)  {
                pokemonPlayerOne.applyHeal(pokemonPlayerOne.getStatHP() / 16);
                Messages.leftoversHeal(pokemonPlayerOne);
                pokemonPlayerTwo.applyHeal(pokemonPlayerTwo.getStatHP() / 16);
                Messages.leftoversHeal(pokemonPlayerTwo);
            } else {
                pokemonPlayerTwo.applyHeal(pokemonPlayerTwo.getStatHP() / 16);
                Messages.leftoversHeal(pokemonPlayerTwo);
                pokemonPlayerOne.applyHeal(pokemonPlayerOne.getStatHP() / 16);
                Messages.leftoversHeal(pokemonPlayerOne);
            }
            return;
        }

        if (pokemonPlayerOne.getHeldItem() == Item.LEFTOVERS)   {
            pokemonPlayerOne.applyHeal(pokemonPlayerOne.getStatHP() / 16);
            Messages.leftoversHeal(pokemonPlayerOne);
            return;
        }

        if (pokemonPlayerTwo.getHeldItem() == Item.LEFTOVERS)   {
            pokemonPlayerTwo.applyHeal(pokemonPlayerTwo.getStatHP() / 16);
            Messages.leftoversHeal(pokemonPlayerTwo);
            return;
        }
    }

    @Override
    public boolean checkPokemonAreNotFainted() {
        {
            return player1.getCurrentPokemon().getCurrentHP() > 0
                   &&
                   player2.getCurrentPokemon().getCurrentHP() > 0;
        }
    }
}
