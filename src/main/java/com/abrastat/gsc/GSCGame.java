package com.abrastat.gsc;

import com.abrastat.general.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import com.abrastat.general.MoveSecondaryEffect;
import static com.abrastat.gsc.GSCDamageCalc.*;
import static com.abrastat.general.Status.*;

public final class GSCGame implements Game {

    private int turnNumber = 0;

    private GSCPokemon pokemonPlayerOne, pokemonPlayerTwo;

    private int p1ReflectCounter = 0, p2ReflectCounter = 0;
    private int p1LightScreenCounter = 0, p2LightScreenCounter = 0;
    private int p1SafeguardCounter = 0, p2SafeguardCounter = 0;
    private int lastDamageValue;
    private GSCMove lastMoveUsed;

    public GSCGame() {

        this.player1.setName("Youngster Joey");
        this.player2.setName("Bug Catcher Don");

        this.pokemonPlayerOne = (GSCPokemon) player1.getCurrentPokemon();
        this.pokemonPlayerTwo = (GSCPokemon) player2.getCurrentPokemon();

        Messages.announceTeam(player1);
        Messages.announceTeam(player2);
    }



    public void initTurn(GSCMove movePlayerOne, GSCMove movePlayerTwo)   {
        turnNumber++;
        Messages.announceTurn(turnNumber);

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

        this.lastDamageValue = 0;
    }

    private void takeTurn(GSCPokemon attackingPokemon, GSCPokemon defendingPokemon, GSCMove move) {

        // both Pokemon can faint at the same time in the instance of moves like Explosion
        if (checkPokemonAreNotFainted()) {
            return; // end turn when either member faints
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
                this.lastDamageValue = defendingPokemon.getLastDamageTaken();
                this.lastMoveUsed = move;
            }
        }

        checkStatusAfterEffects(attackingPokemon);
        this.setLastMoveUsed(move);

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

    public boolean someoneFainted() {
        if (pokemonPlayerOne.getCurrentHP() == 0)  {
            Messages.logFainted(pokemonPlayerOne);
        }
        if (pokemonPlayerTwo.getCurrentHP() == 0)  {
            Messages.logFainted(pokemonPlayerTwo);
        }
        return checkPokemonAreNotFainted();
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

    private boolean didAttackMiss(int accuracy) {
        int roll = ThreadLocalRandom.current().nextInt(256);
        return (accuracy < roll);
    }

    private void rollSecondaryEffects(GSCPokemon self, GSCPokemon opponent, @NotNull Move move) {
        MoveSecondaryEffect effect = move.getSecondaryEffect();

        if (effect.getTarget() == MoveSecondaryEffect.Target.NONE)   {
            return;
        }

        int roll = ThreadLocalRandom.current().nextInt(256);

        if (roll < move.getSecondaryChance()) {

            switch (effect) {
                case PARALYSIS:
                    if (opponent.getNonVolatileStatus() == PARALYSIS)
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

    public void setLastMoveUsed(GSCMove move)  {
        this.lastMoveUsed = move;
    }

    public GSCMove getLastMoveUsed(GSCMove move)   {
        return this.lastMoveUsed;
    }

    public void setLastDamageValue(int damageValue) {
        this.lastDamageValue = damageValue;
    }

    public int getLastDamageValue() {
        return this.lastDamageValue;
    }

    @Override
    public boolean checkPokemonAreNotFainted() {
        return this.pokemonPlayerOne.getCurrentHP() == 0 || this.pokemonPlayerTwo.getCurrentHP() == 0;
    }
}
