package com.abrastat.gsc;

import com.abrastat.general.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import static com.abrastat.general.Status.*;
import static com.abrastat.general.Game.checkPokemonAreNotFainted;

public class GSCGame implements Game {

    private int turnNumber = 0;

    private final GSCPokemon pokemonPlayerOne;
    private final GSCPokemon pokemonPlayerTwo;
    private int winner; // 0 = draw, 1 = p1, 2 = p2

    private int p1ReflectCounter = 0, p2ReflectCounter = 0;
    private int p1LightScreenCounter = 0, p2LightScreenCounter = 0;
    private int p1SafeguardCounter = 0, p2SafeguardCounter = 0;
    private GSCMove lastMoveUsed;

    public GSCGame(@NotNull GSCPlayer player1, @NotNull GSCPlayer player2) {

        this.pokemonPlayerOne = player1.getCurrentPokemon();
        this.pokemonPlayerTwo = player2.getCurrentPokemon();

        Messages.announceTeam(player1);
        Messages.announceTeam(player2);

        // Messages.announceSwitch(player1, pokemonPlayerOne);
        // Messages.announceSwitch(player2, pokemonPlayerTwo);

        while (!someoneFainted())   {
            GSCMove movePlayerOne = player1.chooseMove(player2);
            GSCMove movePlayerTwo = player2.chooseMove(player1);
            initTurn(movePlayerOne, movePlayerTwo);
        }
    }

    public void initTurn(GSCMove movePlayerOne, GSCMove movePlayerTwo)   {
        turnNumber++;
        Messages.announceTurn(turnNumber);
        Messages.displayCurrentHP(pokemonPlayerOne);
        Messages.displayCurrentHP(pokemonPlayerTwo);

        // prevents Counter or Mirror Coat from carrying over
        pokemonPlayerOne.setLastDamageTaken(0);
        pokemonPlayerTwo.setLastDamageTaken(0);

        // IF switch selected
            // IF pursuit also selected

        // IF speed priority !=0 move selected (Quick Attack, Protect, Roar)

        if (playerOneIsFaster()) { // true = player1, false = player2
            new GSCTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne);
            new GSCTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo);
        } else {
            new GSCTurn(pokemonPlayerTwo, pokemonPlayerOne, movePlayerTwo);
            new GSCTurn(pokemonPlayerOne, pokemonPlayerTwo, movePlayerOne);
        }

        // IF battle effects like Perish Song, Light Screen, Safeguard

        applyLeftovers(playerOneIsFaster());
        // thaw chance
        if (pokemonPlayerOne.getNonVolatileStatus() == FREEZE) {
            rollFreezeThaw(pokemonPlayerOne);
        }
        if (pokemonPlayerTwo.getNonVolatileStatus() == FREEZE) {
            rollFreezeThaw(pokemonPlayerTwo);
        }


    }

    private void rollFreezeThaw(GSCPokemon pokemon) {
        int roll = ThreadLocalRandom.current().nextInt(256);
        if (roll < 25) {
            pokemon.removeNonVolatileStatus();
            Messages.statusChanged(pokemon, FREEZE);
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

    public boolean someoneFainted() {
        if (pokemonPlayerOne.getCurrentHP() == 0)  {
            pokemonPlayerOne.removeNonVolatileStatusDebuff();
            pokemonPlayerOne.isFainted();
            Messages.logFainted(pokemonPlayerOne);
        }
        if (pokemonPlayerTwo.getCurrentHP() == 0)  {
            pokemonPlayerTwo.removeNonVolatileStatusDebuff();
            pokemonPlayerTwo.isFainted();
            Messages.logFainted(pokemonPlayerTwo);
        }
        setWinner();
        return checkPokemonAreNotFainted(pokemonPlayerOne, pokemonPlayerTwo);
    }

    public int getWinner()   {
        return this.winner;
    }

    private void setWinner() {
        if (pokemonPlayerOne.getNonVolatileStatus() == FAINT && pokemonPlayerTwo.getNonVolatileStatus() == FAINT)   {
            winner = 0;
        } else if (pokemonPlayerOne.getNonVolatileStatus() == FAINT)    {
            winner = 2;
        } else {
            winner = 1;
        }
    }


    private void applyLeftovers(boolean playerOneIsFaster) {

        // this is the cleanest way I can think of implementing this while respecting
        // the game mechanics.

        // flat integer division replicates in-game behaviour.

        if (checkPokemonAreNotFainted(pokemonPlayerOne, pokemonPlayerTwo))  {
            return;
        }

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

        else if (pokemonPlayerOne.getHeldItem() == Item.LEFTOVERS)   {
            pokemonPlayerOne.applyHeal(pokemonPlayerOne.getStatHP() / 16);
            Messages.leftoversHeal(pokemonPlayerOne);
            return;
        }

        else if (pokemonPlayerTwo.getHeldItem() == Item.LEFTOVERS)   {
            pokemonPlayerTwo.applyHeal(pokemonPlayerTwo.getStatHP() / 16);
            Messages.leftoversHeal(pokemonPlayerTwo);
            return;
        }
    }

    @Override
    public Move getLastMoveUsed() {
        return this.lastMoveUsed;
    }

    @Override
    public void setLastMoveUsed(GSCMove move) {
        this.lastMoveUsed = move;
    }
}
