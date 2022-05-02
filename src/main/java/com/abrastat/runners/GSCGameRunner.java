package com.abrastat.runners;

import com.abrastat.general.*;
import com.abrastat.gsc.*;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.general.Item.*;

public class GSCGameRunner {

    private GSCPlayer player1;
    private GSCPlayer player2;
    private GameResult gameResult;

    private int playerOneWinnerCount = 0, playerTwoWinnerCount = 0, drawCount = 0;
    private Item playerOnePermanentItem, playerTwoPermanentItem; // returns the item in the case of Thief or Knock Off
    private int simulationCount = 1000;

    public GSCGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo)  {
        gameRunnerHelper(pokemonPlayerOne, pokemonPlayerTwo);
    }

    public GSCGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo, int simulationCount) {
        gameRunnerHelper(pokemonPlayerOne, pokemonPlayerTwo);
        for (PlayerBehaviour p1Behaviours : player1.getActiveBehaviours()) {
            for (PlayerBehaviour p2Behaviours : player2.getActiveBehaviours()) {
                this.simulate(simulationCount, p1Behaviours, p2Behaviours);
            }
        }
    }

    public GSCGameRunner(int simulationCount) {

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

            switch (new GSCGame(player1, p1Behaviours, player2, p2Behaviours).getWinner()) {
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


    public void playerOneWins()  {
        this.playerOneWinnerCount++;
    }

    public void playerTwoWins() {
        this.playerTwoWinnerCount++;
    }

    public void nobodyWins()    {
        drawCount++;
    }

    private int displayP1Wins()   {
        return playerOneWinnerCount;
    }

    private int displayP2Wins()  {
        return playerTwoWinnerCount;
    }

    private int displayDraws()   {
        return drawCount;
    }

    public @NotNull String currentResults() {
        return "Player 1: " + displayP1Wins() + ", Player 2: " + displayP2Wins() + ", Draws: " + displayDraws();
    }

    public GameResult getResult() {

    }

}
