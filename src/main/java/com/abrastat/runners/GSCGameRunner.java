package com.abrastat.runners;

import com.abrastat.general.*;
import com.abrastat.gsc.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.abrastat.general.Item.*;

public class GSCGameRunner {

    private final GSCPlayer player1 = new GSCPlayer();
    private final GSCPlayer player2 = new GSCPlayer();

    private int playerOneWinnerCount = 0, playerTwoWinnerCount = 0, drawCount = 0;
    private ArrayList<? extends Game> gameList = new ArrayList<>();
    private Item playerOnePermanentItem, playerTwoPermanentItem; // returns the item in the case of Thief or Knock Off
    private int simulationCount = 1000;

    public GSCGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo)  {
        player1.setName("Youngster Joey");
        player2.setName("Bug Catcher Don");
        this.player1.addPokemon(pokemonPlayerOne);
        this.player2.addPokemon(pokemonPlayerTwo);
    }

    public GSCGameRunner(Pokemon pokemonPlayerOne, Pokemon pokemonPlayerTwo, int simulationCount) {
        player1.setName("Youngster Joey");
        player2.setName("Bug Catcher Don");
        this.player1.addPokemon(pokemonPlayerOne);
        this.player2.addPokemon(pokemonPlayerTwo);
        this.simulationCount = simulationCount;

        this.setPermanentItems();
        this.simulate(simulationCount);
    }

    public GSCGameRunner(
            String pkPl1, GSCMove @NotNull [] pkPl1Moves, Item pkPl1Item,
            String pkPl2, GSCMove @NotNull [] pkPl2Moves, Item pkPl2Item,
            int simulationCount
            ) {

        player1.setName("Youngster Joey");
        player2.setName("Bug Catcher Don");

        this.player1.addPokemon(new GSCPokemon.Builder(pkPl1)
                .moves(pkPl1Moves[0], pkPl1Moves[1], pkPl1Moves[2], pkPl2Moves[3])
                .item(pkPl1Item)
                .build()
        );

        this.player2.addPokemon(new GSCPokemon.Builder(pkPl2)
                .moves(pkPl2Moves[0], pkPl2Moves[1], pkPl2Moves[2], pkPl2Moves[3])
                .item(pkPl2Item)
                .build()
        );

        this.setPermanentItems();
        this.simulate(simulationCount);
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
        this.simulate(simulationCount);

    }

    private void setPermanentItems() {
        playerOnePermanentItem = player1.getCurrentPokemon().getHeldItem();
        playerTwoPermanentItem = player2.getCurrentPokemon().getHeldItem();
    }

    private void refreshTeams() {
        player1.getCurrentPokemon().resetStatHp();
        player1.getCurrentPokemon().resetMoveOnePP();
        player1.getCurrentPokemon().removeNonVolatileStatus();
        player2.getCurrentPokemon().resetStatHp();
        player2.getCurrentPokemon().resetMoveOnePP();
        player2.getCurrentPokemon().removeNonVolatileStatus();

        if (player1.getCurrentPokemon().getHeldItem() == null)  {
            player1.getCurrentPokemon().setHeldItem(playerOnePermanentItem);
        }
        if (player2.getCurrentPokemon().getHeldItem() == null)  {
            player2.getCurrentPokemon().setHeldItem(playerTwoPermanentItem);
        }

    }
    private void simulate(int simulationCount) {

        int i;

        for (i = 0; i < simulationCount; i++) {

            switch (new GSCGame(player1, player2).getWinner()) {
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

}
