package com.abrastat.runners;

import com.abrastat.general.Game;
import com.abrastat.general.Item;
import com.abrastat.gsc.GSCGame;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.abrastat.general.Game.player1;
import static com.abrastat.general.Game.player2;
import static com.abrastat.general.Item.*;

public class GSCGameRunner {

    private int playerOneWinnerCount = 0, playerTwoWinnerCount = 0, drawCount = 0;
    private ArrayList<? extends Game> gameList = new ArrayList<>();
    private Item playerOnePermanentItem, playerTwoPermanentItem; // returns the item in the case of Thief or Knock Off

    public GSCGameRunner(GSCPokemon pokemonPlayerOne, GSCMove[] pokemonPlayerOneMoves, Item pokemonPlayerOneItem)    {

    }

    public GSCGameRunner(int simulationCount) {

        player1.addPokemon(new GSCPokemon.Builder("snorlax")
                .moves("bodySlam")
                .item(LEFTOVERS)
                .build());
        player2.addPokemon(new GSCPokemon.Builder("zapdos")
                .moves("thunder")
                .item(LEFTOVERS)
                .build());

        playerOnePermanentItem = player1.getCurrentPokemon().getHeldItem();
        playerTwoPermanentItem = player2.getCurrentPokemon().getHeldItem();

        for (int i = 0; i < simulationCount; i++)   {

            switch (new GSCGame().getWinner())  {
                case 0:
                    nobodyWins();
                    break;
                case 1:
                    playerOneWins();
                    break;
                case 2:
                    playerTwoWins();
                    break;
            }

            refreshTeams();

        }
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

    public void playerOneWins()  {
        this.playerOneWinnerCount++;
        System.out.println(currentResults() + System.lineSeparator());
    }

    public void playerTwoWins() {
        this.playerTwoWinnerCount++;
        System.out.println(currentResults() + System.lineSeparator());
    }

    public void nobodyWins()    {
        drawCount++;
    }

    public int displayP1Wins()   {
        return playerOneWinnerCount;
    }

    public int displayP2Wins()  {
        return playerTwoWinnerCount;
    }

    public int displayDraws()   {
        return drawCount;
    }

    private @NotNull String currentResults() {
        return "Player 1: " + displayP1Wins() + ", Player 2: " + displayP2Wins();
    }

}
