package com.abrastat.gsc;

import com.abrastat.general.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import static com.abrastat.general.Game.checkPokemonAreNotFainted;
import static com.abrastat.general.Status.*;
import static com.abrastat.gsc.GSCDamageCalc.calcDamage;

public class GSCTurn {

    public GSCTurn(GSCPokemon attackingPokemon, GSCPokemon defendingPokemon, GSCMove move) {

        // check neither pokemon is fainted
        if (checkPokemonAreNotFainted(attackingPokemon, defendingPokemon)) {
            return;
        }

        checkStatusMoveEffects(attackingPokemon);

        if (canAttack(attackingPokemon)) {

            Messages.logAttack(attackingPokemon, move);

            if (didAttackMiss(move.getAccuracy())) {

                Messages.logMissedAttack(attackingPokemon);

            } else {
                calcDamage(attackingPokemon, defendingPokemon, move);

                // for stuff like Counter as well as debugging
                rollSecondaryEffects(attackingPokemon, defendingPokemon, move);
            }

            move.reduceCurrentPP();

        }
    }

    private static void checkStatusMoveEffects(@NotNull GSCPokemon attackingPokemon) {

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

    private static boolean canAttack(@NotNull GSCPokemon attackingPokemon) {

        if (attackingPokemon.getNonVolatileStatus() == HEALTHY)    {
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

    private static boolean didAttackMiss(int accuracy) {
        int roll = ThreadLocalRandom.current().nextInt(256);
        return (accuracy < roll);
    }

    private static void rollSecondaryEffects(GSCPokemon self, GSCPokemon opponent, @NotNull Move move) {
        MoveSecondaryEffect effect = move.getSecondaryEffect();

        if (effect.getTarget() == MoveSecondaryEffect.Target.NONE)   {
            return;
        }

        int roll = ThreadLocalRandom.current().nextInt(256);

        if (roll < move.getSecondaryChance()) {

            switch (effect) {
                case PARALYSIS:
                    if (opponent.getNonVolatileStatus() != HEALTHY)
                    { break; }

                    opponent.applyNonVolatileStatus(PARALYSIS);
                    Messages.logNewStatus(opponent, PARALYSIS);
                    break;

                // TODO other effects
                default:
                    Messages.notImplementedYet(effect);
            }
        }
    }

}
