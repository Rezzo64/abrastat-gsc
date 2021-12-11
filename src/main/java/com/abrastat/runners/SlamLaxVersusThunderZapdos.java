package com.abrastat.runners;

import com.abrastat.general.Game;
import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.gsc.GSCGame;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;
import com.abrastat.gsc.GSCTurn;

import static com.abrastat.general.Game.player1;
import static com.abrastat.general.Game.player2;

public class SlamLaxVersusThunderZapdos {



    public static void main(String[] args) {

        player1.addPokemon(new GSCPokemon("snorlax"));
        player1.getCurrentPokemon().addMoves(new GSCMove("bodySlam"));
        player1.getCurrentPokemon().setHeldItem(Item.LEFTOVERS);
        player2.addPokemon(new GSCPokemon("zapdos"));
        player2.getCurrentPokemon().addMoves(new GSCMove("thunder"));
        player2.getCurrentPokemon().setHeldItem(Item.LEFTOVERS);

        Game game = new GSCGame();

        Messages.gameOver();
        System.exit(0);

    }
}
