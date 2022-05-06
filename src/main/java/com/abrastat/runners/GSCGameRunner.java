package com.abrastat.runners;

import com.abrastat.general.*;
import com.abrastat.gsc.*;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Item.*;

public class GSCGameRunner {

    private GSCPlayer player1;
    private GSCPlayer player2;
    private GameResult gameResult;

    private int p1WinnerCount = 0, p2WinnerCount = 0, drawCount = 0, aggregateTurnCount = 0,
            p1AggregateHP, p2AggregateHP, p1StruggleCount, p2StruggleCount, p1BoomCount, p2BoomCount;

    private int[] p1AggregatePPs, p2AggregatePPs;

    private Item playerOnePermanentItem, playerTwoPermanentItem; // returns the item in the case of Thief or Knock Off

    private int simulationCount = 1000;

    public GSCGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo)  {
        gameRunnerHelper(pokemonPlayerOne, pokemonPlayerTwo);
    }

    public GSCGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo, int simulationCount) {
        this.simulationCount = simulationCount;
        gameRunnerHelper(pokemonPlayerOne, pokemonPlayerTwo);
        for (PlayerBehaviour p1Behaviours : player1.getActiveBehaviours()) {
            for (PlayerBehaviour p2Behaviours : player2.getActiveBehaviours()) {
                this.simulate(simulationCount, p1Behaviours, p2Behaviours);
            }
        }
    }

    public GSCGameRunner(int simulationCount) {
        this.simulationCount = simulationCount;
        this.player1.setName("Youngster Joey");
        this.player2.setName("Bug Catcher Don");

        player1.addPokemon(new GSCPokemon.Builder("snorlax")
                .moves(GSCMove.BODY_SLAM)
                .item(LEFTOVERS)
                .build());
        player2.addPokemon(new GSCPokemon.Builder("zapdos")
                .moves(GSCMove.THUNDER)
                .item(LEFTOVERS)
                .build());

        this.setPermanentItems();
        this.simulate(simulationCount, PlayerBehaviour.JUST_ATTACK, PlayerBehaviour.JUST_ATTACK);

    }

    private void gameRunnerHelper(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo)   {
        player1 = new GSCPlayer("Youngster Joey", pokemonPlayerOne);
        player2 = new GSCPlayer("Bug Catcher Don", pokemonPlayerTwo);
        this.player1.addPokemon(pokemonPlayerOne);
        this.player2.addPokemon(pokemonPlayerTwo);
        this.setPermanentItems();
    }

    private void setPermanentItems() {
        playerOnePermanentItem = player1.getCurrentPokemon().getHeldItem();
        playerTwoPermanentItem = player2.getCurrentPokemon().getHeldItem();
    }

    private void refreshTeams() {
        player1.getCurrentPokemon().resetStatHp();
        player1.getCurrentPokemon().resetAllPp();
        player1.getCurrentPokemon().removeNonVolatileStatus();
        player2.getCurrentPokemon().resetStatHp();
        player2.getCurrentPokemon().resetAllPp();
        player2.getCurrentPokemon().removeNonVolatileStatus();

        if (player1.getCurrentPokemon().getHeldItem() == null)  {
            player1.getCurrentPokemon().setHeldItem(playerOnePermanentItem);
        }
        if (player2.getCurrentPokemon().getHeldItem() == null)  {
            player2.getCurrentPokemon().setHeldItem(playerTwoPermanentItem);
        }

    }
    private void simulate(int simulationCount,
                          PlayerBehaviour p1Behaviours,
                          PlayerBehaviour p2Behaviours) {

        int i;

        for (i = 0; i < simulationCount; i++) {

            GSCGame game = new GSCGame(player1, p1Behaviours, player2, p2Behaviours);

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
    }

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

    private int displayP1Wins()   {
        return p1WinnerCount;
    }

    private int displayP2Wins()  {
        return p2WinnerCount;
    }

    private int displayDraws()   {
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
    }

}
