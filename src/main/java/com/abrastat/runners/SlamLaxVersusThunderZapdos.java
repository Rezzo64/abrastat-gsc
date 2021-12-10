package com.abrastat.runners;

import com.abrastat.general.Game;
import com.abrastat.gsc.GSCGame;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

import static com.abrastat.general.Game.player1;
import static com.abrastat.general.Game.player2;

public class SlamLaxVersusThunderZapdos {



    public static void main(String[] args) {

        player1.addPokemon(new GSCPokemon("snorlax"));
        player2.addPokemon(new GSCPokemon("zapdos"));

        Game game = new GSCGame();

        while (game.checkPokemonAreNotFainted())    {
            ((GSCGame) game).initTurn(new GSCMove("thunder"), new GSCMove("bodySlam"));
        }
    }
}
