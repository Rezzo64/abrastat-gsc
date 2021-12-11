package com.abrastat.runners;

import com.abrastat.general.Game;
import com.abrastat.general.Item;
import com.abrastat.general.Player;
import com.abrastat.gsc.GSCGame;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.abrastat.general.Game.player1;
import static com.abrastat.general.Game.player2;

public class GSCGameRunner {

    private int simulationCount = 50000;
    private int playerOneWinnerCount = 0, playerTwoWinnerCount = 0, drawCount = 0;
    private ArrayList<? extends Game> gameList = new ArrayList<>();

    public GSCGameRunner(@NotNull Player player1, @NotNull Player player2) {

        for (int i = 0; i < simulationCount; i++)   {

            refreshTeams();

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
        }
    }

    private void refreshTeams() {
        player1.addPokemon(new GSCPokemon("snorlax"));
        player1.getCurrentPokemon().addMoves(new GSCMove("bodySlam"));
        player1.getCurrentPokemon().setHeldItem(Item.LEFTOVERS);
        player2.addPokemon(new GSCPokemon("zapdos"));
        player2.getCurrentPokemon().addMoves(new GSCMove("thunder"));
        player2.getCurrentPokemon().setHeldItem(Item.LEFTOVERS);
    }

    public void playerOneWins()  {
        this.playerOneWinnerCount++;
        System.out.println(currentResults());
    }

    public void playerTwoWins() {
        this.playerTwoWinnerCount++;
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
