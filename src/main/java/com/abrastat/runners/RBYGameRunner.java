package com.abrastat.runners;

import com.abrastat.general.*;
import com.abrastat.rby.*;

public class RBYGameRunner {
    // this program takes in two teams of pokemon
    // and the number of simulations to record
    // the number of wins, losses, and draws
    private final RBYPlayer player1;
    private final RBYPlayer player2;

    private int p1WinnerCount = 0,
            p2WinnerCount = 0,
            drawCount = 0;

    public RBYGameRunner(RBYPokemon[] p1Pokemons,
                         RBYPokemon[] p2Pokemons,
                         int simulationCount) {
        player1 = setup("Ash Ketchum", p1Pokemons);
        player2 = setup("Gary Oak", p2Pokemons);

        // TODO choose behavior
        //  let AI choose behavior

        simulate(simulationCount, PlayerBehaviour.JUST_ATTACK, PlayerBehaviour.JUST_ATTACK);
    }

    private RBYPlayer setup(String name, RBYPokemon[] pokemons) {
        if (pokemons.length == 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "Player must have at least one pokemon"
            );
        }
        if (pokemons.length > 6) {
            throw new ArrayIndexOutOfBoundsException(
                    "Player can only have up to six pokemon"
            );
        }

        RBYPokemon pokemon1 = pokemons[0];
        RBYPlayer player = new RBYPlayer(name, pokemon1);
        for (int i = 1; i < pokemons.length; i++)
            player.addPokemon(pokemons[i]);
        return player;
    }

    private void simulate(int simulationCount,
                          PlayerBehaviour p1Behaviours,
                          PlayerBehaviour p2Behaviours) {
        RBYGame game = new RBYGame(player1, p1Behaviours, player2, p2Behaviours);
        for (int i = 0; i < simulationCount; i++) {
            game.main(player1, player2);

            // record results
            switch (game.getWinner()) {
                case 0: nobodyWins();   break;
                case 1: p1Wins();       break;
                case 2: p2Wins();       break;
            }
            displayResults(i);

            // return to state 0
            refreshTeams();
        }
    } // simulate

    private void p1Wins()  {
        p1WinnerCount++;
    }

    private void p2Wins() {
        p2WinnerCount++;
    }

    private void nobodyWins() {
        drawCount++;
    }

    private void displayResults(int simulationCount) {
        String currentResults = "Player 1: " + displayP1Wins()
                + ", Player 2: " + displayP2Wins()
                + ", Draws: " + displayDraws();
        String resultsPercentage =
                "Player 1: " + (displayP1Wins() * 100f) / (simulationCount + 1)
                + "%, Player 2: " + (displayP2Wins() * 100f) / (simulationCount + 1)
                + "%, Draws: " + (displayDraws() * 100f) / (simulationCount + 1) + "%";
        System.out.println(currentResults);
        System.out.println(resultsPercentage
                + System.lineSeparator());
    }

    public int displayP1Wins() {
        return p1WinnerCount;
    }

    public int displayP2Wins() {
        return p2WinnerCount;
    }

    public int displayDraws() {
        return drawCount;
    }

    private void refreshTeams() {
        refreshPokemon(player1);
        refreshPokemon(player2);
    }

    private void refreshPokemon(RBYPlayer player) {
        for (int i = 0; i < 6; i++) {
            RBYPokemon pokemon = (RBYPokemon) player.getPokemon(i);
            if (pokemon != null) {
                pokemon.clearVolatileStatus();
                pokemon.removeNonVolatileStatus();
                pokemon.resetAllCounters();
                pokemon.resetAllPp();
                pokemon.resetMods();
                pokemon.resetStat(Stat.ATTACK);
                pokemon.resetStat(Stat.SPEED);
                pokemon.resetStatHp();
            }
        }
    }
} // RBYGameRunner
