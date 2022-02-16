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

        if (canAttack(attackingPokemon, move)) {

            Messages.logAttack(attackingPokemon, move);

            if (didAttackMiss(move.accuracy())) {

                Messages.logMissedAttack(attackingPokemon);

            } else {
                calcDamage(attackingPokemon, defendingPokemon, move);

                // for stuff like Counter as well as debugging
                rollSecondaryEffects(attackingPokemon, defendingPokemon, move);
            }

            attackingPokemon.decrementMovePp(move);

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
                    attackingPokemon.incrementConfuseCounter();
                    // increment counter, roll hurt self chance, check for end chance
                    break;
                case ENCORE:
                    roll = ThreadLocalRandom.current().nextInt(256);
                    attackingPokemon.incrementEncoreCounter();
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

    private static boolean canAttack(@NotNull GSCPokemon attackingPokemon, @NotNull GSCMove move) {

        if (attackingPokemon.getNonVolatileStatus() == HEALTHY)    {
            return true;
        }

        int roll = ThreadLocalRandom.current().nextInt(256);  // handles RNG factor

        switch (attackingPokemon.getNonVolatileStatus())   {
            case REST:
            case SLEEP: // decrement sleep counter, check for sleep talk call, skip turn otherwise
                if (attackingPokemon.getSleepCounter() > 0) {

                    Messages.cantAttack(attackingPokemon, SLEEP);
                    attackingPokemon.decrementSleepCounter();

                    if (move == GSCMove.SLEEP_TALK) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                else    {
                    Messages.statusChanged(attackingPokemon, SLEEP);
                    return true;
                }

            case FREEZE:
                // if (move != GSCMove.FLAME_WHEEL)
                Messages.cantAttack(attackingPokemon, FREEZE);
                return false;

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
        MoveEffect effect = move.effect();
        if (effect.target() == MoveEffect.Target.NONE)   {
            return;
        }

        // Guaranteed effects go here!
        if (effect.chance() == 0)   {
            switch (effect) {
                case RECOIL25:
                case STRUGGLE:
                    int recoil = opponent.getLastDamageTaken() / 4;
                    self.applyDamage(recoil);
                    Messages.logRecoil(self, recoil);
            }
            return;
        }

        int roll = ThreadLocalRandom.current().nextInt(256);

        // Chance probability attacks go here!
        if (roll < move.effect().chance()) {

            switch (effect) {
                case THUNDER:
                case PRZ100:
                case PRZ30:
                case PRZ10:
                case PRZ:

                    if (opponent.getNonVolatileStatus() != HEALTHY)
                    { break; }

                    opponent.applyNonVolatileStatus(PARALYSIS);
                    Messages.logNewStatus(opponent, PARALYSIS);
                    break;

                case FRZ10:
                    if (opponent.getNonVolatileStatus() != HEALTHY)
                    { break; }

                    opponent.applyNonVolatileStatus(FREEZE);
                    Messages.logNewStatus(opponent, FREEZE);
                    break;

                case BRN10:
                case SACREDFIRE:
                case FLAMEWHEEL:
                    if (opponent.getNonVolatileStatus() != HEALTHY)
                    { break; }

                    opponent.applyNonVolatileStatus(BURN);
                    Messages.logNewStatus(opponent, BURN);

                // TODO other effects
                default:
                    Messages.notImplementedYet(effect);
            }
        }
    }

}
