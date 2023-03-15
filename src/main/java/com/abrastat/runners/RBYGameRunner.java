package com.abrastat.runners;

import com.abrastat.general.*;
import com.abrastat.rby.*;
import org.jetbrains.annotations.NotNull;

//import static com.abrastat.general.Item.*;

public class RBYGameRunner {
    private RBYPlayer player1;
    private RBYPlayer player2;
    private GameResult gameResult;

    private int p1WinnerCount = 0, p2WinnerCount = 0, drawCount = 0, aggregateTurnCount = 0,
            p1AggregateHP, p2AggregateHP, p1StruggleCount, p2StruggleCount, p1BoomCount, p2BoomCount;
    private int[] p1AggregatePPs, p2AggregatePPs;
    private int simulationCount = 1000;

    public RBYGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo)  {
        gameRunnerHelper(pokemonPlayerOne, pokemonPlayerTwo);
    }

    public RBYGameRunner(Pokemon pokemonPlayerOne,
                                    Pokemon pokemonPlayerTwo,
                                    int simulationCount) {
        this.simulationCount = simulationCount;
        gameRunnerHelper(pokemonPlayerOne, pokemonPlayerTwo);
//        for (PlayerBehaviour p1Behaviours : player1.getActiveBehaviours()) {
//            for (PlayerBehaviour p2Behaviours : player2.getActiveBehaviours()) {
//                this.simulate(simulationCount, p1Behaviours, p2Behaviours);
//            }
//        }
        this.simulate(simulationCount, PlayerBehaviour.JUST_ATTACK, PlayerBehaviour.JUST_ATTACK);
    }

    public RBYGameRunner(int simulationCount) {
        this.simulationCount = simulationCount;
        this.player1.setName("Youngster Joey");
        this.player2.setName("Bug Catcher Don");

        player1.addPokemon(new RBYPokemon.Builder("snorlax")
                .moves(RBYMove.BODY_SLAM)
                .build());
        player2.addPokemon(new RBYPokemon.Builder("zapdos")
                .moves(RBYMove.THUNDERBOLT)
                .build());

        this.simulate(simulationCount, PlayerBehaviour.JUST_ATTACK, PlayerBehaviour.JUST_ATTACK);
    } // RBYGameRunner

    private void gameRunnerHelper(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo)   {
        player1 = new RBYPlayer("Youngster Joey", pokemonPlayerOne);
        player2 = new RBYPlayer("Bug Catcher Don", pokemonPlayerTwo);
        this.player1.addPokemon(pokemonPlayerOne);
        this.player2.addPokemon(pokemonPlayerTwo);
    }

    private void refreshTeams() {
        player1.getCurrentPokemon().resetStatHp();
        player1.getCurrentPokemon().resetAllPp();
        player1.getCurrentPokemon().removeNonVolatileStatus();
        player2.getCurrentPokemon().resetStatHp();
        player2.getCurrentPokemon().resetAllPp();
        player2.getCurrentPokemon().removeNonVolatileStatus();
    }

    private void simulate(int simulationCount,
                          PlayerBehaviour p1Behaviours,
                          PlayerBehaviour p2Behaviours) {

        for (int i = 0; i < simulationCount; i++) {
            RBYGame game = new RBYGame(player1, p1Behaviours, player2, p2Behaviours);

            switch (game.getWinner()) {
                case 0:
                    this.nobodyWins();
                    break;
                case 1:
                    playerOneWins();
                    break;
                case 2:
                    playerTwoWins();
                    break;
            }

            this.aggregateTurnCount += game.getTurnNumber(); // sum all final turn counts for averaging later on
            this.p1AggregateHP += game.getPokemonPlayerOneHP();
            this.p2AggregateHP += game.getPokemonPlayerTwoHP();
            this.p1StruggleCount += game.isStrugglePlayerOne() ? 1 : 0;
            this.p2StruggleCount += game.isStrugglePlayerTwo() ? 1 : 0;
            this.p1BoomCount += game.isBoomedPlayerOne() ? 1 : 0;
            this.p2BoomCount += game.isBoomedPlayerTwo() ? 1 : 0;

            System.out.println(currentResults());
            System.out.println(resultsPercentages(i) + System.lineSeparator());
            refreshTeams();
        }
    } // simulate

    private @NotNull String resultsPercentages(int simCount) {
        return "Player 1: " + ((displayP1Wins() * 100f) / (simCount + 1))
                + "%, Player 2: " + ((displayP2Wins() * 100f) / (simCount + 1))
                + "%, Draws: " + ((displayDraws() * 100f / (simCount + 1))
                + "%");
    }

    public void incrementTurnCount() {
        this.aggregateTurnCount++;
    }

    public void playerOneWins()  {
        this.p1WinnerCount++;
    }

    public void playerTwoWins() {
        this.p2WinnerCount++;
    }

    public void nobodyWins()    {
        drawCount++;
    }

    public int displayP1Wins()   {
        return p1WinnerCount;
    }

    public int displayP2Wins()  {
        return p2WinnerCount;
    }

    public int displayDraws()   {
        return drawCount;
    }

    public @NotNull String currentResults() {
        return "Player 1: " + displayP1Wins() + ", Player 2: " + displayP2Wins() + ", Draws: " + displayDraws();
    }

    public GameResult getResult() {
        return new GameResult(
                player1.getCurrentPokemon(),
                player2.getCurrentPokemon(),
                p1WinnerCount,
                p2WinnerCount,
                drawCount,
                (aggregateTurnCount / simulationCount),
                p1AggregateHP,
                p1AggregatePPs,
                p2AggregateHP,
                p2AggregatePPs,
                p1StruggleCount,
                p2StruggleCount,
                p1BoomCount,
                p2BoomCount
        );
    } // GameResult
} // RBYGameRunner
